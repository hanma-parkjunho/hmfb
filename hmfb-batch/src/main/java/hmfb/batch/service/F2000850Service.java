package hmfb.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F2000850Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.framework.batch.biz.AbstractBatchService;

@Service
public class F2000850Service extends AbstractBatchService {
	@Autowired
	IntfService intfService;

    /** 자동이체 청구
     * @param dto
     * @return
     * @throws Exception
     */
    public FirmReturnDto f2000850Service (F2000850Dto dto, String telemsgNo) {
    	// 송신 호출
    	FirmReturnDto rtnDto = intfService.sendTcp(InterfaceMapping.INF_FIRM_22, dto, telemsgNo);
        
        return rtnDto;
    }
}
