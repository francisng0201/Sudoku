import java.util.Random;

public class generator {
	
	private static final int LENGTH = 9;
	private int[][] board;
	
	public generator(){
		board = new int[LENGTH][LENGTH];
	}
	
	public void generate(){
		putNumbers(0, 0);
	}
	
	public boolean putNumbers(int x, int y){
		Random random = new Random();
		int currentNum = 0;
		int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};

		for (int i = LENGTH - 1; i > 0; i--){
			currentNum = random.nextInt(i);
			int tmp = numbers[currentNum];
			numbers[currentNum] = numbers[i];
			numbers[i] = tmp;
		}

		for (int i = 0; i < LENGTH; i++){
			if (isLegal(x, y, numbers[i])){
				board[x][y] = numbers[i];
				
				if (x != 8){
					x++;
				}
				else{
					if (y == 8){
						return true;
					}
					else{
						x = 0;
						y++;
					}
				}
				
				if (putNumbers(x, y)){
					return true;
				}
			}
		}
		
		board[x][y] = 0;
		return false;
	}
	
	private boolean isLegal(int x, int y, int current){
		// Check if same number exists in horizontal or vertical directions
		for (int i = 0; i < LENGTH; i++){
			if (board[x][i] == current || board[i][y] == current){
				return false;
			}
		}
		
		int edgeX = 0;
		int edgeY = 0;
		
		// Check for x out of bound
		if (x > 2){
			if (x > 5){
				edgeX = 6;
			}
			else{
				edgeX = 3;
			}
		}
		
		// Check for y out of bound
		if (y > 2){
			if (y > 5){
				edgeY = 6;
			}
			else{
				edgeY = 3;
			}
		}
		
		// Check if same number exists in the same subgrid
		for (int i = edgeX; i < edgeX + 3; i++){
			for (int j = edgeY; j < edgeY + 3; j++){
				if (board[i][j] == current){
					return false;
				}
			}
		}
				
		return true;
	}
	
	public void print(){
		for(int i=0;i<LENGTH;i++){
			for(int j=0;j<LENGTH;j++){
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args)
	{
		generator sg = new generator();
		sg.putNumbers(0, 0);
		sg.print();
	}
}
