package hmfb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hmfb.core.dto.F0300500Dto;
import hmfb.service.F0300500Service;

@RestController
@RequestMapping("/hmfbApi")
public class F0300500Controller {

	@Autowired
	F0300500Service f0300500Service;
	
	@Value("${hmfb.firm.id}")
    private String firmId;
	
	/** 모계좌별가상계좌요청
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "api/0300500", method = RequestMethod.POST)
	public F0300500Dto f0300500( HttpServletRequest request,
                              HttpServletResponse response,
                              @RequestBody F0300500Dto dto) {
		F0300500Dto returnDto = new F0300500Dto();
		dto.setOrgCode(firmId);	//기관코드
		try {
			returnDto = f0300500Service.f0300500Service(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnDto;
	}
	
}
