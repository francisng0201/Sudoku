import java.sql.*;

public class sql {
	
	/**
	 * Print all users' information
	 */
	public void printAll() {
		Connection connection = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:sudoku.db");
			connection.setAutoCommit(false);
			System.out.println("Opened database successfully");

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
		System.out.println("Operation done successfully");
	}

	/**
	 * print one user's information
	 * @param username
	 */
	public void printOne(String username){
		Connection connection = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:sudoku.db");
			connection.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT FROM USER WHERE USERNAME = " + username + ";");
			print(rs);
			
			rs.close();
			stmt.close();
			connection.close();
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully");		
	}
	
	/**
	 * insert new user to database
	 * @param username
	 * @param password
	 */
	public void writeDatabase(String username, String password) {
		Connection connection = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:sudoku.db");
			connection.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = connection.createStatement();
			String sql = "INSERT INTO USER (NAME, PASSWORD) " + "VALUES ('" + username + "', '" + password + "');";

			stmt.executeUpdate(sql);
			stmt.close();
			connection.commit();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Records created successfully");

	}

	/**
	 * udpate a user's score and number of hints
	 */
	public void updateDatabase(int newScore, int newHint, int id) {
		Connection connection = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:test.db");
			connection.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = connection.createStatement();
			String sql = "UPDATE USER SET SCORE = " + newScore + ", HINTS = " + newHint + " WHERE ID = " + id + ";";
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
		System.out.println("Operation done successfully");
	}

	/**
	 * delete a user
	 * @param username
	 */
	public void delete(String username) {
		Connection connection = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:test.db");
			connection.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = connection.createStatement();
			String sql = "DELETE FROM USER WHERE USERNAME = " + username + ";";
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
		System.out.println("Operation done successfully");
	}
	
	/**
	 * Print information
	 * @param rs
	 * @throws SQLException
	 */
	public void print(ResultSet rs) throws SQLException{
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