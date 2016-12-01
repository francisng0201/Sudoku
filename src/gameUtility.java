import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class gameUtility {
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

		// dont stop until user finishes the board/forfeit/restart/pause/validate
		while (!Arrays.deepEquals(board, originalBoard) && !checker.isValidSudoku(sudoku)) {
			utility.printBoard(board);
			System.out.println("Please enter a position (x, y) and a number, separate by space: ");
			System.out.println("or type forfeit to give up.");
			System.out.println("you can also type restart to restart the game.");
			System.out.println("type hint for a hint");
			System.out.println("type revert to revert");
			System.out.println("type pause to pause");
			System.out.println("type validate to validate");
			input = reader.nextLine();
			String[] parts = input.split(" ");

			// check input
			if (parts.length == 1) {
				if (parts[0].equals("forfeit")) {
					System.out.println("The solution is: ");
					Solver s = new Solver();
					s.solve(board);
					utility.printBoard(board);
					return 1;
				}
				if (parts[0].equals("restart")) {
					return 2;
				}
				if (parts[0].equals("hint")) {
					gameUtility.hint(sudoku, originalBoard, user);
					continue;
				}
				if (parts[0].equals("pause")) {
					// save the information
					SudokuDatabase.saveSudoku(user.getName(), sudoku, originalBoard);
					return 3;
				}
				if (parts[0].equals("revert")) {
					gameUtility.revert(board, stack);
					continue;
				}
				if (parts[0].equals("validate")) {
					gameUtility.isValid(board);
					continue;
				}
			}

			if (parts.length < 3) {
				System.out.println("Not enough input! Please enter again: ");
				System.out.println();
				continue;
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
	
	/**
	 * validate the current board
	 * @param board
	 */
	public static void isValid(int[][] board) {
		Solver solver = new Solver();
		int[][] validate = new int[board.length][];
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				validate[i] = board[i].clone();
			}
		}
		if (solver.solve(validate)) {
			System.out.println("is valid");
		} else {
			System.out.println("is not valid");
		}
	}
	
	/**
	 * revert to last step if possible
	 * @param board
	 * @param stack
	 */
	public static void revert(int[][] board, Stack<int[]> stack){
		if (stack.size() != 0) {
			int[] position = stack.pop();
			board[position[0]][position[1]] = 0;
		} else {
			System.out.println("You can't revert anymore!");
		}
	}
}
