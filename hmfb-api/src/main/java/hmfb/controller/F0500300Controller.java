package hmfb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hmfb.core.dto.F0500300Dto;
import hmfb.service.F0500300Service;

@RestController
@RequestMapping("/hmfbApi")
public class F0500300Controller {

	@Autowired
	F0500300Service f0500300Service;
	
	/** 혜지예상조회조회
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "api/0500300", method = RequestMethod.POST)
	public F0500300Dto f0500300( HttpServletRequest request,
                              HttpServletResponse response,
                              @RequestBody F0500300Dto dto) {
		F0500300Dto returnDto = new F0500300Dto();
		try {
			returnDto = f0500300Service.f0500300Service(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnDto;
	}
	
}
