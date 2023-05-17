package hmfb.core.utils;

/**

 * @FileName : StringUtil.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : String Util 클레스

 * @변경이력 :

 */

public class StringUtil {
	
	/**
	
	  * @Method Name : isEmpty
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : String이 비었거나("") 혹은 null 인지 검증한다.
	
	  * @변경이력 : 
	
	  */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}
	
	/**
	
	  * @Method Name : remove
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 기준 문자열에 포함된 모든 대상 문자(char)를 제거한다.
	
	  * @변경이력 : 
	
	  */
	public static String remove(String str, char remove) {
		if (isEmpty(str) || str.indexOf(remove) == -1) {
			return str;
		}
		char[] chars = str.toCharArray();
		int pos = 0;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] != remove) {
				chars[pos++] = chars[i];
			}
		}
		return new String(chars, 0, pos);
	}

	/**
	
	  * @Method Name : removeMinusChar
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 문자열 내부의 마이너스 character(-)를 모두 제거한다.
	
	  * @변경이력 : 
	
	  */
	public static String removeMinusChar(String str) {
		return remove(str, '-');
	}
	
}
