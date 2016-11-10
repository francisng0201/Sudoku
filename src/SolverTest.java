import static org.junit.Assert.*;

import org.junit.Test;

public class SolverTest {

	@Test
	public void testSolve() {
		Solver solver = new Solver();
		Sudoku s = new Sudoku();
		s.generate();
		s.setBoard(0, 0, 0);
		s.setBoard(0, 1, 2);
		s.setBoard(0, 3, 7);
		s.setBoard(0, 5, 8);
		s.setBoard(0, 6, 3);
		s.setBoard(0, 2, 8);
		s.print();
		int[][] board = s.getBoard();
		solver.solve(board);
		s.print();
		assertArrayEquals(board, s.getBoard());
	}

}
