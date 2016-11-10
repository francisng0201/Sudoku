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
				user = login();
				input = 4;
			} else if (input == 2) {
				user = signup();
				input = 4;
			} else if (input == 3) {
				System.out.println("Goodbye!");
				System.exit(0);
			}
		}

		input = 0;
		String username = user.getName();

		// options to play games or show information
		while (input != 4) {
			System.out.println("Hello " + username + ", what do you want to do?");
			System.out.println("(1) Play Sudoku");
			System.out.println("(2) Show score");
			System.out.println("(3) Show number of hints");
			System.out.println("(4) exit");
			System.out.println("Please choose one:");
			input = reader.nextInt();
			if (input == 1) {
				play(user);
			} else if (input == 2) {
				System.out.println("Your score is: " + user.getScore());
			} else if (input == 3) {
				System.out.println("You have " + user.getHints() + " hints so far");
			} else if (input == 4) {
				System.out.println("Goodbye!");
			} else {
				System.out.println("Invalid! Please choose again");
			}
			System.out.println();
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

		sudoku.generate();
		int[][] board = sudoku.getBoard();
		int[][] originalBoard = new int[board.length][];
		for (int i = 0; i < board.length; i++) {
			originalBoard[i] = board[i].clone();
		}
		DigHoles dh = new DigHoles(sudoku, difficultly);
		dh.dig(sudoku);

		boolean win = gameLoop(sudoku, originalBoard);
		if (win) {
			int currentScore = user.getScore();
			user.setScore(currentScore + 1);
			System.out.println("Your score is: " + user.getScore());

			if (user.getScore() > currentScore && user.getScore() % 3 == 0) {
				user.setHints(user.getHints() + 1);
				System.out.println("Congratulations! you have unlocked a new hint!");
				System.out.println("You now have: " + user.getHints());
			}
			user.updateDatabase();
		}
	}

	/**
	 * main game loop
	 * 
	 * @param sudoku
	 * @param originalBoard
	 * @return true if the game finishes successfully, false otherwise
	 */
	public static boolean gameLoop(Sudoku sudoku, int[][] originalBoard) {
		Scanner reader = new Scanner(System.in);
		Checker checker = new Checker();
		int[][] board = sudoku.getBoard();
		String input = "";
		while (!Arrays.deepEquals(board, originalBoard) && !checker.isValidSudoku(sudoku)) {
			printBoard(board);
			System.out.println("Please enter a position (x, y) and a number, separate by space: ");
			System.out.println("or type forfeit to give up.");
			input = reader.nextLine();
			String[] parts = input.split(" ");

			if (parts.length == 1 && parts[0].equals("forfeit")) {
				return false;
			}
			if (parts.length < 3) {
				System.out.println("Not enough input! Please enter again: ");
				System.out.println();
			}

			int number = Integer.parseInt(parts[2]);
			int x = Integer.parseInt(parts[1]);
			int y = Integer.parseInt(parts[0]);
			if (!sudoku.getCanPut(x, y) || x < 0 || x > 8 || y < 0 || y > 8 || number < 0 || number > 9) {
				System.out.println("Invalid input! Please enter again: ");
				System.out.println();
			} else {
				board[x][y] = number;
				if (!checker.checkColumn(board, x) || !checker.checkRow(board, y)) {
					System.out.println("number already exists in the same row/column! Please enter again: ");
					board[x][y] = 0;
				}
				System.out.println();
			}
		}
		System.out.println("Congratualtions!");
		return true;
	}

	/**
	 * print the current board in order to let user to play
	 * 
	 * @param array
	 */
	public static void printBoard(int[][] array) {
		System.out.print("  ");
		for (int i = 0; i < array.length; i++) {
			if (i % 3 == 0) {
				System.out.print("|");
			}
			System.out.print(" " + i + " ");
		}

		System.out.println();
		System.out.println("_______________________________");

		for (int i = 0; i < array.length; i++) {
			System.out.print(i + " ");
			for (int j = 0; j < array.length; j++) {
				if (j % 3 == 0) {
					System.out.print("|");
				}
				if (array[i][j] != 0) {
					System.out.print(" " + array[i][j] + " ");
				} else {
					System.out.print(" _ ");
				}
			}
			if ((i + 1) % 3 == 0) {
				System.out.println();
				System.out.println("_______________________________");
			} else
				System.out.println();
		}
	}

	/**
	 * function that allows user to login
	 * 
	 * @return the user's information
	 */
	public static User login() {
		Scanner reader = new Scanner(System.in);
		String username = "";
		String password = "";
		boolean success = false;
		User user = null;
		while (!success) {
			System.out.print("Please enter username: ");
			username = reader.next();
			System.out.print("Please enter password: ");
			password = reader.next();
			user = Database.findUser(username, password);
			if (user == null) {
				System.out.println("Username or password incorrect! Please try again: ");
			} else {
				System.out.println();
				success = true;
			}
		}
		return user;
	}

	/**
	 * function that allows user to sign up and store information into database
	 * 
	 * @return new User information
	 */
	public static User signup() {
		Scanner reader = new Scanner(System.in);
		String username = "";
		String password = "";
		boolean success = false;
		while (!success) {
			System.out.print("Please enter username: ");
			username = reader.next();
			System.out.print("Please enter password: ");
			password = reader.next();
			if (!Database.writeDatabase(username, password)) {
				System.out.println("Username exists! Please choose a new username.");
			} else {
				System.out.println();
				success = true;
			}
		}
		return new User(username, password);
	}
}
