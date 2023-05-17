package hmfb.web.advice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import hmfb.core.exception.ErrorCode;
import hmfb.core.exception.HmfbException;
import hmfb.web.user.security.SecurityProperties;
import hmfb.web.user.security.UserDetail;
import hmfb.web.utils.SecurityUtils;

/**

 * @FileName : HmfbControllerAdvice.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : Controller Advice 클레스

 * @변경이력 :

 */

@ControllerAdvice(annotations = Controller.class)
public class WebModelControllerAdvice {
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@ModelAttribute
    public void handleRequest(@RequestParam(required = false) String selUprnSeqNo, 
    		                  @RequestParam(required = false) String selSeqNo, 
    		                  HttpServletRequest request,
    		                  HttpServletResponse response,
    		                  Model model) throws HmfbException {
		
		if(SecurityUtils.isAuthenticated()) {
			String reqUri = ((HttpServletRequest)request).getRequestURI();
			UserDetail user = SecurityUtils.getPrincipal();
			if(!securityProperties.getDefaultSuccessUrl().equals(reqUri) && user.getAllMenuUrls().indexOf(reqUri) > -1)
				if(user.getAuthMenuUrls().indexOf(reqUri) == -1)
					throw new HmfbException(ErrorCode.E403);
		}
		
		model.addAttribute("selUprnSeqNo", selUprnSeqNo);
		model.addAttribute("selSeqNo", selSeqNo);
		model.addAttribute("loginPage", securityProperties.getLoginPage());
		model.addAttribute("loginProcessingUrl", securityProperties.getLoginProcessingUrl());
		model.addAttribute("defaultSuccessUrl", securityProperties.getDefaultSuccessUrl());
		model.addAttribute("logoutUrl", securityProperties.getLogoutUrl());
		
    }
}
