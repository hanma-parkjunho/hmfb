package hmfb.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F0600200Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.framework.batch.biz.AbstractBatchService;

@Service
public class F0600200Service extends AbstractBatchService {
	@Autowired
	IntfService intfService;

    /** 거래명세통지결번요청
     * @param dto
     * @return
     * @throws Exception
     */
    public FirmReturnDto f0600200Service (F0600200Dto dto, String telemsgNo) {
    	// 송신 호출
    	FirmReturnDto rtnDto = intfService.sendTcp(InterfaceMapping.INF_FIRM_17, dto, telemsgNo);
        
        return rtnDto;
    }
}
