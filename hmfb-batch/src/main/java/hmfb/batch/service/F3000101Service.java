package hmfb.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F3000101Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.framework.batch.biz.AbstractBatchService;

@Service
public class F3000101Service extends AbstractBatchService {
	@Autowired
	IntfService intfService;

    /** 타행이체결과 불능분 통지
     * @param dto
     * @return
     * @throws Exception
     */
    public FirmReturnDto f3000101Service (F3000101Dto dto, String telemsgNo) {
    	// 송신 호출
    	FirmReturnDto rtnDto = intfService.sendTcp(InterfaceMapping.INF_FIRM_25, dto, telemsgNo);
        
        return rtnDto;
    }
}
