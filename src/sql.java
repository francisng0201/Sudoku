import java.sql.*;

public class sql{
	  public void readDataBase(){
	    Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:sudoku.db");
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM USER;" );
	      while ( rs.next() ) {
	         String  name = rs.getString("name");
	         int score  = rs.getInt("score");
	         int hints = rs.getInt("hints");
	         System.out.println( "Name = " + name );
	         System.out.println( "Score = " + score );
	         System.out.println("HINTS = " + hints);
	         System.out.println();
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Operation done successfully");
	  }
	  
	  public void writeDatabase(String username, String password){
			  Connection c = null;
			    Statement stmt = null;
			    try {
			      Class.forName("org.sqlite.JDBC");
			      c = DriverManager.getConnection("jdbc:sqlite:sudoku.db");
			      c.setAutoCommit(false);
			      System.out.println("Opened database successfully");

			      stmt = c.createStatement();
			      String sql = "INSERT INTO USER (NAME, PASSWORD) " +
			    		  	   "VALUES ('" + username + "', '" + password +"');";
			      
			      stmt.executeUpdate(sql);
			      stmt.close();
			      c.commit();
			      c.close();
			    } catch ( Exception e ) {
			      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			      System.exit(0);
			    }
			    System.out.println("Records created successfully");
			    
	  }

	  /**
	   * udpate score and number of hints
	   */
	  public void updateDatabase(int newScore, int newHint, int id){
		    Connection c = null;
		    Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:test.db");
		      c.setAutoCommit(false);
		      System.out.println("Opened database successfully");

		      stmt = c.createStatement();
		      String sql = "UPDATE USER SET SCORE = " + newScore  
		      		+ ", HINTS = "+ newHint + " WHERE ID = " + id + ";";
		      stmt.executeUpdate(sql);
		      c.commit();

		      ResultSet rs = stmt.executeQuery( "SELECT * FROM USER;" );
		      while (rs.next()) {
		         String  name = rs.getString("name");
		         int score  = rs.getInt("score");
		         int hints = rs.getInt("hints");
		         System.out.println( "Name = " + name );
		         System.out.println( "Score = " + score );
		         System.out.println("HINTS = " + hints);
		         System.out.println();
		      }
		      
		      rs.close();
		      stmt.close();
		      c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("Operation done successfully");
	  }
	  
	  public void delete(String username){
		    Connection c = null;
		    Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:test.db");
		      c.setAutoCommit(false);
		      System.out.println("Opened database successfully");

		      stmt = c.createStatement();
		      String sql = "DELETE FROM USER WHERE USERNAME = " + username + ";";
		      stmt.executeUpdate(sql);
		      c.commit();

		      ResultSet rs = stmt.executeQuery( "SELECT * FROM USER;" );
		      while ( rs.next() ) {
		         String  name = rs.getString("name");
		         int score  = rs.getInt("score");
		         int hints = rs.getInt("hints");
		         System.out.println( "Name = " + name );
		         System.out.println( "Score = " + score );
		         System.out.println("HINTS = " + hints);
		         System.out.println();
		      }
		      
		      rs.close();
		      stmt.close();
		      c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("Operation done successfully");
	  }
}