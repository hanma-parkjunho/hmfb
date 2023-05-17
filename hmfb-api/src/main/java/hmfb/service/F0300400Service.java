package hmfb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F0300400Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.core.utils.SequenceUtil;

@Service
public class F0300400Service {
	@Autowired
	IntfService intfService;

    /** 은행대리점등록
     * @param dto
     * @return
     * @throws Exception
     */
    public F0300400Dto f0300400Service (F0300400Dto dto) throws Exception {
    	// 송신 호출
    	FirmReturnDto rtnDto = intfService.sendTcp(InterfaceMapping.INF_FIRM_10, dto, SequenceUtil.getNextValue());
        
        return (F0300400Dto)rtnDto.getRtnObj();
    }
}
