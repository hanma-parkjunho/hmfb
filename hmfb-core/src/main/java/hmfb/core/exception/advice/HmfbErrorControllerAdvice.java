package hmfb.core.exception.advice;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import hmfb.core.dto.ErrorDto;
import hmfb.core.exception.ErrorCode;
import hmfb.core.exception.HmfbException;
import hmfb.core.log.service.LogService;
import hmfb.core.utils.DateUtil;
import lombok.extern.log4j.Log4j2;

/**

 * @FileName : HmfbControllerAdvice.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : Controller Advice 클레스

 * @변경이력 :

 */
@Log4j2
@Order(Ordered.LOWEST_PRECEDENCE)
@ControllerAdvice(annotations = Controller.class)
public class HmfbErrorControllerAdvice {
	
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
	public String handleRuntimeException(HttpServletRequest request, HttpServletResponse response, Model model, Exception ex) {
		setError(request, response, model, ex);
		return "error/error";
	}
	
	/**
	
	  * @Method Name : handleException
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : Exception ExceptionHandler
	
	  * @변경이력 : 
	
	  */
	@ExceptionHandler(Exception.class)
	public String handleException(HttpServletRequest request, HttpServletResponse response, Model model, Exception ex) {
		this.setError(request, response, model, ex);
		return "error/error";
	}
	
	/**
	
	  * @Method Name : handleException
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : HmfbException ExceptionHandler
	
	  * @변경이력 : 
	
	  */
	@ExceptionHandler(HmfbException.class)
	public String handleException(Exception ex, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		if(((HmfbException)ex).getErrorCode() != null)
			this.setError(request, response, model, ex, ((HmfbException)ex).getErrorCode());
		else
			this.setError(request, response, model, ex);
		
		return "error/error";
	}
	
	/**
	
	  * @Method Name : setError
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 오류 로그 처리
	
	  * @변경이력 : 
	
	  */
	private void setError(HttpServletRequest request, HttpServletResponse response, Model model, Exception ex) {
		this.setError(request, response, model, ex, ErrorCode.E500);
	}
	
	/**
	
	  * @Method Name : setError
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 오류 로그 처리
	
	  * @변경이력 : 
	
	  */
	
	private void setError(HttpServletRequest request, HttpServletResponse response, Model model, Exception ex, ErrorCode errorCode) {
		
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
		
		model.addAttribute("path", errorDto.getPath());
		model.addAttribute("status", errorDto.getStatus());
		model.addAttribute("error", errorDto.getError());
		model.addAttribute("message", message);
		model.addAttribute("regDt", errorDto.getErrorDt());
		model.addAttribute("regTm", errorDto.getErrorTm());
		
	}
	
}
