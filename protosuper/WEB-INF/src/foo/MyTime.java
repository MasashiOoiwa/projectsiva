package foo;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class MyTime {

	/**
	 * @param args
	 */

		  public static String GetTime(int hour, int minute, int second){
			  String s_hour;
			  if(hour<10){
				  s_hour = "0"+String.valueOf(hour);
			  }else{
				  s_hour = String.valueOf(hour);
			  }
			  return s_hour +":"+ String.valueOf(minute)+":"+String.valueOf(second);
		  }

		  public static String GetTime(){
		        Calendar cal = Calendar.getInstance();
		        SimpleDateFormat sdf = new SimpleDateFormat("k':'m':'s");
		        String strDate = sdf.format(cal.getTime());

				return strDate;
		  }

		  public static String GetDate(){
		        Calendar cal = Calendar.getInstance();
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy':'MM':'dd");
		        String strDate = sdf.format(cal.getTime());

				return strDate;
		  }

		  public static String GetDate(int year, int month, int day){
			  return String.valueOf(year) + ":"+ String.valueOf(month)+":"+String.valueOf(day);
		  }


	}