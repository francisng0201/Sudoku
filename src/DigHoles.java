// The reasons I am choosing this file is because This is the first project I collabroated with other people
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DigHoles {
	int numGiven;
	int lowerbound;
	String difficultly;

	/**
	 * Constructor
	 * 
	 * @param sudoku
	 *            the sudoku being dug
	 * @param difficultly
	 *            game's difficultly
	 */
	public DigHoles(Sudoku sudoku, String difficultly) {
		// Randomly generate number of holes being dug based on the difficultly
		// Number of holes being dug are based on the paper
		// http://zhangroup.aporc.org/images/files/Paper_3485.pdf
		if (difficultly.equals("Easy")) {
			this.numGiven = ThreadLocalRandom.current().nextInt(36, 50);
			lowerbound = 4;
		} else if (difficultly.equals("Medium")) {
			this.numGiven = ThreadLocalRandom.current().nextInt(32, 35);
			lowerbound = 3;
		} else {
			this.numGiven = ThreadLocalRandom.current().nextInt(28, 31);
			lowerbound = 2;
		}
		this.difficultly = difficultly;
	}

	/**
	 * Dig some holes from the given sudoku based on the difficultly
	 * 
	 * @param sudoku
	 */
	public void dig(Sudoku sudoku) {
		int nonEmptyCells = 0;
		if (this.difficultly.equals("Easy") || this.difficultly.equals("Medium")) {
			nonEmptyCells = digRandomly(sudoku);
		} else {
			nonEmptyCells = digFromTop(sudoku);
		}

		System.out.println("Number of Empty cells: " + (81 - nonEmptyCells));
	}

	/**
	 * dig teh sudoku randomly
	 * @param sudoku
	 * @return number of non empty cells
	 */
	private int digRandomly(Sudoku sudoku) {
		int[][] board = sudoku.getBoard();
		int nonEmptyCells = 81;
		Solver solver = new Solver();
		int[][] validate = new int[board.length][];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				validate[i] = board[i].clone();
			}
		}
		
		// Keep digging until condition is met
		while (nonEmptyCells > numGiven) {
			Random xRand = new Random();
			int x = xRand.nextInt(Sudoku.LENGTH);
			Random yRand = new Random();
			int y = yRand.nextInt(Sudoku.LENGTH);

			// Check if the cell being dug is valid
			
			if (lowerbound < numOfNonEmptyinRow(board, x) && lowerbound < numOfNonEmptyinColumn(board, y)
					&& lowerbound < numOfNonEmptyinSubGrid(board, x, y) && board[x][y] != 0 && solver.solve(validate)) {
				board[x][y] = 0;
				sudoku.setCanPut(x, y);
				nonEmptyCells--;
			}
		}
		return nonEmptyCells;
	}

	/**
	 * dig starting from the left top side
	 * @param sudoku
	 * @return number of non empty cells
	 */
	private int digFromTop(Sudoku sudoku) {
		int[][] board = sudoku.getBoard();
		int nonEmptyCells = 81;
		Solver solver = new Solver();
		int[][] validate = new int[board.length][];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				validate[i] = board[i].clone();
			}
		}
		
		// start digging from the top
		while (nonEmptyCells > numGiven) {
			for (int i = 0; i < Sudoku.LENGTH; i++) {
				for (int j = 0; j < Sudoku.LENGTH; j++) {
					if (lowerbound < numOfNonEmptyinRow(board, i) && lowerbound < numOfNonEmptyinColumn(board, j)
							&& lowerbound < numOfNonEmptyinSubGrid(board, i, j) && board[i][j] != 0 && solver.solve(validate)) {
						board[i][j] = 0;
						sudoku.setCanPut(i, j);
						nonEmptyCells--;
					}
					if (nonEmptyCells <= numGiven){
						break;
					}
				}
			}
		}
		
		//shuffle sudoku
		propagation(sudoku);

		return nonEmptyCells;
	}

	/**
	 * Shuffle the sudoku randomly
	 * 
	 * @param sudoku
	 */
	private void propagation(Sudoku sudoku) {
		int[][] board = sudoku.getBoard();

		// rotate sudoku
		int[][] b = new int[board.length][board[0].length];
		Random rand = new Random();
		int rotate = rand.nextInt(5);
		for (int k = 0; k < rotate; k++) {
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[0].length; j++) {
					b[j][board.length - 1 - i] = board[i][j];
				}
			}
		}
		sudoku.setBoard(b);

		// swap column randomly
		rand = new Random();
		int col = rand.nextInt(101);
		for (int j = 0; j < col; j++) {
			Random colRand = new Random();
			int col1 = colRand.nextInt(Sudoku.LENGTH);
			int col2 = 0;
			if (col1 % 3 == 0) {
				col2 = (int) (Math.random() * 2) + 1;
			}
			if (col1 % 3 == 2) {
				col2 = (int) (Math.random() * (-2)) - 1;
			}
			col2 = col1 + col2;
			if (col1 % 3 == 0 || col1 % 3 == 2) {
				for (int i = 0; i < Sudoku.LENGTH; i++) {
					int temp = board[i][col1];
					board[i][col1] = board[i][col2];
					board[i][col2] = temp;
				}
			}
		}

		//swap row randomly
		rand = new Random();
		int row = rand.nextInt(101);
		for (int j = 0; j < row; j++) {
			Random rowRand = new Random();
			int row1 = rowRand.nextInt(Sudoku.LENGTH);
			int row2 = 0;
			if (row1 % 3 == 0) {
				row2 = (int) (Math.random() * 2) + 1;
			}
			if (row1 % 3 == 2) {
				row2 = (int) (Math.random() * (-2)) - 1;
			}
			row2 = row1 + row2;
			if (row1 % 3 == 0 || row1 % 3 == 2) {
				for (int i = 0; i < Sudoku.LENGTH; i++) {
					int temp = board[row1][i];
					board[row1][i] = board[row2][i];
					board[row2][i] = temp;
				}
			}
		}
	}

	/**
	 * Helper function to check number of non empty cells in one row
	 * 
	 * @param board
	 * @param row
	 * @return number of non empty cells
	 */
	public int numOfNonEmptyinRow(int[][] board, int row) {
		int count = 0;
		for (int i = 0; i < Sudoku.LENGTH; i++) {
			if (board[row][i] != 0) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Helper function to check number of non empty cells in one column
	 * 
	 * @param board
	 * @param col
	 * @return number of non empty cells
	 */
	public int numOfNonEmptyinColumn(int[][] board, int col) {
		int count = 0;
		for (int i = 0; i < Sudoku.LENGTH; i++) {
			if (board[i][col] != 0) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Helper function to check number of non empty cells in one sub grid
	 * 
	 * @param board
	 * @param row
	 * @param col
	 * @return number of non empty cells
	 */
	public int numOfNonEmptyinSubGrid(int[][] board, int row, int col) {
		int count = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int m = row / 3 * 3 + i;
				int n = col / 3 * 3 + j;
				if (board[m][n] != 0) {
					count++;
				}
			}
		}
		return count;
	}

}
