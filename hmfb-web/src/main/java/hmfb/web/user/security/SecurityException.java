package hmfb.web.user.security;

import org.springframework.security.core.AuthenticationException;

/**

 * @FileName : SecurityException.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : SecurityException 클레스

 * @변경이력 :

 */

public class SecurityException extends AuthenticationException {
	
	private static final long serialVersionUID = 2291037287876808582L;

	/**
	
	  * @Method Name : SecurityException
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : SecurityException 생성자
	
	  * @변경이력 : 
	
	  */
	public SecurityException(String msg) {
		super(msg);
	}

	/**
	
	  * @Method Name : SecurityException
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : HmfbSecurityException 생성자
	
	  * @변경이력 : 
	
	  */
	public SecurityException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
