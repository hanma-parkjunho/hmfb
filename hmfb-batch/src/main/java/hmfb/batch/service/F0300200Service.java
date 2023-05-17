package hmfb.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F0300200Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.framework.batch.biz.AbstractBatchService;

@Service
public class F0300200Service extends AbstractBatchService {
	@Autowired
	IntfService intfService;

    /** 경유계좌해지
     * @param dto
     * @return
     * @throws Exception
     */
    public FirmReturnDto f0300200Service (F0300200Dto dto, String telemsgNo) {
    	// 송신 호출
    	FirmReturnDto rtnDto = intfService.sendTcp(InterfaceMapping.INF_FIRM_08, dto, telemsgNo);
        
        return rtnDto;
    }
}
