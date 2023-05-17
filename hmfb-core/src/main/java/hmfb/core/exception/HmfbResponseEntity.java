package hmfb.core.exception;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import hmfb.core.dto.ErrorDto;

/**

 * @FileName : HmfbResponseEntity.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : HmfbResponseEntity 클레스

 * @변경이력 :

 */

public class HmfbResponseEntity extends ResponseEntity<Object> {
	
	/**
	
	  * @Method Name : HmfbResponseEntity
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : HmfbResponseEntity 생성자
	
	  * @변경이력 : 
	
	  */
	public HmfbResponseEntity(Object body, HttpStatus status) {
		super(body, status);
	}
	
	/**
	
	  * @Method Name : error
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 기본 오류처리 ResponseEntity
	
	  * @변경이력 : 
	
	  */
	public static ResponseEntity<?> error() {
		return (ResponseEntity<?>) ResponseEntity.internalServerError();
	}
	
	/**
	
	  * @Method Name : error
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 기본 오류처리 ResponseEntity
	
	  * @변경이력 : 
	
	  */
	public static ResponseEntity<?> error(String message) {
		return ResponseEntity.status(HttpStatus.valueOf(ErrorCode.E500.getStatus()))
				.body(ErrorDto.builder()
						.status(ErrorCode.E500.getStatus())
						.error(ErrorCode.E500.getError())
						.message(message)
						.build());
	}
	
	/**
	
	  * @Method Name : error
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : ErrorCode 전달 받아 오류처리 ResponseEntity
	
	  * @변경이력 : 
	
	  */
	public static <T> ResponseEntity<ErrorDto> error(MessageSource messageSource, ErrorCode errorCode) {
		return ResponseEntity.status(HttpStatus.valueOf(errorCode.getStatus()))
				.body(ErrorDto.builder()
						.status(errorCode.getStatus())
						.error(errorCode.getError())
						.message(messageSource.getMessage(errorCode.getMessage(), new Object[] {}, Locale.getDefault()))
						.build());
	}
	
}
