package hmfb.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F1000100Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.core.utils.SequenceUtil;
import hmfb.framework.batch.biz.AbstractBatchService;

@Service
public class F1000100Service extends AbstractBatchService {
	@Autowired
	IntfService intfService;

    /** 업무개시
     * @param dto
     * @return
     * @throws Exception
     */
    public FirmReturnDto f1000100Service (F1000100Dto dto) {
    	// 송신 호출
    	FirmReturnDto rtnDto = intfService.sendTcp(InterfaceMapping.INF_FIRM_18, dto, SequenceUtil.getNextValue());
        
        return rtnDto;
    }
}
