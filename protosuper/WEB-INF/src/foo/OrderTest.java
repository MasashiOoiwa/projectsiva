package foo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderTest extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,HttpServletResponse response)
				throws ServletException,IOException
	{
		PrintWriter pw = response.getWriter();
		HashData data = new HashData();
		data.SetData(request);
		data.ShowData();
		MyDB mydb = new MyDB("shopdb", "ordertable", "root", "");
		mydb.Insert(data.Data(), data.record());
		//mydb.Delete(data.Data(), data.record());
		//mydb.Select(data.Data(), data.record());
		pw.println("ok");
	}

}
