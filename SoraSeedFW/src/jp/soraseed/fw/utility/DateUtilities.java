package jp.soraseed.fw.utility;

import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * 日付用ユーティリティ
 * @author Tatsuya
 *
 */
public final class DateUtilities {

    private DateUtilities(){}

    /** 
     * 今日の日付を取得
     * @return　yyyymmdd
     */
    public static String getToday(){
        GregorianCalendar greCal = new GregorianCalendar();
        
        int year = greCal.get(Calendar.YEAR);
        String month = Integer.toString(greCal.get(Calendar.MONTH) + 1); //月は0から数えます
        if (month.length() == 1) {
          month = "0" + month;
        }
        String day = Integer.toString(greCal.get(Calendar.DATE));
        if (day.length() == 1) {
          day = "0" + day;
        }
        StringBuilder sb = new StringBuilder(10);
        sb.append(year).append(month).append(day);
        return sb.toString();
    }
    
    /**
     * 月の足し算
     * @param yyyyMM 年月
     * @param param 足す数
     * @return yyyyMM
     */
	public static String addMonth(String yyyyMM, int param){
		String date = "";
		if (yyyyMM.length() == 6) {
			int yearInt = Integer.parseInt(yyyyMM.substring(0, 4));
			int monthInt = Integer.parseInt(yyyyMM.substring(4, 6));
			GregorianCalendar greCal = new GregorianCalendar(yearInt, monthInt - 1, 1);
			greCal.add(Calendar.MONTH, param);
			// 年
			int year = greCal.get(Calendar.YEAR);
		    // 月
		    String month = Integer.toString(greCal.get(Calendar.MONTH) + 1);
		    if(month.length() == 1){
		    	month = "0" + month;
		    }
		    date = year + month;
		} else {
			date = yyyyMM;
		}
	    return date;
	}
    
	
    /**
     * 日付変換
     * @param date yyyyMMdd(アタマに0含む)
     * @param noYearFlag 戻り値に年を含まない場合はtrue
     * @return yyyy/MM/dd(アタマに0含まない)
     */
    public static String convertStrDate(String date, boolean noYearFlag) {
    	String convDate = "";
    	if (date == null || date.length() != 8) {
    		convDate = date;
    	} else {
    		String year = date.substring(0, 4);
    		String month = date.substring(4, 6);
    		String day = date.substring(6, 8);

    		if (month.startsWith("0")) {
    			month = month.substring(1);
    		}
    		if (day.startsWith("0")) {
    			day = day.substring(1);
    		}

    		if (noYearFlag) {
    			convDate = month + "/" + day;
    		} else {
    			convDate = year + "/" + month + "/" + day;
    		}

    	}
    	return convDate;
    }

}