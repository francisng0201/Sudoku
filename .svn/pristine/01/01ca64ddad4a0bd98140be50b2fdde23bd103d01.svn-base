import static org.junit.Assert.*;

import org.junit.Test;

public class SolverTest {

	@Test
	public void testSolve() {
		Solver solver = new Solver();
		Sudoku s = new Sudoku();
		s.generate();
		int[][] board = s.getBoard();
		board[0][0] = 0;
		board[8][8] = 0;
		board[7][6] = 0;
		board[4][3] = 0;
		board[2][5] = 0;
		board[7][3] = 0;
		board[8][0] = 0;
		solver.solve(board);
		assertArrayEquals(board, s.getBoard());
	}

}
