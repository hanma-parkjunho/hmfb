package hmfb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hmfb.core.dto.V04001100Dto;
import hmfb.core.dto.VReturnDto;
import hmfb.core.service.VBaseService;
import hmfb.db.TcpDao;

//입금취소
public class V04001100Service implements VBaseService{
	private static final Logger CLOG = LoggerFactory.getLogger("CLOGGER");
	
	@Override
	public void process(VReturnDto dto) {
		V04001100Dto receiveDto = (V04001100Dto)dto.getRtnObj();
        dto.getCommonDto().setSndRcvFlag("1");
        
        String acnutNo = receiveDto.getRCPMNY_ACNUT_NO(); //입금계좌번호
        
        int cnt = TcpDao.getDao().selectOne("T02001100.selectT02001100", acnutNo);
        
    	if(cnt < 1)
    		dto.getCommonDto().setRecvCd("9999");
        
        CLOG.info("Service 호출됨..!"+receiveDto.getMessage());
		
	}

}
