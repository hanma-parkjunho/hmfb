package hmfb.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F3100101Dto;
import hmfb.core.dto.StdFirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.framework.batch.biz.AbstractBatchService;

@Service
public class F3100101Service extends AbstractBatchService {
	@Autowired
	IntfService intfService;

    /** 타행이체결과 불능분 통지
     * @param dto
     * @return
     * @throws Exception
     */
    public StdFirmReturnDto f3100101Service (F3100101Dto dto, String telemsgNo) {
    	// 송신 호출
    	StdFirmReturnDto rtnDto = intfService.StdsendTcp(InterfaceMapping.INF_FIRM_25, dto, telemsgNo);
        
        return rtnDto;
    }
}
