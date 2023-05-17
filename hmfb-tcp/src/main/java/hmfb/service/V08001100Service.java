package hmfb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hmfb.core.dto.V08001100Dto;
import hmfb.core.dto.VReturnDto;
import hmfb.core.service.VBaseService;

//통신망관리
public class V08001100Service implements VBaseService{
	private static final Logger CLOG = LoggerFactory.getLogger("CLOGGER");
	
	@Override
	public void process(VReturnDto dto) {
        V08001100Dto receiveDto = (V08001100Dto)dto.getRtnObj();
        dto.getCommonDto().setSndRcvFlag("1");
        CLOG.info("Service 호출됨..!"+receiveDto.getMessage());
		
	}

}
