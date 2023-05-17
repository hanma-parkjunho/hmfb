package hmfb.core.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**

 * @FileName : MybaitsUtil.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : Mybaits Util 클레스

 * @변경이력 :

 */

public class MybaitsUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(MybaitsUtil.class); 
	
	/**
	
	  * @Method Name : isEmpty
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : Empty 여부를 확인한다.
	
	  * @변경이력 : 
	
	  */
	public static boolean isEmpty(Object o) throws IllegalArgumentException {
		try {
			if(o == null) return true;
		
			if(o instanceof String) {
				if(((String)o).length() == 0){
					return true;
				}
			} else if(o instanceof Collection) {
				if(((Collection<?>)o).isEmpty()){
				return true;
				}
			} else if(o.getClass().isArray()) {
				if(Array.getLength(o) == 0){
				return true;
				}
			} else if(o instanceof Map) {
				if(((Map<?,?>)o).isEmpty()){
				return true;
				}
			}else {
				return false;
			}
			
			return false;
		//2017.03.03 	조성원 	시큐어코딩(ES)-오류 메시지를 통한 정보노출[CWE-209]
		} catch(IllegalArgumentException e) {
			logger.error("[IllegalArgumentException] Try/Catch...usingParameters Runing : "+ e.getMessage());
		} catch(Exception e) {
			logger.error("["+e.getClass()+"] Try/Catch...Exception : " + e.getMessage());
		}
		return false;
	}

	/**
	
	  * @Method Name : isNotEmpty
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : Not Empty 여부를 확인한다.
	
	  * @변경이력 : 
	
	  */
	public static boolean isNotEmpty(Object o) {
		return !isEmpty(o);
	}

	/**
	
	  * @Method Name : isEquals
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : Equal 여부를 확인한다.
	
	  * @변경이력 : 
	
	  */
    public static boolean isEquals(Object obj, Object obj2){
    	if(isEmpty(obj)) return false;

    	if(obj instanceof String && obj2 instanceof String) {
    		if( (String.valueOf(obj)).equals( String.valueOf(obj2) )){
				return true;
			}
    	}else if(obj instanceof String && obj2 instanceof Character) {
     		if( (String.valueOf(obj) ).equals( String.valueOf(obj2) )){
     			return true;
     		}
    	}else if(obj instanceof String && obj2 instanceof Integer) {
    		if( (String.valueOf(obj)).equals( String.valueOf((Integer)obj2) )){
				return true;
			}
    		
    	}else if(obj instanceof Integer && obj2 instanceof String) {
    		if( (String.valueOf(obj2)).equals( String.valueOf((Integer)obj) )){
				return true;
			}
		} else if(obj instanceof Integer && obj instanceof Integer) {
    		if((Integer)obj == (Integer)obj2){
				return true;
			}
		}
    	
        return false;	
    }
   
    /**
	
	  * @Method Name : isEqualsStr
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : String의 Equal 여부를 확인한다.
	
	  * @변경이력 : 
	
	  */
    public static boolean isEqualsStr(Object obj, String s){
    	if(isEmpty(obj)) return false;
    	
    	if(s.equals(String.valueOf(obj))){
    		 return true;
    	}
        return false;
    }
    
}
