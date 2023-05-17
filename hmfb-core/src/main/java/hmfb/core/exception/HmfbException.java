package hmfb.core.exception;

import java.text.MessageFormat;
import java.util.Locale;
import org.springframework.context.MessageSource;
import lombok.Getter;
import lombok.Setter;

/**

 * @FileName : HmfbException.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : 한마펌뱅킹 HmfbException 클레스

 * @변경이력 :

 */

@Getter
@Setter
public class HmfbException extends Exception {
	
	private static final long serialVersionUID = -6472603809674646573L;

	protected ErrorCode errorCode = null;
	protected String message = null;
	protected String messageKey = null;
	protected Object[] messageParameters = null;
	protected Exception wrappedException = null;
	
	/**
	
	  * @Method Name : HmfbException
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : HmfbException 생성자
	
	  * @변경이력 : 
	
	  */
	public HmfbException() {
	}
	
	/**
	
	  * @Method Name : HmfbException
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : HmfbException 생성자
	
	  * @변경이력 : 
	
	  */
	public HmfbException(ErrorCode errorCode) {
		super();
		this.errorCode = errorCode;
	}
	
	/**
	
	  * @Method Name : HmfbException
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : HmfbException 생성자
	
	  * @변경이력 : 
	
	  */
	public HmfbException(String message) {
		this(message, null, null);
		this.message = message;
	}
	
	/**
	
	  * @Method Name : HmfbException
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : HmfbException 생성자
	
	  * @변경이력 : 
	
	  */
	public HmfbException(Throwable wrappedException) {
		this(wrappedException.getMessage(), null, wrappedException);
	}

	/**
	
	  * @Method Name : HmfbException
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : HmfbException 생성자
	
	  * @변경이력 : 
	
	  */
	public HmfbException(ErrorCode errorCode, String message) {
		super(message);
		this.message = message;
		this.errorCode = errorCode;
	}
	
	/**
	
	  * @Method Name : HmfbException
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : HmfbException 생성자
	
	  * @변경이력 : 
	
	  */
	public HmfbException(String message, Throwable wrappedException) {
		this(message, null, wrappedException);
		this.message = message;
	}
	
	/**
	
	  * @Method Name : HmfbException
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : HmfbException 생성자
	
	  * @변경이력 : 
	
	  */
	public HmfbException(ErrorCode errorCode, Throwable wrappedException) {
		this(wrappedException.getMessage(), null, wrappedException);
		this.errorCode = errorCode;
		this.message = wrappedException.getMessage();
	}
	
	/**
	
	  * @Method Name : HmfbException
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : HmfbException 생성자
	
	  * @변경이력 : 
	
	  */
	public HmfbException(ErrorCode errorCode, String message, Throwable wrappedException) {
		this(message, null, wrappedException);
		this.errorCode = errorCode;
		this.message = message;
	}
	
	/**
	
	  * @Method Name : HmfbException
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : HmfbException 생성자
	
	  * @변경이력 : 
	
	  */
	public HmfbException(String message, Object[] messageParameters, Throwable wrappedException) {
		super(wrappedException);
		String userMessage = message;
		if (messageParameters != null) {
			userMessage = MessageFormat.format(message, messageParameters);
		}
		this.message = userMessage;
	}

	/**
	
	  * @Method Name : HmfbException
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : HmfbException 생성자
	
	  * @변경이력 : 
	
	  */
	public HmfbException(MessageSource messageSource, String messageKey) {
		this(messageSource, messageKey, null, null, Locale.getDefault(), null);
	}

	/**
	
	  * @Method Name : HmfbException
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : HmfbException 생성자
	
	  * @변경이력 : 
	
	  */
	public HmfbException(MessageSource messageSource, String messageKey, Throwable wrappedException) {
		this(messageSource, messageKey, null, null, Locale.getDefault(), wrappedException);
	}

	/**
	
	  * @Method Name : HmfbException
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : HmfbException 생성자
	
	  * @변경이력 : 
	
	  */
	public HmfbException(MessageSource messageSource, String messageKey, Locale locale, Throwable wrappedException) {
		this(messageSource, messageKey, null, null, locale, wrappedException);
	}

	/**
	
	  * @Method Name : HmfbException
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : HmfbException 생성자
	
	  * @변경이력 : 
	
	  */
	public HmfbException(MessageSource messageSource, String messageKey, Object[] messageParameters, Locale locale, Throwable wrappedException) {
		this(messageSource, messageKey, messageParameters, null, locale, wrappedException);
	}

	/**
	
	  * @Method Name : HmfbException
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : HmfbException 생성자
	
	  * @변경이력 : 
	
	  */
	public HmfbException(MessageSource messageSource, String messageKey, Object[] messageParameters, Throwable wrappedException) {
		this(messageSource, messageKey, messageParameters, null, Locale.getDefault(), wrappedException);
	}

	/**
	
	  * @Method Name : HmfbException
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : HmfbException 생성자
	
	  * @변경이력 : 
	
	  */
	public HmfbException(MessageSource messageSource, String messageKey, Object[] messageParameters, String message, Throwable wrappedException) {
		this(messageSource, messageKey, messageParameters, message, Locale.getDefault(), wrappedException);
	}

	/**
	
	  * @Method Name : HmfbException
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : HmfbException 생성자
	
	  * @변경이력 : 
	
	  */
	public HmfbException(MessageSource messageSource, String messageKey, Object[] messageParameters, String message, Locale locale, Throwable wrappedException) {
		super(wrappedException);
		this.messageKey = messageKey;
		this.messageParameters = messageParameters;
		this.message = messageSource.getMessage(messageKey, messageParameters, message, locale);
	}
	
}
