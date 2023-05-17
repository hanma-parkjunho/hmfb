package hmfb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hmfb.core.dto.V02003110Dto;
import hmfb.core.dto.VReturnDto;
import hmfb.core.service.VBaseService;
import hmfb.db.TcpDao;

//입금이체
public class V02003110Service implements VBaseService{
	private static final Logger CLOG = LoggerFactory.getLogger("CLOGGER");
	
	@Override
	public void process(VReturnDto dto) {
        V02003110Dto receiveDto = (V02003110Dto)dto.getRtnObj();
        dto.getCommonDto().setSndRcvFlag("1");
        
        String acnutNo = receiveDto.getRCPMNY_ACNUT_NO(); //입금계좌번호
        
        int cnt = TcpDao.getDao().selectOne("T02001100.selectT02001100", acnutNo);
        
    	if(cnt < 1)
    		dto.getCommonDto().setRecvCd("9999");
    	
        CLOG.info("Service 호출됨..!"+receiveDto.getMessage());
		
	}

}
