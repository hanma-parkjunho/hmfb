package hmfb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hmfb.core.dto.F0500200Dto;
import hmfb.service.F0500200Service;

@RestController
@RequestMapping("/hmfbApi")
public class F0500200Controller {

	@Autowired
	F0500200Service f0500200Service;
	
	/** 수취인 조회
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "api/0500200", method = RequestMethod.POST)
	public F0500200Dto f0500200( HttpServletRequest request,
                              HttpServletResponse response,
                              @RequestBody F0500200Dto dto) {
		F0500200Dto returnDto = new F0500200Dto();
		try {
			returnDto = f0500200Service.f0500200Service(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnDto;
	}
	
}
