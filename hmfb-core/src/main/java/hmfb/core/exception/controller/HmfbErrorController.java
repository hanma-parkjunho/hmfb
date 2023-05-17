package hmfb.core.exception.controller;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import hmfb.core.dto.ErrorDto;
import hmfb.core.exception.ErrorCode;
import hmfb.core.log.service.LogService;
import hmfb.core.utils.DateUtil;
import lombok.extern.log4j.Log4j2;

/**

 * @FileName : HmfbErrorController.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : 서블릿 예외 처리 클레스

 * @변경이력 :

 */

@Log4j2
@Controller
public class HmfbErrorController implements ErrorController {
	
	@Autowired
	LogService logService;
	
	@Autowired
    private MessageSource messageSource;
	
	/**
	
	  * @Method Name : handleError
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 오류 처리  
	
	  * @변경이력 : 
	
	  */
    @RequestMapping(value = "/error")
    public String handleError(HttpServletRequest request, Model model) {
    	Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(status != null){
            int statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.FORBIDDEN.value()){
            	setError(request, model, ErrorCode.E403);
            } else if(statusCode == HttpStatus.NOT_FOUND.value()){
            	setError(request, model, ErrorCode.E404);
            } else {
            	setError(request, model, ErrorCode.E500);
            }
        } else {
        	setError(request, model, ErrorCode.E500);
        }
        return "error/error";
    }
	
    /**
	
	  * @Method Name : setError
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 오류 로그 처리
	
	  * @변경이력 : 
	
	  */
    private void setError(HttpServletRequest request, Model model, ErrorCode errorCode) {
    	
    	String dttm = DateUtil.getCurrentTime();
    	
    	ErrorDto errorDto = ErrorDto.builder()
				.status(errorCode.getStatus())
				.path(request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI).toString())
				.error(errorCode.getError())
				.message(errorCode.getMessage())
				.errorDt(dttm.substring(0, 10))
				.errorTm(dttm.substring(11))
				.build();
		
		model.addAttribute("path", errorDto.getPath());
		model.addAttribute("status", errorDto.getStatus());
		model.addAttribute("error", errorDto.getError());
		model.addAttribute("message", messageSource.getMessage(errorDto.getMessage(), new Object[] {}, Locale.getDefault()));
		model.addAttribute("regDt", errorDto.getErrorDt());
		model.addAttribute("regTm", errorDto.getErrorTm());
		
		log.error(model.toString());
	}
    
}
