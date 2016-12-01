import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class utility {
	/**
	 * Allow to search other users
	 */
	public static void search(User user) {
		Scanner reader = new Scanner(System.in);
		String username = "";
		String yesno = "";
		System.out.println("Please enter a username: ");
		username = reader.nextLine();
		int user1ID = Database.findUserID(user.getName());
		int user2ID = Database.findUserID(username);
		
		if (user2ID != -1) {
			System.out.println("Do you want to add the user as friend?");
			System.out.println("Y/N");
			yesno = reader.nextLine();
			if (yesno.equals("Y")) {
				addFriend(user1ID, user2ID);
			} else if (yesno.equals("N")) {
				return;
			}
		}

	}
	
	private static void addFriend(int user1ID, int user2ID) {
		addFriendShip(user1ID, user2ID);
		System.out.println("friend requeest sent");
	}
	
	/**
	 * add relationship to the table
	 * @param user1ID
	 * @param user2ID
	 * @return
	 */
	public static void addFriendShip(int user1ID, int user2ID) {
		Connection connection = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:sudoku.db");
			connection.setAutoCommit(false);

			stmt = connection.createStatement();

			String sql = "INSERT OR REPLACE INTO friendship (USER1ID, USER2ID, user1, user2) " + "VALUES (" + user1ID + ", " + user2ID + " , " + 1 + " , " + 0 
					+ ");";

			stmt.executeUpdate(sql);
			stmt.close();
			connection.commit();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	public static void haveFriendRequest(User user) {

		int requestedID = Database.findUserID(user.getName());

		String name = hasFriendShip(requestedID);
		int friendRequestID = Database.findUserID(name);

		if (!name.equals("")) {
			Scanner reader = new Scanner(System.in);
			System.out.println("you have a friend request from " + name + "! Do you wanna add the user? (Y/N/hold)");
			String input = reader.nextLine();
			if (input.equals("Y")) {
				friendshipConnected(friendRequestID, requestedID);
				System.out.println("you are now friends!");
				
			} else if (input.equals("N")) {
				deleteFriendShip(friendRequestID, requestedID);
				
			} else if (input.equals("hold")) {
				return;
			}
		}

	}

	/**
	 * delete a friendship
	 * 
	 * @param user1ID
	 * @param user2ID
	 */
	public static void deleteFriendShip(int friendRequestID, int requestedID) {
		Connection connection = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:sudoku.db");
			connection.setAutoCommit(false);

			stmt = connection.createStatement();
			String sql = "DELETE FROM friendship WHERE user2id = " + requestedID + " AND user1id = " + friendRequestID
					+ ";";
			stmt.executeUpdate(sql);
			connection.commit();

			stmt.close();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * Connect friendship between two users
	 * 
	 * @param userID
	 */
	private static void friendshipConnected(int friendRequestID, int requestedID) {
		Connection connection = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:sudoku.db");
			connection.setAutoCommit(false);

			Statement stmt = connection.createStatement();
			String sql = "UPDATE FRIENDSHIP SET user2 = " + 1 + " WHERE user2id = " + requestedID + " AND user1id = "
					+ friendRequestID + ";";

			stmt.executeUpdate(sql);
			connection.commit();

			stmt.close();
			connection.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * If there an user sends a friend request, return its name
	 * 
	 * @param userID
	 * @return the user who sent friend request
	 */
	public static String hasFriendShip(int requestedID) {
		Connection connection = null;
		String name = "";
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:sudoku.db");
			connection.setAutoCommit(false);

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM friendship WHERE user2id = '" + requestedID + "';");

			if (!rs.isBeforeFirst()) {

				stmt.close();
				connection.close();
				return "";
			}

			int isOne = rs.getInt("user2");
			if (isOne == 1) {
				stmt.close();
				connection.close();
				return "";
			}

			int id = rs.getInt("user1ID");
			rs = stmt.executeQuery("SELECT * FROM USER WHERE id = '" + id + "';");
			name = rs.getString("name");

			stmt.close();
			connection.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return name;
	}
	
	

	/**
	 * print messages if user wins the game as well as updating the database
	 * 
	 * @param user
	 */
	public static void winning(User user) {

		// clear resume database
		SudokuDatabase.saveSudoku(user.getName(), null, null);

		// update score
		int currentScore = user.getScore();
		user.setScore(currentScore + 1);
		System.out.println("Your score is: " + user.getScore());

		// update hints if possible
		if (user.getScore() > currentScore && user.getScore() % 3 == 0) {
			user.setHints(user.getHints() + 1);
			System.out.println("Congratulations! you have unlocked a new hint!");
			System.out.println("You now have: " + user.getHints());
		}
		user.updateDatabase();

	}
	
	/**
	 * print the current board in order to let user to play
	 * 
	 * @param array
	 */
	public static void printBoard(int[][] array) {
		System.out.print("  ");
		for (int i = 0; i < array.length; i++) {
			if (i % 3 == 0) {
				System.out.print("|");
			}
			System.out.print(" " + i + " ");
		}

		System.out.println();
		System.out.println("_______________________________");

		for (int i = 0; i < array.length; i++) {
			System.out.print(i + " ");
			for (int j = 0; j < array.length; j++) {
				if (j % 3 == 0) {
					System.out.print("|");
				}
				if (array[i][j] != 0) {
					System.out.print(" " + array[i][j] + " ");
				} else {
					System.out.print(" _ ");
				}
			}
			if ((i + 1) % 3 == 0) {
				System.out.println();
				System.out.println("_______________________________");
			} else
				System.out.println();
		}
	}
	

	/**
	 * function that allows user to login
	 * 
	 * @return the user's information
	 */
	public static User login() {
		Scanner reader = new Scanner(System.in);
		String username = "";
		String password = "";
		boolean success = false;
		User user = null;

		// check invalid input
		while (!success) {
			System.out.print("Please enter username: ");
			username = reader.next();
			System.out.print("Please enter password: ");
			password = reader.next();
			user = Database.findUser(username, password);
			if (user == null) {
				System.out.println("Username or password incorrect! Please try again: ");
			} else {
				System.out.println();
				success = true;
			}
		}
		return user;
	}
	

	/**
	 * function that allows user to sign up and store information into database
	 * 
	 * @return new User information
	 */
	public static User signup() {
		Scanner reader = new Scanner(System.in);
		String username = "";
		String password = "";
		boolean success = false;

		// check invalid input
		while (!success) {
			System.out.print("Please enter username: ");
			username = reader.next();
			System.out.print("Please enter password: ");
			password = reader.next();
			if (!Database.writeDatabase(username, password)) {
				System.out.println("Username exists! Please choose a new username.");
			} else {
				System.out.println();
				success = true;
			}
		}

		return new User(username, password);
	}
	
	
}
