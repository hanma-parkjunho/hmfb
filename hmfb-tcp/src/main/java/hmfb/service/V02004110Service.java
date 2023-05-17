package hmfb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hmfb.core.dto.V02004110Dto;
import hmfb.core.dto.VReturnDto;
import hmfb.core.service.VBaseService;

//수취인조회
public class V02004110Service implements VBaseService{
	private static final Logger CLOG = LoggerFactory.getLogger("CLOGGER");
	
	@Override
	public void process(VReturnDto dto) {
        V02004110Dto receiveDto = (V02004110Dto)dto.getRtnObj();
        dto.getCommonDto().setSndRcvFlag("1");

        
        receiveDto.setRCPMNY_ACNUT_NM("KLID001");
        dto.setRtnObj(receiveDto);
        
        CLOG.info("Service 호출됨..!"+receiveDto.getMessage());
		
	}

}
