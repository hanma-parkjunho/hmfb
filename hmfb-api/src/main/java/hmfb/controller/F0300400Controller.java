package hmfb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hmfb.core.dto.F0300400Dto;
import hmfb.service.F0300400Service;

@RestController
@RequestMapping("/hmfbApi")
public class F0300400Controller {

	@Autowired
	F0300400Service f0300400Service;
    
	@Value("${hmfb.firm.id}")
    private String firmId;
    
	/** 은행대리점등록
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "api/0300400", method = RequestMethod.POST)
	public F0300400Dto F0300400( HttpServletRequest request,
                              HttpServletResponse response,
                              @RequestBody F0300400Dto dto) {
		F0300400Dto returnDto = new F0300400Dto();
		dto.setOrgCode(firmId);	//기관코드
		try {
			returnDto = f0300400Service.f0300400Service(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnDto;
	}
	
}
