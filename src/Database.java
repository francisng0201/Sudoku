import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database{
	/**
	 * Print all users' information
	 */
	public static void printAll() {
		Connection connection = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:sudoku.db");
			connection.setAutoCommit(false);

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM USER;");
			print(rs);
			rs.close();
			stmt.close();
			connection.close();
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * print one user's information
	 * @param username
	 */
	public static void printOne(String username){
		Connection connection = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:sudoku.db");
			connection.setAutoCommit(false);

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM USER WHERE NAME = '" + username + "';");
			print(rs);		
			rs.close();
			stmt.close();
			connection.close();
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 * insert new user to database
	 * @param username
	 * @param password
	 */
	public static boolean writeDatabase(String username, String password) {
		Connection connection = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:sudoku.db");
			connection.setAutoCommit(false);

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM USER WHERE NAME = '" + username + "';");
			if (rs.next()){
				stmt.close();
				connection.close();
				return false;
			}
			String sql = "INSERT OR REPLACE INTO USER (NAME, PASSWORD) " + "VALUES ('" + username + "', '" + password + "');";

			stmt.executeUpdate(sql);
			stmt.close();
			connection.commit();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("sign up successfully");
		return true;
	}

	/**
	 * udpate a user's score and number of hints
	 */
	public static void updateDatabase(int newScore, int newHint, String username) {
		Connection connection = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:sudoku.db");
			connection.setAutoCommit(false);

			stmt = connection.createStatement();
			String sql = "UPDATE USER SET SCORE = " + newScore + ", HINTS = " + newHint + " WHERE NAME = '" + username + "';";
			stmt.executeUpdate(sql);
			connection.commit();

			ResultSet rs = stmt.executeQuery("SELECT * FROM USER;");

			rs.close();
			stmt.close();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * delete a user
	 * @param username
	 */
	public static void delete(String username) {
		Connection connection = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:sudoku.db");
			connection.setAutoCommit(false);

			stmt = connection.createStatement();
			String sql = "DELETE FROM USER WHERE NAME = " + username + ";";
			stmt.executeUpdate(sql);
			connection.commit();

			ResultSet rs = stmt.executeQuery("SELECT * FROM USER;");
			print(rs);

			rs.close();
			stmt.close();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("delete done successfully");
	}
	
	public static User findUser(String username, String password){
		Connection connection = null;
		Statement stmt = null;
		int score = 0;
		int hints = 0;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:sudoku.db");
			connection.setAutoCommit(false);

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM USER WHERE NAME = '" + username + "' AND PASSWORD = '"+ password + "';");
			if (!rs.next()){
				System.out.println("what");
				stmt.close();
				connection.close();
				return null;
			}
			
			score = rs.getInt("score");
			hints = rs.getInt("hints");
			
			stmt.close();
			connection.commit();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Login successfully");
		
		return new User(username, password, score, hints);		
	}
	
	/**
	 * Print information
	 * @param rs
	 * @throws SQLException
	 */
	public static void print(ResultSet rs) throws SQLException{
		while (rs.next()) {
			String name = rs.getString("name");
			int score = rs.getInt("score");
			int hints = rs.getInt("hints");
			System.out.println("Name = " + name);
			System.out.println("Score = " + score);
			System.out.println("HINTS = " + hints);
			System.out.println();
		}
	}
}