package hmfb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F0300500Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.core.utils.SequenceUtil;

@Service
public class F0300500Service {
	@Autowired
	IntfService intfService;

    /** 모계좌별가상계좌요청
     * @param dto
     * @return
     * @throws Exception
     */
    public F0300500Dto f0300500Service (F0300500Dto dto) throws Exception {
    	// 송신 호출
    	FirmReturnDto rtnDto = intfService.sendTcp(InterfaceMapping.INF_FIRM_11, dto, SequenceUtil.getNextValue());
        
        return (F0300500Dto)rtnDto.getRtnObj();
    }
}
