package hmfb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F0500300Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.core.utils.SequenceUtil;

@Service
public class F0500300Service {
	@Autowired
	IntfService intfService;

    /** 혜지예상조회
     * @param dto
     * @return
     * @throws Exception
     */
    public F0500300Dto f0500300Service (F0500300Dto dto) throws Exception {
    	// 송신 호출
    	FirmReturnDto rtnDto = intfService.sendTcp(InterfaceMapping.INF_FIRM_15, dto, SequenceUtil.getNextValue());
        
        return (F0500300Dto)rtnDto.getRtnObj();
    }
}
