package hmfb.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F3000201Dto;
import hmfb.core.dto.StdFirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.framework.batch.biz.AbstractBatchService;

@Service
public class F3000201Service extends AbstractBatchService {
	@Autowired
	IntfService intfService;

    /** 모계좌 잔액조회
     * @param dto
     * @return
     * @throws Exception
     */
    public StdFirmReturnDto f3000201Service (F3000201Dto dto, String telemsgNo) {
    	// 송신 호출
    	StdFirmReturnDto rtnDto = intfService.StdsendTcp(InterfaceMapping.INF_FIRM_26, dto, telemsgNo);
        
        return rtnDto;
    }
}
