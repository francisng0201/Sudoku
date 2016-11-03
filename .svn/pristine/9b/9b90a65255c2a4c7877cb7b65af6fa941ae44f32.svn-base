import static org.junit.Assert.*;

import org.junit.Test;

public class CheckerTest {

	@Test
	/**
	 * test isCompleted()
	 */
	public void testIsCompleted() {
		int[][] board = new int[9][9];
		for (int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				board[i][j] = j+1;
			}
		}
		Checker c = new Checker();
		assertTrue(c.isCompleted(board));
		
		board[0][0] = -1;
		assertFalse(c.isCompleted(board));
	}

	@Test
	/**
	 * test checkRow()
	 */
	public void testCheckRow(){
		int[][] board = new int[9][9];
		for (int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				board[i][j] = 1;
			}
		}
		Checker c = new Checker();
		for (int i = 0; i < 9; i++){
			assertFalse(c.checkRow(board, i));
		}
		assertFalse("wrong row number", c.checkRow(board, -1));
		assertFalse("wrong row number", c.checkRow(board, 10));
	}
	
	@Test
	/**
	 * test checkColumn()
	 */
	public void testcheckColumn(){
		int[][] board = new int[9][9];
		for (int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				board[i][j] = j+1;
			}
		}
		Checker c = new Checker();
		for (int i = 0; i < 9; i++){
			assertFalse(c.checkColumn(board, i));
		}
		assertFalse("wrong column number", c.checkColumn(board, -1));
		assertFalse("wrong column number", c.checkColumn(board, 10));
	}
	
	@Test
	/**
	 * test checkSubGrid()
	 */
	public void testCheckSubGrid(){
		int[][] board = new int[9][9];
		for (int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				board[i][j] = j+1;
			}
		}
		Checker c = new Checker();
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				assertFalse(c.checkSubGrid(board, i, j));
			}
		}
		assertFalse("wrong column number", c.checkSubGrid(board, 0, 100));
		assertFalse("wrong row number", c.checkSubGrid(board, -1, 0));
	}
}
