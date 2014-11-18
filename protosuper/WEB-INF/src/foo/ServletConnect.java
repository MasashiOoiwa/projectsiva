package foo;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.*;
import javax.servlet.http.*;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;

public class ServletConnect extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		out.println("Hello unko ye");
		String val = req.getParameter("name");
		out.println(val + "<br>");
	}

	public int test() {
		return 3;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doPost()メソッド実");
		String que = request.getParameter("query");
		String tab = request.getParameter("table");
		String col[] = request.getParameterValues("col[]");
		
		PrintWriter out = response.getWriter();
		out.println(que + " "+ tab +"<br>");
		Connection conn;
		Statement stat;
		ResultSet rs;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/test?user=testuser");

			try {
				String sql ="select ? from ?;";
				java.sql.PreparedStatement st = conn.prepareStatement(sql);
				st.setString(1,que);
				st.setString(2, tab);
				rs = st.executeQuery();
				ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
				System.out.println(col.length);
//				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
//					// フィールド名
//					String field = rsmd.getColumnName(i);
//					// フィールド名に対するデータ
//					System.out.println(field);
//				}
				for(int g=0;g<col.length;g++){
					out.println(col[g] + " ");
				}
				out.println("aaaaaaaaaaaa");
				out.println("<br>");
				while (rs.next()) {
					for(int c=0;c<col.length;c++){
						out.println(rs.getString(col[c])+" ");
					}
					out.println("<br>");
//					System.out.println(col[1]);
//					System.out.println(rs.getString(col[1]));
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