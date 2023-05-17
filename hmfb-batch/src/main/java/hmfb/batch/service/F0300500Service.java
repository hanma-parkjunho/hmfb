package hmfb.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F0300500Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.framework.batch.biz.AbstractBatchService;

@Service
public class F0300500Service extends AbstractBatchService {
	@Autowired
	IntfService intfService;

    /** 모계좌별가상계좌요청
     * @param dto
     * @return
     * @throws Exception
     */
    public FirmReturnDto f0300500Service (F0300500Dto dto, String telemsgNo) {
    	// 송신 호출
    	FirmReturnDto rtnDto = intfService.sendTcp(InterfaceMapping.INF_FIRM_11, dto, telemsgNo);
        
        return rtnDto;
    }
}
