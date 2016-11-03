import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class SudokuTest {

	@Test
	/**
	 * test generate() as well as putNumbers()
	 */
	public void testGenerate() {
		Sudoku s = new Sudoku();
		s.generate();
		int[][] board = s.getBoard();
		assertEquals("length is incorrect", board.length, 9);
		assertEquals("length is incorrect", board[0].length, 9);
		int[] number = {1, 2, 3, 4, 5, 6, 7, 8, 9};
		for (int i = 0; i < 9; i++){
			boolean valid = true;
			for (int j = 0; j < 9; j++){
				if (Arrays.binarySearch(number,board[i][j]) == -1){
					valid = false;
				}
				assertTrue(valid);
			}
		}
	}
	
	@Test
	/**
	 * test isLegal()
	 */
	public void testIsLegal(){
		Sudoku s = new Sudoku();
		int[][] board = new int[9][9];
		for (int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				board[i][j] = j+1;
			}
		}
		s.setBoard(board);

		for(int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				for (int k = 1; k < 10; k++){
					assertFalse(s.isLegal(i, j, k));
				}
			}	
		}
	}

	/**
	 * test getter and setter
	 */
	public void testGetandSet(){
		Sudoku s = new Sudoku();
		int[][] board = new int[9][9];
		for (int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				board[i][j] = j+1;
			}
		}
		s.setBoard(board);
		int[][] b = s.getBoard();
		for (int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				assertEquals("wrong number!", b[i][j], j+1);
			}
		}
		
		s.setBoard(10, 0, 0);
		assertEquals("wrong number!", b[0][0], 10);
		
		s.setBoard(111, 8, 8);
		assertEquals("number should not be changed!", b[8][8], 9);
		
		s.setBoard(5, 8, 8);
		assertEquals("wrong number!", b[8][8], 5);
		
		s.setBoard(3, -1, 0);
		assertEquals("number should not be changed!", b[0][0], 10);
		
		s.setBoard(4, 0, 9);
		assertEquals("number should not be changed!", b[0][0], 10);
	}
}
