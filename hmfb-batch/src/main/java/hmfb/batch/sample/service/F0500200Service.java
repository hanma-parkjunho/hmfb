package hmfb.batch.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F0500200Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.service.IntfService;
import hmfb.framework.batch.biz.AbstractBatchService;

@Service(value = "batch.f0500200Service")
public class F0500200Service extends AbstractBatchService {
	@Autowired
	IntfService intfService;

    /** 수취인조회
     * @param dto
     * @return
     * @throws Exception
     */
    public F0500200Dto f0500200Service (F0500200Dto dto) {
    	// 송신 호출
    	FirmReturnDto rtnDto = intfService.sendTcp(InterfaceMapping.INF_FIRM_01, dto);
        
        return (F0500200Dto)rtnDto.getRtnObj();
    }
}
