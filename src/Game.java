import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Game {

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		int input = 0;
		User user = null;
		// Sign up or login
		System.out.println("Welcome! Please enter your username and password to login or signup: ");
		while (input != 4) {
			System.out.println("(1) Login");
			System.out.println("(2) Signup");
			System.out.println("(3) exit");
			input = reader.nextInt();
			if (input == 1) {
				user = utility.login();
				input = 4;
			} else if (input == 2) {
				user = utility.signup();
				input = 4;
			} else if (input == 3) {
				System.out.println("Goodbye!");
				System.exit(0);
			}
		}

		input = 0;
		String username = user.getName();

		// options to play games or show information
		while (input != 6) {

			utility.haveFriendRequest(user);

			System.out.println("Hello " + username + ", what do you want to do?");
			System.out.println("(1) Play Sudoku");
			System.out.println("(2) Resume");
			System.out.println("(3) Show score");
			System.out.println("(4) Show number of hints");
			System.out.println("(5) search user");
			System.out.println("(6) exit");
			System.out.println("Please choose one:");
			input = reader.nextInt();

			if (input == 1) {
				play(user);
			} else if (input == 2) {
				resume(user);
			} else if (input == 3) {
				System.out.println("Your score is: " + user.getScore());
			} else if (input == 4) {
				System.out.println("You have " + user.getHints() + " hints so far");
			} else if (input == 5) {
				utility.search(user);
			} else if (input == 6) {
				System.out.println("Goodbye!");
			} else {
				System.out.println("Invalid! Please choose again");
			}

			System.out.println();
		}
	}
	
	/**
	 * Resume game
	 * @param user
	 */
	public static void resume(User user){
		// resuming the game from the database
		String username = user.getName();
		Sudoku sudoku = SudokuDatabase.loadSudoku(username);

		boolean isEmpty = true;

		for (int i = 0; i < Sudoku.LENGTH; i++) {
			for (int j = 0; j < Sudoku.LENGTH; j++) {
				if (sudoku.getBoard()[i][j] != 0) {
					isEmpty = false;
				}
			}
		}

		if (!isEmpty) {
			int[][] originalBoard = SudokuDatabase.loadOriginalBoard(username);
			int win = gameUtility.gameLoop(sudoku, originalBoard, user, new Stack<int[]>());
			if (win == 0) {
				utility.winning(user);
			}
		} else {
			System.out.println("can't resume");
		}
	}
	

	/**
	 * let user choose difficultly and if the user won; update its score and
	 * number of hints
	 * 
	 * @param user
	 */
	public static void play(User user) {
		Scanner reader = new Scanner(System.in);
		String difficultly = "";
		Sudoku sudoku = new Sudoku();
		int input = 0;

		// choose difficulty
		while (input != 1 && input != 2 && input != 3) {
			System.out.println("Please choose difficulty: ");
			System.out.println("(1) Easy");
			System.out.println("(2) Medium");
			System.out.println("(3) Hard");
			System.out.println("(4) Back");
			input = reader.nextInt();
			if (input == 1) {
				difficultly = "Easy";
			} else if (input == 2) {
				difficultly = "Medium";
			} else if (input == 3) {
				difficultly = "Hard";
			} else if (input == 4) {
				return;
			} else {
				System.out.println("Invalid! Please choose again");
				System.out.println();
			}
		}

		// generate sudoku and dig holes
		sudoku.generate();
		int[][] board = sudoku.getBoard();
		int[][] originalBoard = new int[board.length][];
		for (int i = 0; i < board.length; i++) {
			originalBoard[i] = board[i].clone();
		}

		DigHoles dh = new DigHoles(sudoku, difficultly);
		dh.dig(sudoku);

		// use a 2d array to store dug sudoku for restart
		int[][] restart = new int[board.length][];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				restart[i] = board[i].clone();
			}
		}

		// start the game
		Stack<int[]> stack = new Stack<int[]>();

		int win = gameUtility.gameLoop(sudoku, originalBoard, user, stack);

		// check if it's restart
		while (win == 2) {
			stack = new Stack<int[]>();
			sudoku.setBoard(restart);
			win = gameUtility.gameLoop(sudoku, originalBoard, user, stack);
		}

		// player wins, print message and update database
		if (win == 0) {
			utility.winning(user);
		}

	}

}
