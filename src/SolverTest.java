import static org.junit.Assert.*;

import org.junit.Test;

public class SolverTest {

	@Test
	public void testSolve() {
		Solver solver = new Solver();
		Sudoku s = new Sudoku();
		s.generate();
		s.print();

		DigHoles dh = new DigHoles(s, "Difficult");
		dh.dig(s);
		int[][] board = s.getBoard();
		solver.solve(board);
		s.print();
		assertArrayEquals(board, s.getBoard());
	}

}
