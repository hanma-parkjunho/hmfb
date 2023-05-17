package hmfb.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F0200200Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.framework.batch.biz.AbstractBatchService;

@Service
public class F0200200Service extends AbstractBatchService {
	@Autowired
	IntfService intfService;

    /** 2자이체
     * @param dto
     * @return
     * @throws Exception
     */
    public FirmReturnDto f0200200Service (F0200200Dto dto, String telemsgNo) {
    	// 송신 호출
    	FirmReturnDto rtnDto = intfService.sendTcp(InterfaceMapping.INF_FIRM_04, dto, telemsgNo);
        
        return rtnDto;
    }
}
