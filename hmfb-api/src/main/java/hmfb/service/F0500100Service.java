package hmfb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F0500100Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.core.utils.SequenceUtil;

@Service
public class F0500100Service {
	@Autowired
	IntfService intfService;

    /** 모계좌/가상계좌 잔액조회
     * @param dto
     * @return
     * @throws Exception
     */
    public F0500100Dto f0500100Service (F0500100Dto dto) throws Exception {
    	// 송신 호출
    	FirmReturnDto rtnDto = intfService.sendTcp(InterfaceMapping.INF_FIRM_13, dto, SequenceUtil.getNextValue());
        
        return (F0500100Dto)rtnDto.getRtnObj();
    }
}
