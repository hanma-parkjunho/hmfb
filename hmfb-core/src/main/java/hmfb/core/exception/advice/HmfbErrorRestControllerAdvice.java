package hmfb.core.exception.advice;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import hmfb.core.dto.ErrorDto;
import hmfb.core.exception.ErrorCode;
import hmfb.core.exception.HmfbException;
import hmfb.core.log.service.LogService;
import hmfb.core.utils.DateUtil;
import lombok.extern.log4j.Log4j2;

/**

 * @FileName : HmfbRestControllerAdvice.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : RestControllerAdvice 오류 처리 Advice 클레스

 * @변경이력 :

 */
@Log4j2
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(annotations = RestController.class)
public class HmfbErrorRestControllerAdvice {
	
	@Autowired
	LogService logService;
	
	@Autowired
    private MessageSource messageSource;
	
	/**
	
	  * @Method Name : handleRuntimeException
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : RuntimeException ExceptionHandler
	
	  * @변경이력 : 
	
	  */
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Map<String, Object>> handleRuntimeException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		return setError(request, response, ex);
	}
	
	/**
	
	  * @Method Name : handleException
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : Exception ExceptionHandler
	
	  * @변경이력 : 
	
	  */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		return setError(request, response, ex);
	}
	
	/**
	
	  * @Method Name : HmfbException
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : HmfbException ExceptionHandler
	
	  * @변경이력 : 
	
	  */
	@ExceptionHandler(HmfbException.class)
	public ResponseEntity<Map<String, Object>> handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		if(((HmfbException)ex).getErrorCode() != null)
			return setError(request, response, ex, ((HmfbException)ex).getErrorCode());
		else
			return setError(request, response, ex);
	}
	
	/**
	
	  * @Method Name : setError
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 오류 설정 및 로그 처리
	
	  * @변경이력 : 
	
	  */
	private ResponseEntity<Map<String, Object>> setError(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		return this.setError(request, response, ex, ErrorCode.E500);
	}
	
	/**
	
	  * @Method Name : setError
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 오류 설정 및 로그 처리
	
	  * @변경이력 : 
	
	  */
	private ResponseEntity<Map<String, Object>> setError(HttpServletRequest request, HttpServletResponse response, Exception ex, ErrorCode errorCode) {		                                   		
		
		String dttm = DateUtil.getCurrentTime();
		
		String message = messageSource.getMessage(errorCode.getMessage(), new Object[] {}, Locale.getDefault());
		
		ErrorDto errorDto = ErrorDto.builder()
				.status(errorCode.getStatus())
				.path(request.getRequestURI())
				.error(ObjectUtils.isEmpty(ex.getClass()) ? errorCode.getError() : ex.getClass().toString())
				.message(ObjectUtils.isEmpty(ex.getMessage()) ? message : ex.getMessage())
				.errorDt(dttm.substring(0, 10))
				.errorTm(dttm.substring(11))
				.build();
		
		logService.log("ERROR", errorDto);
		
		log.error(ex.getMessage(), ex.getCause());
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		model.put("path", errorDto.getPath());
		model.put("status", errorDto.getStatus());
		model.put("error", errorDto.getError());
		model.put("message", message);
		model.put("regDt", errorDto.getErrorDt());
		model.put("regTm", errorDto.getErrorTm());
		
		return ResponseEntity.status(errorCode.getStatus()).body(model);
		
	}
	
}
