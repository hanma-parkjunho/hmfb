package hmfb.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F2000101Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.dto.StdFirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.framework.batch.biz.AbstractBatchService;

@Service
public class F2000101Service extends AbstractBatchService {
	@Autowired
	IntfService intfService;

    /** 당, 타행 지급이체(자금이체)
     * @param dto
     * @return
     * @throws Exception
     */
    public StdFirmReturnDto f2000101Service (F2000101Dto dto, String telemsgNo) {
    	// 송신 호출
    	StdFirmReturnDto rtnDto = intfService.StdsendTcp(InterfaceMapping.INF_FIRM_20, dto, telemsgNo);
        
        return rtnDto;
    }
}
