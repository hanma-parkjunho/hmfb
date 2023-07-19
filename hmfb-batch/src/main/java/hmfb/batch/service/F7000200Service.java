package hmfb.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F7000200Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.dto.StdFirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.framework.batch.biz.AbstractBatchService;

@Service
public class F7000200Service extends AbstractBatchService {
	@Autowired
	IntfService intfService;

    /** 모계좌 잔액조회
     * @param dto
     * @return
     * @throws Exception
     */
    public StdFirmReturnDto f7000200Service (F7000200Dto dto, String telemsgNo) {
    	// 송신 호출
    	StdFirmReturnDto rtnDto = intfService.StdsendTcp(InterfaceMapping.INF_FIRM_20, dto, telemsgNo);
        
        return rtnDto;
    }
}
