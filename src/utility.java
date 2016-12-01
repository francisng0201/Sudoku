import java.util.Scanner;

public class utility {
	/**
	 * Allow to search other users
	 */
	public static void search() {
		Scanner reader = new Scanner(System.in);
		String username = "";
		String yesno = "";
		boolean found = true;
		System.out.println("Please enter a username: ");
		username = reader.nextLine();
		if (!Database.findUser(username)) {
			found = false;
		}

		if (found) {
			System.out.println("Do you want to add the user as friend?");
			System.out.println("Y/N");
			yesno = reader.nextLine();
			if (yesno.equals("Y")) {
				System.out.println("friend requeest sent");
				//addFriend();
			} else if (yesno.equals("N")) {
				return;
			}
		}

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
