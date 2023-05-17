package hmfb.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F0300300Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.framework.batch.biz.AbstractBatchService;

@Service
public class F0300300Service extends AbstractBatchService {
	@Autowired
	IntfService intfService;

    /** 가상계좌과제정보등록
     * @param dto
     * @return
     * @throws Exception
     */
    public FirmReturnDto f0300300Service (F0300300Dto dto, String telemsgNo) {
    	// 송신 호출
    	FirmReturnDto rtnDto = intfService.sendTcp(InterfaceMapping.INF_FIRM_09, dto, telemsgNo);
        
        return rtnDto;
    }
}
