package hmfb.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**

 * @FileName : DateUtil.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : Date Util 클레스

 * @변경이력 :

 */

public class DateUtil {
	
	private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	
	private static final String DTTM = "yyyyMMddHHmmss";
	
	/**
	
	  * @Method Name : getCurrentDttm
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 현재 일자를 yyyy-MM-dd 형식으로 반환
	
	  * @변경이력 : 
	
	  */
	public static String getCurrentDttm() {
		return getCurrentTime(DTTM);
	}
	
	/**
	
	  * @Method Name : getCurrentTime
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 현재 TIMESTAMP를 yyyy-MM-dd HH:mm:ss.SSS 형식으로  반환
	
	  * @변경이력 : 
	
	  */
	public static String getCurrentTime() {
		return getCurrentTime(TIMESTAMP_FORMAT);
	}
	
	/**
	
	  * @Method Name : getCurrentTime
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 현재 TIMESTAMP를 전달받은 형식으로  반환
	
	  * @변경이력 : 
	
	  */
	public static String getCurrentTime(String formatter) {
		SimpleDateFormat fmt = new SimpleDateFormat(formatter);
		fmt.setTimeZone(getTimeZone());
		return fmt.format(new Date(System.currentTimeMillis()));
	}
	
	/**
	
	  * @Method Name : getTimeZone
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 기본으로 서정된 TimeZon을 반환
	
	  * @변경이력 : 
	
	  */
	public static TimeZone getTimeZone() {
		return TimeZone.getDefault();
	}
	
	/**
	
	  * @Method Name : addYearMonthDay
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 날짜 문자열을 입력 받아 년, 월, 일을 증감한다.
	
	  * @변경이력 : 
	
	  */
	public static String addYearMonthDay(String sDate, int year, int month, int day) {

		String dateStr = validChkDate(sDate);

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
		try {
			cal.setTime(sdf.parse(dateStr));
		} catch (ParseException e) {
			throw new IllegalArgumentException("Invalid date format: " + dateStr);
		}

		if (year != 0) {
			cal.add(Calendar.YEAR, year);
		}
		if (month != 0) {
			cal.add(Calendar.MONTH, month);
		}
		if (day != 0) {
			cal.add(Calendar.DATE, day);
		}
		
		return sdf.format(cal.getTime());
	}

	/**
	
	  * @Method Name : addYear
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 날짜 문자열을 입력 받아 년을 증감한다.
	
	  * @변경이력 : 
	
	  */
	public static String addYear(String dateStr, int year) {
		return addYearMonthDay(dateStr, year, 0, 0);
	}

	/**
	
	  * @Method Name : addMonth
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 날짜 문자열을 입력 받아 월을 증감한다.
	
	  * @변경이력 : 
	
	  */
	public static String addMonth(String dateStr, int month) {
		return addYearMonthDay(dateStr, 0, month, 0);
	}

	/**
	
	  * @Method Name : addDay
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 날짜 문자열을 입력 받아 일(day)을 증감한다.
	
	  * @변경이력 : 
	
	  */
	public static String addDay(String dateStr, int day) {
		return addYearMonthDay(dateStr, 0, 0, day);
	}

	/**
	
	  * @Method Name : formatDate
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : yyyyMMdd 형식의 날짜문자열을 원하는 캐릭터(ch)로 쪼개 돌려준다
	
	  * @변경이력 : 
	
	  */
	public static String formatDate(String sDate, String ch) {
		String dateStr = validChkDate(sDate);

		String str = dateStr.trim();
		String yyyy = "";
		String mm = "";
		String dd = "";

		if (str.length() == 8) {
			yyyy = str.substring(0, 4);
			if (yyyy.equals("0000")) {
				return "";
			}

			mm = str.substring(4, 6);
			if (mm.equals("00")) {
				return yyyy;
			}

			dd = str.substring(6, 8);
			if (dd.equals("00")) {
				return yyyy + ch + mm;
			}
			
			return yyyy + ch + mm + ch + dd;
		
		} else if (str.length() == 6) {
			yyyy = str.substring(0, 4);
			if (yyyy.equals("0000")) {
				return "";
			}

			mm = str.substring(4, 6);
			if (mm.equals("00")) {
				return yyyy;
			}

			return yyyy + ch + mm;
			
		} else if (str.length() == 4) {
			yyyy = str.substring(0, 4);
			if (yyyy.equals("0000")) {
				return "";
			} else {
				return yyyy;
			}
		} else {
			return "";
		}
	}

	/**
	
	  * @Method Name : formatDate
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : HH24MISS 형식의 시간문자열을 원하는 캐릭터(ch)로 쪼개 돌려준다
	
	  * @변경이력 : 
	
	  */
	public static String formatTime(String sTime, String ch) {
		String timeStr = validChkTime(sTime);
		return timeStr.substring(0, 2) + ch + timeStr.substring(2, 4) + ch + timeStr.substring(4, 6);
	}
	
	/**
	
	  * @Method Name : validChkDate
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 입력된 일자 문자열을 확인하고 8자리로 리턴
	
	  * @변경이력 : 
	
	  */
	public static String validChkDate(String dateStr) {
		if (dateStr == null || !(dateStr.trim().length() == 8 || dateStr.trim().length() == 10)) {
			throw new IllegalArgumentException("Invalid date format: " + dateStr);
		}
				
		if (dateStr.length() == 10) {
			return StringUtil.removeMinusChar(dateStr);
		}
		
		return dateStr;
	}
	
	/**
	
	  * @Method Name : validChkTime
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 입력된 일자 문자열을 확인하고 8자리로 리턴
	
	  * @변경이력 : 
	
	  */
	public static String validChkTime(String timeStr) {
		if (timeStr == null || !(timeStr.trim().length() == 4)) {
			throw new IllegalArgumentException("Invalid time format: " + timeStr);
		}
		
		if (timeStr.length() == 5) {
			timeStr = StringUtil.remove(timeStr, ':');
		}

		return timeStr;
	}
	
}
