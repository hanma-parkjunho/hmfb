package hmfb.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F0100300Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.core.utils.SequenceUtil;
import hmfb.framework.batch.biz.AbstractBatchService;

@Service
public class F0100300Service extends AbstractBatchService {
	@Autowired
	IntfService intfService;

    /** 시스템상황체크
     * @param dto
     * @return
     * @throws Exception
     */
    public FirmReturnDto f0100300Service (F0100300Dto dto) {
    	// 송신 호출
    	FirmReturnDto rtnDto = intfService.sendTcp(InterfaceMapping.INF_FIRM_03, dto, SequenceUtil.getNextValue());
        
        return rtnDto;
    }
}
