package hmfb.core.exception;

/**

 * @FileName : HmfbSecurityException.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : HmfbSecurityException 클레스

 * @변경이력 :

 */

public class HmfbSecurityException extends Exception {
	
	private static final long serialVersionUID = 2291037287876808582L;

	/**
	
	  * @Method Name : HmfbSecurityException
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : HmfbSecurityException 생성자
	
	  * @변경이력 : 
	
	  */
	public HmfbSecurityException(String msg) {
		super(msg);
	}

	/**
	
	  * @Method Name : HmfbSecurityException
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : HmfbSecurityException 생성자
	
	  * @변경이력 : 
	
	  */
	public HmfbSecurityException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
