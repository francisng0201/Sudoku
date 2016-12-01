import static org.junit.Assert.*;

import org.junit.Test;

public class DigHolesTest {

	@Test
	public void test() {
		Sudoku sudoku = new Sudoku();
		sudoku.generate();
		DigHoles dh = new DigHoles(sudoku,"Easy");
		int[][] board = new int[9][9];
		for (int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				board[i][j] = 0;
			}
		}

		for (int i = 0; i < 9; i++){
			int num = dh.numOfNonEmptyinColumn(board, i);
			assertEquals("number of Non empty is incorrect!", 0, num);
		}
		
		for (int i = 0; i < 9; i++){
			int num = dh.numOfNonEmptyinRow(board, i);
			assertEquals("number of Non empty is incorrect!", 0, num);
		}
		
		for (int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				int num = dh.numOfNonEmptyinSubGrid(board, i, j);
				assertEquals("number of Non empty is incorrect!", 0, num);
			}
		}
		
		board[0][0] = 1;
		int num = dh.numOfNonEmptyinColumn(board, 0);
		assertEquals("number of Non empty is incorrect!", 1, num);
		int num2 = dh.numOfNonEmptyinSubGrid(board, 0, 0);
		assertEquals("number of Non empty is incorrect!", 1, num2);
		int num3 = dh.numOfNonEmptyinRow(board, 8);
		assertEquals("number of Non empty is incorrect!", 0, num3);
		
	}

}
