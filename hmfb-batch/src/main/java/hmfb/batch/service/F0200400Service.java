package hmfb.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F0200400Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.framework.batch.biz.AbstractBatchService;

@Service
public class F0200400Service extends AbstractBatchService {
	@Autowired
	IntfService intfService;

    /** 4자이체
     * @param dto
     * @return
     * @throws Exception
     */
    public FirmReturnDto f0200400Service (F0200400Dto dto, String telemsgNo) {
    	// 송신 호출
    	FirmReturnDto rtnDto = intfService.sendTcp(InterfaceMapping.INF_FIRM_06, dto, telemsgNo);
        
        return rtnDto;
    }
}
