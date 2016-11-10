import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DigHoles {
	Sudoku sudoku = new Sudoku();
	int numOfHoles;
	int lowerbound;
	public DigHoles(Sudoku sudoku, String difficultly){
		this.sudoku.setBoard(sudoku.getBoard());
		if (difficultly.equals("Easy")){
			this.numOfHoles = ThreadLocalRandom.current().nextInt(36, 50);
			lowerbound = 4;
		}
		else if (difficultly.equals("Medium")){
			this.numOfHoles = ThreadLocalRandom.current().nextInt(32, 35);
			lowerbound = 3;
		}
		else{
			this.numOfHoles = ThreadLocalRandom.current().nextInt(28, 31);
			lowerbound = 2;
		}
		dig(this.sudoku);
	}
	
	public int getNumOfHoles(){
		System.out.println(numOfHoles);
		return this.numOfHoles;
	}
	
	public void dig(Sudoku sudoku){
		sudoku.print();
		int[][] board = sudoku.getBoard();
		int nonEmptyCells = 81;
		while (nonEmptyCells > numOfHoles){
			Random xRand = new Random();
			int x = xRand.nextInt(Sudoku.LENGTH);
			Random yRand = new Random();
			int y = yRand.nextInt(Sudoku.LENGTH);
			if (lowerbound < numOfNonEmptyinRow(board, x) && lowerbound < numOfNonEmptyinColumn(board, y) && board[x][y] != 0){
				board[x][y] = 0;
				nonEmptyCells--;
			}
		}
		sudoku.setBoard(board);
		sudoku.print();
		Solver s = new Solver();
		s.solveSudoku(sudoku);
		sudoku.print();
	}
	
	public int numOfNonEmptyinRow(int[][] board, int row){
		int count = 0;
		for (int i = 0; i < Sudoku.LENGTH; i++){
			if (board[row][i] != 0){
				count++;
			}
		}
		return count;
	}
	
	public int numOfNonEmptyinColumn(int[][] board, int col){
		int count = 0;
		for (int i = 0; i < Sudoku.LENGTH; i++){
			if (board[i][col] != 0){
				count++;
			}
		}
		return count;
	}
	
	public static void main(String[] args){
		Sudoku sudoku = new Sudoku();
		sudoku.generate();
		DigHoles dh = new DigHoles(sudoku, "Easy");
	}
}
