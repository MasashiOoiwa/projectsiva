package foo;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Map;
import java.util.HashMap;

public class MySQLConnections extends HttpServlet
{
	/// ---------------------------------------------------------
	/// field
	/// ---------------------------------------------------------
	protected		Connection					m_connecter;
	protected		Statement					m_state;
	protected		java.sql.PreparedStatement	m_pState;
	protected		ResultSet					m_result;
	protected		PrintWriter					m_out;

	protected		String						m_database;		//データベース名
	protected		String						m_table;		//テーブル名
	protected		HashMap<String, String>		m_postData;
	protected		String						m_query;
	protected		int							m_dataCount = 0;

	/// ---------------------------------------------------------
	/// Set Connections to MySQL.
	/// ---------------------------------------------------------
	public  MySQLConnections ()
	{
		m_postData = new HashMap<String, String>();
		m_table = "empmaster";
		m_database = "shop";
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			m_connecter = DriverManager.getConnection("jdbc:mysql://localhost/shop","shopmaster", "kagihime");
			m_state = m_connecter.createStatement();
		}catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	protected void doGet(HttpServletRequest req,HttpServletResponse res)
			throws ServletException,IOException
	{
		System.out.println("get ok");
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response)
				throws ServletException,IOException
	{

		boolean success;
		m_out = response.getWriter();


		_SetParam(request);			//メンバに格納
		_SetQuery();				//クエリの生成
		_GetResult();				//クエリ結果を取り出す



		m_out.println("ok");
	}


	private void _SetParam(HttpServletRequest request)
	{
		Enumeration<String> names = request.getParameterNames();
		while(names.hasMoreElements()){
			String name = (String)names.nextElement();
			m_postData.put(name, request.getParameter(name));
			System.out.println(name +" "+request.getParameter(name));
			m_dataCount++;
		}
		_AdditionData();
	}

	private void _AdditionData()
	{
		MyTime time = new MyTime();
		String presence_date = time.GetDate();
		String presence_time = time.GetTime();
		m_postData.put("UPDATING_DAY", presence_date);
		m_postData.put("UPDATING_TIME", presence_time);
		m_postData.put("UPDATING_ID", "MASTER");
		m_postData.put("REGIST_DAY", presence_date);
		m_postData.put("REGIST_TIME", presence_time);
		m_postData.put("REGISTRANT_ID", "MASTER");
		m_dataCount += 6;		//これはだめ
	}

	protected boolean _SetQuery()
	{

		boolean success = true;
		String column = "";
		String values = "";

		int count = 0;
		for(Map.Entry<String, String> e : m_postData.entrySet())
		{
			column += e.getKey();
			values += "'"+e.getValue()+"'";
			count++;
			if(count < m_dataCount){
				column += ", ";
				values += ", ";
			}
		}
		String query = "INSERT INTO " + m_table + "(" + column + ") VALUES(" + values + ");";

		try {
			m_pState = m_connecter.prepareStatement(query);

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return success;
	}

	protected ResultSet _GetResult()
	{
		try
		{
			m_pState.executeUpdate();

		} catch (Exception ex) {
			m_out.println("SQL is worry");
			m_out.println(ex.getMessage());
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("query error");
		}finally{
			try {
				m_connecter.close();
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}


		return m_result;
	}


}
