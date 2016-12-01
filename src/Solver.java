import java.util.*;

public class Solver {

	/**
	 * Solve a given sudoku
	 * 
	 * @param sudoku
	 */
	public void solveSudoku(Sudoku sudoku) {
		int[][] board = sudoku.getBoard();
		solve(board);
	}

	/**
	 * Solve the sudoku recursively, fail if the sudoku is not valid
	 * 
	 * @param board
	 * @return true if solve successfully, false otherwise
	 */
	public boolean solve(int[][] board) {

		for (int i = 0; i < Sudoku.LENGTH; i++) {
			for (int j = 0; j < Sudoku.LENGTH; j++) {
				if (board[i][j] == 0) {
					for (int num = 1; num <= 9; num++) { // Try 1 through 9
						if (isValid(board, i, j, num)) {
							board[i][j] = num; // Put number into this cell
							if (solve(board)) {
								return true;
							} else {
								board[i][j] = 0;
							}
						}
					}
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Check the number put is valid
	 * 
	 * @param board
	 *            the sudoku board
	 * @param row
	 *            horizon position of the number
	 * @param col
	 *            vertical position of the number
	 * @param num
	 * @return true if the move is valid, false otherwise
	 */
	private boolean isValid(int[][] board, int row, int col, int num) {
		for (int i = 0; i < 9; i++) {

			// check row
			if (board[i][col] != 0 && board[i][col] == num) {
				return false;
			}

			// check column
			if (board[row][i] != 0 && board[row][i] == num) {
				return false;
			}

			// check subgrid
			int x = 3 * (row / 3) + i / 3;
			int y = 3 * (col / 3) + i % 3;
			if (board[x][y] != 0 && board[x][y] == num) {
				return false;
			}
		}
		return true;

	}
}
