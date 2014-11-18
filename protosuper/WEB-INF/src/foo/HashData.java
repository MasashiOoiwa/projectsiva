package foo;

import java.util.*;
import javax.servlet.http.HttpServletRequest;


public class HashData {
	/**
	 * @brief		格納している全要素数を返す
	 * @param		なし
	 * @return		m_data		HashMap<String, String[]>		格納しているハッシュテーブル
	 */		
	public HashMap<String, String[]>	Data()
	{
		return	m_data;
	}

	/**
	 * @brief		格納しているレコード数を返す
	 * @param		なし
	 * @return		m_count		int		格納しているレコード数
	 */
	public int record()
	{
		System.out.println(">--------------------");
		System.out.println("@HashData.record");
		System.out.println("戻り値 m_record: "+m_record);
		System.out.println("--------------------<");
		return m_record;
	}
	
	/**
	 * @brief		格納している全要素数を返す
	 * @param		なし
	 * @return		m_count		int		格納している全要素数
	 */
	public int length()
	{
		System.out.println(">--------------------");
		System.out.println("@HashData.length");
		System.out.println("戻り値 m_count: "+m_count);
		System.out.println("--------------------<");
		return m_count;
	}
	
	/**
	 * @brief		デフォルトコンストラクタ
	 * @param[in]	なし
	 * @return		なし
	 */
	public HashData()
	{
		m_data = new HashMap<String, String[]>();	
	}
	
	/**
	 * @brief		引数のリクエストからデータを格納するコンストラクタ
	 * @param[in]	HttpServletRequest	request		ユーザーリクエスト
	 * @return		なし
	 */
	public HashData(HttpServletRequest request)
	{
		m_data = new HashMap<String, String[]>();
		SetData(request);
	}
	
	/**
	 * @brief		POSTされたデータをHashMapに格納する
	 * @param[in]	HttpServletRequest	request
	 * @return		なし
	 */
	public void SetData(HttpServletRequest request)
	{
		System.out.println(">--------------------");
		System.out.println("@HashData.SetData");
		Enumeration<String> request_keys = request.getParameterNames();
		while(request_keys.hasMoreElements())
		{
			String request_key = (String)request_keys.nextElement();	//リクエストから送られた要素のキー
			String hash_key = request_key;								//格納したときキーになる文字列
			if(!_CheckRequest())										//リクエストチェック
			{
				return;
			}
			if(request.getParameterValues(request_key).length > 1)		//データが配列型の場合キーに[]が挿入されるのでチェックする
			{
				hash_key = _RenameKey(request_key);						//[]文字列の除去
			}
			m_data.put(hash_key, request.getParameterValues(request_key));
			for(int i=0;i<request.getParameterValues(request_key).length;i++){
				System.out.println("hash_key:" +String.format("%-20s", hash_key)+ " value:" +request.getParameterValues(request_key)[i]);
			}
			m_count += request.getParameterValues(request_key).length;	//このコレクションクラスの要素数に加えた要素の数を加える
		}
		m_record = _CountRecords();
		_CalculateServerSideData();
		 System.out.println("--------------------<");
	}
	
	/**
	 * @brief		リクエストに含まれるレコード情報を取得し返す
	 * @param		なし
	 * @return		レコード数
	 */
	private int _CountRecords()
	{
		int count = -1;
		for(Map.Entry<String, String[]> e : m_data.entrySet())
		{
			count = e.getValue().length;
		}
		return count;
	}
	
	/**
	 * @brief		格納されているデータをすべて表示する
	 * @param		なし
	 * @return		なし
	 */
	public void ShowData()
	{
		System.out.println(">--------------------");
		System.out.println("@HashData.ShowData");
		for(Map.Entry<String, String[]> e : m_data.entrySet()){
			for(int i=0;i<e.getValue().length;i++){
				System.out.println("hash_key:" +String.format("%-20s", e.getKey())+ " value:" +e.getValue()[i]);
			}
		}
		System.out.println("--------------------<");
	}

	/**
	 * @brief		POSTされたデータのうち、修正が必要なキー名を修正する([])
	 * @param[in]	HttpServletRequest		request		
	 * @param[in]	String					prev_key	
	 * @return		なし
	 */	
	private	String _RenameKey(String prev_key)
	{
		System.out.println(">--------------------");
		System.out.println("@HashData._RenameKey");

		String new_key = "";
		System.out.println("prev_key: "+prev_key);
		new_key = prev_key.substring(0, prev_key.length()-2);
		System.out.println("new_key: "+new_key);

		System.out.println("--------------------<");
		return new_key;
		
	}
	
	/**
	 * @brief		POSTされたデータのうち、サーバーサイドで計算が必要なものをHashMapに格納する
	 * @param[in]	なし
	 * @return		なし
	 */	
	private void _CalculateServerSideData()
	{
		System.out.println(">--------------------");
		System.out.println("@HashData._CalculateServerSideData");
		if(m_data.containsKey("UPDATING_DAY"))
		{ 
			_AddParam_UPDATING_DAY();
		}
		if(m_data.containsKey("UPDATING_TIME"))
		{
			_AddParam_UPDATING_TIME();		
		}
		if(m_data.containsKey("REGIST_DAY"))
		{
			_AddParam_REGIST_DAY();
		}
		if(m_data.containsKey("REGIST_TIME"))
		{
			_AddParam_REGIST_TIME();
		}
		if(m_data.containsKey("UPDATING_ID"))
		{
			_AddParam_UPDATING_ID();
		}
		if(m_data.containsKey("REGISTRANT_ID"))
		{
			_AddParam_REGISTRANT_ID();
		}	
		System.out.println("--------------------<");
	}
	
	private void _AddParam_UPDATING_DAY()
	{
		String[] data = m_data.get("UPDATING_DAY");
		for(int i=0;i<data.length;i++)
		{
			data[i] =  MyTime.GetDate();
		}
		m_data.put("UPDATING_DAY", data);
	}
	
	private void _AddParam_UPDATING_TIME()
	{
		String[] data = m_data.get("UPDATING_TIME");
		for(int i=0;i<data.length;i++)
		{
			data[i] =  MyTime.GetTime();
		}
		m_data.put("UPDATING_TIME", data);
	}
	
	private void _AddParam_REGIST_DAY()
	{
		String[] data = m_data.get("REGIST_DAY");
		for(int i=0;i<data.length;i++)
		{
			data[i] =  MyTime.GetDate();
		}
		m_data.put("REGIST_DAY", data);
	}
	
	private void _AddParam_REGIST_TIME()
	{
		String[] data = m_data.get("REGIST_TIME");
		for(int i=0;i<data.length;i++)
		{
			data[i] =  MyTime.GetTime();
		}
		m_data.put("REGIST_TIME", data);
	}

	private void _AddParam_UPDATING_ID()
	{
		String[] data = m_data.get("UPDATING_ID");
		for(int i=0;i<data.length;i++)
		{
			data[i] =  "MASTER";
		}
		m_data.put("UPDATING_ID", data);
	}
	
	private void _AddParam_REGISTRANT_ID()
	{
		String[] data = m_data.get("REGISTRANT_ID");
		for(int i=0;i<data.length;i++)
		{
			data[i] =  "MASTER";
		}
		m_data.put("REGISTRANT_ID", data);
	}
	
	private boolean _CheckRequest()
	{
		boolean success = true;
		return success;
	}
	
	/**
	 * Field.
	 */
	private			HashMap<String, String[]>		m_data;			//格納データ
	private			int								m_record;
	private			int								m_count;		//格納している全要素数
}
