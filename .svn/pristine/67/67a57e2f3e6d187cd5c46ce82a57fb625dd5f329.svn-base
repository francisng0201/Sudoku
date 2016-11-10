import static org.junit.Assert.*;

import org.junit.Test;

public class SolverTest {

	@Test
	public void testSolve() {
		Solver solver = new Solver();
		Sudoku s = new Sudoku();
		s.generate();
		int[][] b = {{5,0,0,9,8,0,7,1,3}, 
					{6,9,8,3,0,0,0,0,4}, 
					{0,7,0,5,0,2,8,9,0}, 
					{0,8,0,1,0,0,3,4,0}, 
					{9,0,2,0,3,4,0,7,5}, 
					{0,0,3,7,2,5,9,6,0}, 
					{0,0,0,6,1,0,4,0,7}, 
					{0,1,6,4,0,0,0,0,9},
					{4,5,7,0,9,3,6,8,1}};
		s.setBoard(b);
		s.print();
		int[][] board = s.getBoard();
		solver.solve(board);
		s.print();
		assertArrayEquals(board, s.getBoard());
	}

}
