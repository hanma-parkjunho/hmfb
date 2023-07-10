package hmfb.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F4000201Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.framework.batch.biz.AbstractBatchService;

@Service
public class F4000201Service extends AbstractBatchService {
	@Autowired
	IntfService intfService;

    /** 예금거래(교환어음)명세 결번요구
     * @param dto
     * @return
     * @throws Exception
     */
    public FirmReturnDto f4000201Service (F4000201Dto dto, String telemsgNo) {
    	// 송신 호출
    	FirmReturnDto rtnDto = intfService.sendTcp(InterfaceMapping.INF_FIRM_20, dto, telemsgNo);
        
        return rtnDto;
    }
}
