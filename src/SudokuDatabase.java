import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SudokuDatabase {

	/**
	 * insert new user to database
	 * 
	 * @param username
	 * @param password
	 */
	public static boolean saveSudoku(String username, Sudoku sudoku, int[][] originalBoard) {
		Connection connection = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:sudoku.db");
			connection.setAutoCommit(false);

			stmt = connection.createStatement();
			String nonEmptyBoard = "";
			String original = "";
			if (sudoku != null) {
				// Translate the board into String to store
				StringBuilder sb = new StringBuilder();
				int[][] board = sudoku.getBoard();
				for (int i = 0; i < Sudoku.LENGTH; i++) {
					for (int j = 0; j < Sudoku.LENGTH; j++) {
						if (board[i][j] != 0) {
							String s = Integer.toString(board[i][j]);
							sb.append(s);
						} else {
							sb.append(".");
						}
					}
				}
				nonEmptyBoard = sb.toString();

				sb = new StringBuilder();
				for (int i = 0; i < Sudoku.LENGTH; i++) {
					for (int j = 0; j < Sudoku.LENGTH; j++) {
						String s = Integer.toString(originalBoard[i][j]);
						sb.append(s);
					}
				}
				original = sb.toString();
			}

			String sql = "INSERT OR REPLACE INTO HISTORY (NAME, SUDOKU, ORIGINALBOARD) " + "VALUES ('" + username
					+ "', '" + nonEmptyBoard + "', '" + original + "');";

			stmt.executeUpdate(sql);
			stmt.close();
			connection.commit();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return true;
	}

	public static Sudoku loadSudoku(String username) {
		Connection connection = null;
		Statement stmt = null;
		Sudoku sudoku = new Sudoku();

		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:sudoku.db");
			connection.setAutoCommit(false);

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM HISTORY WHERE NAME = '" + username + "';");

			if (!rs.isBeforeFirst()) {
				System.out.println("Does not exist!");
				stmt.close();
				connection.close();
				return null;
			}

			String str = rs.getString("sudoku");
			if (str.equals("")) {
				System.out.println("You don't have any saved games!");
				stmt.close();
				connection.close();
				return new Sudoku();
			}

			int[][] board = new int[Sudoku.LENGTH][Sudoku.LENGTH];

			int j = 0;
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) == '.') {
					board[j][(i - j * 9)] = 0;
					sudoku.setCanPut(j, i - j * 9);
				} else {
					board[j][(i - j * 9)] = str.charAt(i) - '0';
				}
				if (i % 9 == 8) {
					j++;
				}
			}

			sudoku.setBoard(board);

			stmt.close();
			connection.commit();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("loaded successfully");

		return sudoku;
	}

	public static int[][] loadOriginalBoard(String username) {
		Connection connection = null;
		Statement stmt = null;
		int[][] originalBoard = new int[Sudoku.LENGTH][Sudoku.LENGTH];

		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:sudoku.db");
			connection.setAutoCommit(false);

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM HISTORY WHERE NAME = '" + username + "';");

			String str = rs.getString("originalBoard");

			int[][] board = new int[Sudoku.LENGTH][Sudoku.LENGTH];

			int j = 0;
			for (int i = 0; i < str.length(); i++) {
				board[j][(i - j * 9)] = str.charAt(i) - '0';
				if (i % 9 == 8) {
					j++;
				}
			}

			stmt.close();
			connection.commit();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("loaded successfully");

		return originalBoard;
	}

}