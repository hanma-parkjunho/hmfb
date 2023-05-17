package hmfb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F0500200Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.core.utils.SequenceUtil;

@Service
public class F0500200Service {
	@Autowired
	IntfService intfService;

    /** 수취인조회
     * @param dto
     * @return
     * @throws Exception
     */
    public F0500200Dto f0500200Service (F0500200Dto dto) throws Exception {
    	// 송신 호출
    	FirmReturnDto rtnDto = intfService.sendTcp(InterfaceMapping.INF_FIRM_14, dto, SequenceUtil.getNextValue());
        
        return (F0500200Dto)rtnDto.getRtnObj();
    }
}
