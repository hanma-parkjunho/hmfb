package hmfb.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F0500100Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.framework.batch.biz.AbstractBatchService;

@Service
public class F0500100Service extends AbstractBatchService {
	@Autowired
	IntfService intfService;

    /** 모계좌잔액조회
     * @param dto
     * @return
     * @throws Exception
     */
    public FirmReturnDto f0500100Service (F0500100Dto dto, String telemsgNo) {
    	// 송신 호출
    	FirmReturnDto rtnDto = intfService.sendTcp(InterfaceMapping.INF_FIRM_13, dto, telemsgNo);
        
        return rtnDto;
    }
}
