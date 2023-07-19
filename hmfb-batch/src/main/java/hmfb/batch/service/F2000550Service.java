package hmfb.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F2000550Dto;
import hmfb.core.dto.StdFirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.framework.batch.biz.AbstractBatchService;

@Service
public class F2000550Service extends AbstractBatchService {
	@Autowired
	IntfService intfService;

    /** 자동이체 신청
     * @param dto
     * @return
     * @throws Exception
     */
    public StdFirmReturnDto f2000550Service (F2000550Dto dto, String telemsgNo) {
    	// 송신 호출
    	StdFirmReturnDto rtnDto = intfService.StdsendTcp(InterfaceMapping.INF_FIRM_21, dto, telemsgNo);
        
        return rtnDto;
    }
}
