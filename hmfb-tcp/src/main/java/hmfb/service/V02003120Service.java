package hmfb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hmfb.core.dto.V02003120Dto;
import hmfb.core.dto.VReturnDto;
import hmfb.core.service.VBaseService;
import hmfb.db.TcpDao;

//출금이체
public class V02003120Service implements VBaseService{
	private static final Logger CLOG = LoggerFactory.getLogger("CLOGGER");
	
	@Override
	public void process(VReturnDto dto) {
        V02003120Dto receiveDto = (V02003120Dto)dto.getRtnObj();
        dto.getCommonDto().setSndRcvFlag("1");
        
        String acnutNo = receiveDto.getDEFRAY_ACNUT_NO(); //출금계좌번호
        
        int cnt = TcpDao.getDao().selectOne("T02001100.selectT02001100", acnutNo);
        
    	if(cnt < 1)
    		dto.getCommonDto().setRecvCd("9999");
    	
        CLOG.info("Service 호출됨..!"+receiveDto.getMessage());
		
	}

}
