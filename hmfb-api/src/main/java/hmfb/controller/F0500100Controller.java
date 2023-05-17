package hmfb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hmfb.core.dto.F0500100Dto;
import hmfb.service.F0500100Service;

@RestController
@RequestMapping("/hmfbApi")
public class F0500100Controller {

	@Autowired
	F0500100Service f0500100Service;
	
	/** 모계좌/가상계좌 잔액조회
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "api/0500100", method = RequestMethod.POST)
	public F0500100Dto f0500100( HttpServletRequest request,
                              HttpServletResponse response,
                              @RequestBody F0500100Dto dto) {
		F0500100Dto returnDto = new F0500100Dto();
		try {
			returnDto = f0500100Service.f0500100Service(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnDto;
	}
	
}
