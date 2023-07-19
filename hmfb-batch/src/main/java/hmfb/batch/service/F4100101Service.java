package hmfb.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F4100101Dto;
import hmfb.core.dto.StdFirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.framework.batch.biz.AbstractBatchService;

@Service
public class F4100101Service extends AbstractBatchService {
	@Autowired
	IntfService intfService;

    /** 예금거래(교환어음) 명세통지
     * @param dto
     * @return
     * @throws Exception
     */
    public StdFirmReturnDto f4100101Service (F4100101Dto dto, String telemsgNo) {
    	// 송신 호출
    	StdFirmReturnDto rtnDto = intfService.StdsendTcp(InterfaceMapping.INF_FIRM_20, dto, telemsgNo);
        
        return rtnDto;
    }
}
