import static org.junit.Assert.*;

import org.junit.Test;

public class SudokuDatabaseTest {

	@Test
	public void test() {
		
		String username = "Francis";
		Sudoku sudoku = new Sudoku();
		sudoku.generate();
		int[][] board = sudoku.getBoard();
		
		int[][] originalBoard = new int[board.length][];
		for (int i = 0; i < board.length; i++) {
			originalBoard[i] = board[i].clone();
		}
		
		DigHoles dh = new DigHoles(sudoku, "Easy");
		dh.dig(sudoku);
		
		SudokuDatabase.saveSudoku(username, sudoku, originalBoard);
		SudokuDatabase.loadSudoku(username);
		SudokuDatabase.loadOriginalBoard(username);
	}

}
