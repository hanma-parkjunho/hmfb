package hmfb.core.exception.aspect;

import java.util.Locale;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import hmfb.core.dto.ErrorDto;
import hmfb.core.exception.ErrorCode;
import hmfb.core.exception.HmfbException;
import hmfb.core.exception.HmfbSecurityException;
import hmfb.core.log.service.LogService;
import hmfb.core.utils.DateUtil;
import lombok.extern.log4j.Log4j2;

/**

 * @FileName : AfterThrowingAspectException.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : Service Aspect AfterThrowing 클레스

 * @변경이력 :

 */
@Log4j2
@Aspect
@Component
public class AfterThrowingAspectException {
	
	@Autowired
	LogService logService;
	
	@Autowired
    private MessageSource messageSource;
	
	/**
	
	  * @Method Name : logAfterThrowingExceptionCall
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : Service Exception 발생 시 AfterThrowing 처리  
	
	  * @변경이력 : 
	
	  */
    @AfterThrowing(pointcut = "execution(* hmfb..*Service.*(..))", throwing = "ex")
    public void logAfterThrowingExceptionCall(JoinPoint joinPoint, Exception ex) throws Throwable {
    	if(ex instanceof HmfbException) {
	    	if(ex instanceof HmfbException && ((HmfbException)ex).getErrorCode() != null)
				setError(joinPoint, ex, ((HmfbException)ex).getErrorCode());
			else
				setError(joinPoint, ex);
    	} else {
    		if(!(ex instanceof HmfbSecurityException))
    			setError(joinPoint, ex);
    	}
    }
    
    /**
	
	  * @Method Name : setError
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 오류 로그 처리
	
	  * @변경이력 : 
	
	  */
    private void setError(JoinPoint joinPoint, Exception ex) {
		this.setError(joinPoint, ex, ErrorCode.E500);
	}
	
    /**
	
	  * @Method Name : setError
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 오류 로그 처리
	
	  * @변경이력 : 
	
	  */
	private void setError(JoinPoint joinPoint, Exception ex, ErrorCode errorCode) {
		
		String dttm = DateUtil.getCurrentTime();
		
		ErrorDto errorDto = ErrorDto.builder()
				.status(errorCode.getStatus())
				.path(joinPoint.getSignature().toLongString())
				.error(ObjectUtils.isEmpty(ex.getClass()) ? errorCode.getError() : ex.getClass().toString())
				.message(ObjectUtils.isEmpty(ex.getMessage()) ? messageSource.getMessage(errorCode.getMessage(), new Object[] {}, Locale.getDefault()) : ex.getMessage())
				.errorDt(dttm.substring(0, 10))
				.errorTm(dttm.substring(11))
				.build();
		
		logService.log("ERROR", errorDto);
		
		log.error(ex.getMessage(), ex.getCause());
		
	}
	
}