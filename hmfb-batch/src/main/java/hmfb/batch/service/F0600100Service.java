package hmfb.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F0600100Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.framework.batch.biz.AbstractBatchService;

@Service
public class F0600100Service extends AbstractBatchService {
	@Autowired
	IntfService intfService;

    /** 거래명세통지
     * @param dto
     * @return
     * @throws Exception
     */
    public FirmReturnDto f0600100Service (F0600100Dto dto, String telemsgNo) {
    	// 송신 호출
    	FirmReturnDto rtnDto = intfService.sendTcp(InterfaceMapping.INF_FIRM_16, dto, telemsgNo);
        
        return rtnDto;
    }
}
