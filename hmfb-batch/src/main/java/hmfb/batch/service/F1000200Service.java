package hmfb.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F1000200Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.core.utils.SequenceUtil;
import hmfb.framework.batch.biz.AbstractBatchService;

@Service
public class F1000200Service extends AbstractBatchService {
	@Autowired
	IntfService intfService;

    /** 업무종료
     * @param dto
     * @return
     * @throws Exception
     */
    public FirmReturnDto f1000200Service (F1000200Dto dto) {
    	// 송신 호출
    	FirmReturnDto rtnDto = intfService.sendTcp(InterfaceMapping.INF_FIRM_19, dto, SequenceUtil.getNextValue());
        
        return rtnDto;
    }
}
