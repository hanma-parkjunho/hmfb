package hmfb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hmfb.core.dto.F0500200Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.service.BaseService;
/**
 *	수취인 조회(로컬 테스트 용)
 */
public class F0500200Service implements BaseService {
    private static final Logger CLOG = LoggerFactory.getLogger("CLOGGER");
    @Override
    public void process(FirmReturnDto dto) {
    	F0500200Dto receiveDto = (F0500200Dto)dto.getRtnObj();
        dto.getCommonDto().setSndRcvDvcd("S");
        dto.getCommonDto().setRecvCode("0000");
        receiveDto.setDpstrNm("홍길동");
        CLOG.info("Service 호출됨..!"+receiveDto.getMessage());
    }
}
