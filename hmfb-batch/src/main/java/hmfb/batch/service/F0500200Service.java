package hmfb.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F0500200Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.framework.batch.biz.AbstractBatchService;

@Service
public class F0500200Service extends AbstractBatchService {
	@Autowired
	IntfService intfService;

    /** 수취인조회
     * @param dto
     * @return
     * @throws Exception
     */
    public FirmReturnDto f0500200Service (F0500200Dto dto, String telemsgNo) {
    	// 송신 호출
    	FirmReturnDto rtnDto = intfService.sendTcp(InterfaceMapping.INF_FIRM_14, dto, telemsgNo);
        
        return rtnDto;
    }
}
