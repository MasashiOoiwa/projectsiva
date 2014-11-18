package foo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.IIOException;

import com.mysql.jdbc.Driver;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;
public class MySQLTest {
	public static void main(String[] args){
          Connection conn;
          Statement stat;
          ResultSet rs;
          String res[];

          try {
        	  Class.forName("com.mysql.jdbc.Driver").newInstance();
        	  conn = DriverManager.getConnection("jdbc:mysql://localhost/test?user=testuser");
               try {
                    PreparedStatement ps = (PreparedStatement) conn.prepareStatement("select ? from master;");
                    rs = ps.executeQuery("itemname");
                    java.sql.ResultSetMetaData rsm = rs.getMetaData();
                    System.out.println(rs);
                    
                    while (rs.next()) {
                    	  String lastName = rs.getString("itemname");
                    	  System.out.println(lastName + "\n");
                    	 
                    }
               }finally {
                    conn.close();
               }
          } catch (Exception ex){
               System.out.println("SQLException: " + ex.getMessage());
               //System.out.println("SQLState: " + ex.getSQLState());
               //System.out.println("VendorError: " + ex.getErrorCode());

          }
     }

}
