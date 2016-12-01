import java.util.ArrayList;

public class User {
	String name;
	String password;
	ArrayList<Long> time = new ArrayList<Long>();
	int score;
	int hints;
	
	/**
	 * user constructor
	 * @param name user's name
	 */
	public User(String name, String password){
		this.name = name;
		this.password = password;
		this.score = 0;
		this.hints = 3;
	}
	
	/**
	 * user constructor
	 */
	public User(String name, String password, int score, int hints){
		this.name = name;
		this.password = password;
		this.score = score;
		this.hints = hints;
	}
	
	/**
	 * get the user's name
	 * @return the user's name
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Change the user's name
	 * @param name new name
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * get the user's password
	 * @return
	 */
	public String getPassword(){
		return this.password;
	}
	
	/**
	 * update user's password
	 * @param password
	 */
	public void setPassword(String password){
		this.password = password;
	}
	
	/**
	 * get the user's current score
	 * @return
	 */
	public int getScore(){
		return this.score;
	}
	
	/**
	 * update user's current score
	 * @param score
	 */
	public void setScore(int score){
		this.score = score;
	}
	
	/**
	 * get user's number of hints
	 * @return
	 */
	public int getHints(){
		return this.hints;
	}
	
	/**
	 * update user's number of hints
	 * @param hints
	 */
	public void setHints(int hints){
		this.hints = hints;
	}
	
	/**
	 * return the times being used by the user for all sudoku the user solved
	 * @return
	 */
	public ArrayList<Long> getTime(){
		return this.time;
	}
	
	/**
	 * Update database
	 */
	public void updateDatabase(){
		Database.updateDatabase(this.score, this.hints, this.name);
	}
	
	/**
	 * add a new time when the user finishes a sudoku
	 * @param time
	 */
	public void addTime(long time){
		this.time.add(time);
	}
}
