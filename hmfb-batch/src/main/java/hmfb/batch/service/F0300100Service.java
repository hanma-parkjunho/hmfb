package hmfb.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F0300100Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.framework.batch.biz.AbstractBatchService;

@Service
public class F0300100Service extends AbstractBatchService {
	@Autowired
	IntfService intfService;

    /** 경유계좌등록
     * @param dto
     * @return
     * @throws Exception
     */
    public FirmReturnDto f0300100Service (F0300100Dto dto, String telemsgNo) {
    	// 송신 호출
    	FirmReturnDto rtnDto = intfService.sendTcp(InterfaceMapping.INF_FIRM_07, dto, telemsgNo);
        
        return rtnDto;
    }
}
