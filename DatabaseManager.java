import java.sql.*;

public class DatabaseManager 
{
	
   final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   final String DB_URL = "jdbc:mysql://sql4.freemysqlhosting.net:3306/sql499600";

   final String USER = "sql499600";
   final String PASS = "KXIGpYguWY";
   
   final String sql = "SELECT name,points FROM Highscores";
      
   Connection conn = null;
   Statement stmt = null;
   ResultSet rs = null;
   
   String[] names = new String[10];
   int[] points = new int[10];
     
   private HighscoreManager HM;
   
   public DatabaseManager(HighscoreManager HM)
   {
   		this.HM = HM;
   }  
      
   public void connectDB()
   {
   		try
   		{
   			Class.forName("com.mysql.jdbc.Driver");
   			conn = DriverManager.getConnection(DB_URL,USER,PASS);
   			stmt = conn.createStatement();
   			rs = stmt.executeQuery(sql);
   			int i=0;
   			while(rs.next())
   			{
   				names[i] = rs.getString("name");
   				points[i] = rs.getInt("points");
   				i++;
   			}
   		}
   		catch(Exception e)
   		{	
   			HM.getHighscoresManually();
   		}
   		finally
   		{
   			disconnect();
   		}
   }
   
   public void updateDB(int x)
   {
   		try
   		{
   			Class.forName("com.mysql.jdbc.Driver");
   			conn = DriverManager.getConnection(DB_URL,USER,PASS);
   			stmt = conn.createStatement();
   			
   			for(int i=x; i<10; i++)
   				stmt.executeUpdate(updateSQL(i));
   		}
   		catch(Exception e)
   		{
   			HM.writeHighScoresManually();
   		}
   		
   }
   
   private String updateSQL(int rank)
   {
   		return "UPDATE Highscores SET name='"+HM.hsNames[rank]+"', points="+HM.hsPoints[rank]+" where rank="+(rank+1)+";"; 
   }
   
   public String[] returnNames()
   {
   		return names;			
   }
   
   public int[] returnPoints()
   {
   		return points;
   }
   
   public void disconnect() 
   {
   		conn = null;
   		stmt = null;	
   }
   
}