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
			System.out.println("(2) Resume");
			System.out.println("(3) Show score");
			System.out.println("(4) Show number of hints");
			System.out.println("(5) exit");
			System.out.println("Please choose one:");
			input = reader.nextInt();

			if (input == 1) {
				play(user);

			} else if (input == 2) {
				// resuming the game from the database
				Sudoku sudoku = SudokuDatabase.loadSudoku(username);
				int[][] originalBoard = SudokuDatabase.loadOriginalBoard(username);
				if (sudoku != null) {
					int win = gameLoop(sudoku, originalBoard, user, new Stack<int[]>());
					if (win == 0) {
						winning(user);
					}
				} else {
					System.out.println("can't resume");
				}

			} else if (input == 3) {
				System.out.println("Your score is: " + user.getScore());

			} else if (input == 4) {
				System.out.println("You have " + user.getHints() + " hints so far");

			} else if (input == 5) {
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

		int win = gameLoop(sudoku, originalBoard, user, stack);

		// check if it's restart
		while (win == 2) {
			stack = new Stack<int[]>();
			sudoku.setBoard(restart);
			win = gameLoop(sudoku, originalBoard, user, stack);
		}

		// player wins, print message and update database
		if (win == 0) {
			winning(user);
		}

	}

	/**
	 * print messages if user wins the game as well as updating the database
	 * 
	 * @param user
	 */
	public static void winning(User user) {

		// clear resume database
		SudokuDatabase.saveSudoku(user.getName(), null, null);

		// update score
		int currentScore = user.getScore();
		user.setScore(currentScore + 1);
		System.out.println("Your score is: " + user.getScore());

		// update hints if possible
		if (user.getScore() > currentScore && user.getScore() % 3 == 0) {
			user.setHints(user.getHints() + 1);
			System.out.println("Congratulations! you have unlocked a new hint!");
			System.out.println("You now have: " + user.getHints());
		}
		user.updateDatabase();

	}

	/**
	 * main game loop
	 * 
	 * @param sudoku
	 * @param originalBoard
	 * @param stack
	 *            save the position in order to revert
	 * @return 0 if the user successfully finishes the game; 1 if the user
	 *         forfeit; 2 if the user restarts; 3 if user pauses
	 */
	public static int gameLoop(Sudoku sudoku, int[][] originalBoard, User user, Stack<int[]> stack) {
		Scanner reader = new Scanner(System.in);
		Checker checker = new Checker();
		int[][] board = sudoku.getBoard();
		String input = "";

		// dont stop until user finishes the board/forfeit/restart/pause
		while (!Arrays.deepEquals(board, originalBoard) && !checker.isValidSudoku(sudoku)) {
			printBoard(board);
			System.out.println("Please enter a position (x, y) and a number, separate by space: ");
			System.out.println("or type forfeit to give up.");
			System.out.println("you can also type restart to restart the game.");
			System.out.println("type hint for a hint");
			System.out.println("type revert to revert");
			System.out.println("type pause to pause");
			input = reader.nextLine();
			String[] parts = input.split(" ");

			// check input
			if (parts.length == 1) {
				if (parts[0].equals("forfeit")) {
					System.out.println("The solution is: ");
					printBoard(originalBoard);
					return 1;
				}
				if (parts[0].equals("restart")) {
					return 2;
				}
				if (parts[0].equals("hint")) {
					hint(sudoku, originalBoard, user);
					continue;
				}
				if (parts[0].equals("pause")) {
					// save the information
					SudokuDatabase.saveSudoku(user.getName(), sudoku, originalBoard);
					return 3;
				}
				if (parts[0].equals("revert")) {

					if (stack.size() != 0) {
						int[] position = stack.pop();
						board[position[0]][position[1]] = 0;
					} else {
						System.out.println("You can't revert anymore!");
					}
					continue;
				}
			}

			if (parts.length < 3) {
				System.out.println("Not enough input! Please enter again: ");
				System.out.println();
			}

			int number = Integer.parseInt(parts[2]);
			int x = Integer.parseInt(parts[1]);
			int y = Integer.parseInt(parts[0]);

			// check if user input out of range
			if (!sudoku.getCanPut(x, y) || x < 0 || x > 8 || y < 0 || y > 8 || number < 0 || number > 9) {
				System.out.println("Invalid input! Please enter again: ");
				System.out.println();
			} else {

				board[x][y] = number;
				int[] position = new int[2];
				position[0] = x;
				position[1] = y;
				stack.push(position);

				// check user input exists in the same row or column
				if (!checker.checkColumn(board, x) || !checker.checkRow(board, y)) {
					System.out.println("number already exists in the same row/column! Please enter again: ");
					board[x][y] = 0;
					stack.pop();
				}
				System.out.println();
			}
		}
		System.out.println("Congratualtions!");
		return 0;
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

		// check invalid input
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

		// check invalid input
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

	/**
	 * Randomly give a hint to user
	 * 
	 * @param sudoku
	 * @param originalBoard
	 * @param user
	 */
	public static void hint(Sudoku sudoku, int[][] originalBoard, User user) {
		// check if the user has any hints
		if (user.getHints() == 0) {
			System.out.println("You do not have any hints!");
			return;
		}

		// Randomly generate a position that can put a number
		Random xRand = new Random();
		int x = xRand.nextInt(9);
		Random yRand = new Random();
		int y = yRand.nextInt(9);

		while (!sudoku.getCanPut(x, y)) {
			x = xRand.nextInt(9);
			y = yRand.nextInt(9);
		}

		// update the board
		sudoku.setBoard(originalBoard[x][y], x, y);
		System.out.println("number " + originalBoard[x][y] + " at " + y + " " + x + " is set");

		// Subtract hints by 1 and update the database
		user.setHints(user.getHints() - 1);
		System.out.println("Hints applied. You now have: " + user.getHints());
		user.updateDatabase();
	}
}
