package foo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.ResultSetMetaData;

public class Login extends HttpServlet {
	public void doPost(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		PrintWriter out = response.getWriter();
		String inputuser = request.getParameter("user");
		String inputpass = request.getParameter("pass");
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn;
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/test?user=testuser");
			
			try {
				Statement stat;
				stat = conn.createStatement();
				System.out.println(inputpass);
				ResultSet rs = stat.executeQuery("select * from account where username = '"+inputuser+"';");
				while(rs.next()){
					System.out.println(rs);
					if(rs.getString("password").equals(inputpass)){
						out.println("seikou");
					}else{
						out.println("diffrent pass!!");
					}
				}
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			out.println("SQL is worry");
			out.println(ex.getMessage());
			System.out.println("SQLException: " + ex.getMessage());
			// System.out.println("SQLState: " + ex.getSQLState());
			// System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
}
