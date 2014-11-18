package foo;

import java.io.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Map;
import java.util.HashMap;

import com.mysql.jdbc.Driver;
//java.util.Calendar

public class MasterEmployeeInsert extends MySQLConnections
{
	/// ------------------------------------------------------------
	/// field
	/// ------------------------------------------------------------

	private		String	m_id;			//従業員ID
	private		String	m_name;			//従業員名
	private		String	m_kana;			//従業員名（ふりがな）
	private		String	m_address;		//従業員の住所
	private		String	m_phone1;		//従業員の電話番号
	private		String	m_phone2;		//従業員の携帯電話番号
	private		String	m_sex;			//従業員の性別
	private		String	m_birth;		//従業員の生年月日
	private		String	m_hiredate;		//従業員の入社日
	private		String	m_job;			//従業員の役職
	private		String	m_updateDate;	//更新日
	private		String	m_updateTime;	//更新時間
	private		String	m_updaterID;	//最終更新者ID
	private		String	m_registerDate;	//初登録日
	private		String	m_registerTime;	//初登録時間
	private		String	m_registerID;	//登録者ID
	private		String	m_password;		//従業員のパスワード


	public MasterEmployeeInsert()
	{
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
		System.out.println("sub_const");
	}

	@Override
	protected void doGet(HttpServletRequest req,HttpServletResponse res)
			throws ServletException,IOException
	{

	}


	@Override
	/// ------------------------------------------------------------
	/// @brief	POSTメソッド
	///	@note
	///
	/// ------------------------------------------------------------
	protected void doPost(HttpServletRequest request,HttpServletResponse response)
				throws ServletException,IOException
	{
		boolean success;
		m_out = response.getWriter();

		success = _SetParam(request);			//メンバに格納
		_SetQuery();							//クエリの生成
		_GetResult();							//クエリ結果を取り出す

	}


	/// ------------------------------------------------------------
	///	@brief
	///	@param
	///	@note
	///
	/// ------------------------------------------------------------
	protected boolean _SetParam(HttpServletRequest request)
	{
		boolean success = false;
		try{
			Map test_map = request.getParameterMap();
			Enumeration<String> names = request.getParameterNames();
			while(names.hasMoreElements()){
				String name = names.nextElement();
				System.out.println(name);
			}

			m_id			=	request.getParameter("emp_id");
			m_name			=	request.getParameter("emp_name");
			m_kana			=	request.getParameter("emp_kana");
			m_address		=	request.getParameter("emp_address");
			m_phone1		=	request.getParameter("emp_phone1");
			m_phone2		=	request.getParameter("emp_phone2");
			m_sex			=	request.getParameter("emp_sex");
			m_birth			=	request.getParameter("emp_birth");
			m_hiredate		=	request.getParameter("emp_hiredate");
			m_job			=	request.getParameter("emp_job");
			m_updateDate	=	"2014-11-04";								//更新日
			m_updateTime	=	"00:00:00";									//更新時間
			m_updaterID		=	"Hunce";									//最終更新者ID
			m_registerDate	=	"2014-11-04";								//初登録日
			m_registerTime	=	"00:00:00";;								//初登録時間
			m_registerID	=	"Hunce";									//登録者ID
			m_password		=	request.getParameter("emp_password");

			success = true;
		}catch(Exception e){

		}

		return success;
	}

	protected boolean _tSetParam(HttpServletRequest request)
	{
		boolean flag = false;
		m_id			=	"10";
		m_name			=	"Jony";
		m_kana			=	"Jony";
		m_address		=	"Shonan";
		m_phone1		=	"0667733";
		m_phone2		=	"08022456565";
		m_sex			=	"0";
		m_birth			=	"2014-11-04";
		m_hiredate		=	"2014-11-04";
		m_job			=	"King";
		m_updateDate	=	"2014-11-04";									//更新日
		m_updateTime	=	"00:00:00";									//更新時間
		m_updaterID		=	"unkoman";									//最終更新者ID
		m_registerDate	=	"2014-11-04";									//初登録日
		m_registerTime	=	"00:00:00";									//初登録時間
		m_registerID	=	"unkoman";							//登録者ID
		m_password		=	"unkoman";
		return flag;
	}

	/// ------------------------------------------------------------
	///	@brief
	///	@param
	///	@note
	///
	/// ------------------------------------------------------------
	protected boolean _SetQuery()
	{

		boolean success = true;
		String query ="insert into "+m_table+" values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		try {

			m_pState = m_connecter.prepareStatement(query);

			m_pState.setString(1, m_id);				//
			m_pState.setString(2, m_name);				//
			m_pState.setString(3, m_kana);				//
			m_pState.setString(4, m_address);			//
			m_pState.setString(5, m_phone1);			//
			m_pState.setString(6, m_phone2);			//
			m_pState.setString(7, m_sex);				//
			m_pState.setString(8, m_birth);				//
			m_pState.setString(9, m_hiredate);			//
			m_pState.setString(10, m_job);				//
			m_pState.setString(11, m_updateDate);		//
			m_pState.setString(12, m_updateTime);		//
			m_pState.setString(13, m_updaterID	);		//
			m_pState.setString(14, m_registerDate);		//
			m_pState.setString(15, m_registerTime);		//
			m_pState.setString(16, m_registerID);		//
			m_pState.setString(17, m_password);
			m_pState.setString(18, "bikoh");//

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return success;
	}


	/// ------------------------------------------------------------
	///	@brief
	///	@param
	///	@note
	///
	/// ------------------------------------------------------------
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
