package foo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.mysql.jdbc.ResultSetMetaData;

public class MyDB
{
	/**
	 * @brief		コンストラクタ
	 * @param[in]	String	database		使用するDB名
	 * @param[in]	String	table			使用するテーブル名
	 * @param[in]	String	db_user			使用するDBユーザー
	 * @param[in]	String	db_password		使用するDBユーザーのパスワード
	 * @return		なし
	 */
	public  MyDB (String database, String table, String db_user, String db_password)
	{
		m_connecter		= null;
		m_pState		= null;
		m_result		= null;
		m_database		= database;
		m_table			= table;
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			m_connecter = DriverManager.getConnection("jdbc:mysql://localhost/"+m_database, db_user, db_password);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * @brief		Insert文を実行する
	 * @param[in]	HashMap<String, String[]>	data		ユーザーデータ
	 * @param[in]	int							length		ユーザーデータの項目数
	 * @return		String						query		実行したクエリ
	 */
	public String Insert(HashMap<String, String[]> data, int number_of_record)
	{
		System.out.println(">--------------------");
		System.out.println("@MyDB._Insert");
		String query	=	"";						//実行するクエリ文
		String columns	=	"";						//クエリに挿入するカラム名一覧
		String values	=	"";						//クエリに挿入する値一覧
		for(int i=0;i<number_of_record;i++)
		{
			query		=	"";							//クエリ文を初期化する
			columns		=	"";							//カラム名を初期化する
			values		=	"";							//値を初期化する
			System.out.println("レコード"+i+"件目：");
			Iterator<Map.Entry<String, String[]>> it = data.entrySet().iterator();
			while(it.hasNext())
			{
				Map.Entry<String, String[]> map = it.next();
				columns +=  (String)map.getKey();					//カラム名を挿入
				values += "'"+(String)map.getValue()[i]+"'";		//値を挿入（INSERT文の場合一つのキーに対して挿入される値は一つ）
				if(it.hasNext())									//すべての要素を代入し終えた場合は , は不要
				{
					columns += ", ";
					values += ", ";
				}

			}
			query = "INSERT INTO " + m_table + "(" +columns+ ") VALUES(" +values+ ");";
			_ExecuteUpdate(query);					//クエリ文の実行
		}
		try
		{
			m_connecter.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		System.out.println("--------------------<");
		return query;
	}

	/**
	 * @brief		キーに対して値が一致する要素を削除する
	 * @param[in]	HashMap<String, String[]>	data				ユーザーデータ
	 * @param[in]	int							number_of_record	リクエストされたレコードの総数
	 * @return		String						query				実行したクエリ
	 */
	public String Delete(HashMap<String, String[]> data, int number_of_record)
	{
		System.out.println(">--------------------");
		System.out.println("@MyDB.Delete");
		String query		=	"";					//実行するクエリ文
		String evaluate		=	"";					//WHERE句に挿入する評価式
		for(int i=0;i<number_of_record;i++)
		{
			query			=	"";					//クエリ文を初期化する
			evaluate		=	"";					//カラム名を初期化する
			Iterator<Map.Entry<String, String[]>> it = data.entrySet().iterator();
			System.out.println("レコード"+i+"件目：");
			while(it.hasNext())
			{
				Map.Entry<String, String[]> map = it.next();
				evaluate += (map.getKey() +" = '"+ map.getValue()[i]+"'");
				if(it.hasNext())
				{
					evaluate +=	" AND ";
				}
			}
			query = "DELETE FROM " +m_table+ " WHERE " +evaluate+ " limit 1;";
			_ExecuteUpdate(query);
		}
		try
		{
			m_connecter.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		System.out.println("--------------------<");
		return query;
	}

	/**
	 * @brief		キーに対して値が一致する要素の全項目をselectする
	 * @param[in]	HashMap<String, String[]>	data				ユーザーデータ
	 * @param[in]	int							number_of_record	リクエストされたレコードの総数
	 * @return		String						query				実行したクエリ
	 */
	public String Select(HashMap<String, String[]> data, int number_of_record)
	{
		System.out.println(">--------------------");
		System.out.println("@MyDB.Delete");
		String query		=	"";					//実行するクエリ文
		String columns		=	"";					//クエリに挿入するカラム名一覧
		String evaluate		=	"";					//WHERE句に挿入する評価式
		String ReturnData	=	"";					//クエリの実行結果を格納する
		int count			=	0;					//クエリに挿入されるカラムの総数
		for(int i=0;i<number_of_record;i++)
		{
			query			=	"";					//クエリ文を初期化する
			columns			=	"";					//クエリに挿入するカラム名一覧
			evaluate		=	"";					//カラム名を初期化する
			Iterator<Map.Entry<String, String[]>> it = data.entrySet().iterator();
			System.out.println("レコード"+i+"件目：");
			while(it.hasNext())
			{
				count++;
				Map.Entry<String, String[]> map = it.next();
				evaluate += (map.getKey() +" = '"+ map.getValue()[i]+"'");
				columns +=  (String)map.getKey();					//カラム名を挿入
				if(it.hasNext())
				{
					columns += ", ";			//次の要素がある場合は「,」で区切る
					evaluate +=	" AND ";
				}
			}
			query = "SELECT * FROM " +m_table+ " WHERE " +evaluate;
			ReturnData +=_ExecuteQuery(query,count);
		}
		try
		{
			m_connecter.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		System.out.println("--------------------<");
		return ReturnData;
	}

	public void Update(HashMap<String, String[]> data, int number_of_record)
	{
		String values =  " = ";		//(列名　= 値)
		String evaluate = "";		//()
	}

	/**
	 * @brief		指定されたクエリを実行する
	 * @param[in]	String	query		実行するクエリ
	 * @return		なし
	 */
	public void _ExecuteUpdate(String query)
	{
		System.out.println(">--------------------");
		System.out.println("@MyDB._ExecuteUpdate");
		try
		{
			System.out.println("Execute: "+query);
			m_pState = m_connecter.prepareStatement(query);
		} catch (SQLException e)
		{
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		try
		{
			m_pState.executeUpdate();
		}
		catch (SQLException e)
		{
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("query error");
		}
		finally
		{

		}
		System.out.println("--------------------<");
	}

	/**
	 * @brief		指定されたクエリを実行する（UPDATA,DELETE以外）
	 * @param[in]	String	query		実行するクエリ
	 * @param[in]	int	count			実行されるクエリから返されるカラムの総数
	 * @return		ReturnData			クエリを実行した結果
	 */
	public String _ExecuteQuery(String query,int count)
	{
		ResultSet rs;			//クエリの実行結果を直接格納する
		String ResultData="";	//クエリの実行結果（外部出力用）を格納する
		String field="";		//各カラム名を格納する
		System.out.println(">--------------------");
		System.out.println("@MyDB._ExecuteQuary");
		try
		{
			System.out.println("Execute: "+query);
			m_pState = m_connecter.prepareStatement(query);
		} catch (SQLException e)
		{
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		try
		{
			rs = m_pState.executeQuery(query);
			ResultSetMetaData rsmd=(ResultSetMetaData) rs.getMetaData();
			while(rs.next()){
				for(int i=1;i<=rsmd.getColumnCount();i++)
				{
					field=rsmd.getColumnName(i);
					ResultData+=rs.getString(field)+"\n";
				}
			}
		}
		catch (SQLException e)
		{
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("query error");
		}
		finally
		{

		}
		System.out.println(ResultData);
		System.out.println("--------------------<");
		return ResultData;
	}



	/**
	 * Field
	 */
	protected		Connection					m_connecter;
	protected		java.sql.PreparedStatement	m_pState;
	protected		ResultSet					m_result;
	protected		String						m_database;			//データベース名
	protected		String						m_table;			//テーブル名
}
