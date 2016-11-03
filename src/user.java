import java.util.ArrayList;

public class user {
	String name;
	ArrayList<Long> time = new ArrayList<Long>();
	
	/**
	 * user constructor
	 * @param name user's name
	 */
	public user(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public ArrayList<Long> getTime(){
		return this.time;
	}
	
	public void addTime(long time){
		this.time.add(time);
	}
}
