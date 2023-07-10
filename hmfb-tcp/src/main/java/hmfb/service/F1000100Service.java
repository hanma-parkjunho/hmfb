package hmfb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hmfb.core.dto.F1000100Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.dto.T1100100Dto;
import hmfb.core.service.BaseService;
import hmfb.db.TcpDao;
/**
 *	업무개시 [[ 수신 ]]
 */
public class F1000100Service implements BaseService {
	
    private static final Logger CLOG = LoggerFactory.getLogger("CLOGGER");
    
    @Override
    public void process(FirmReturnDto dto) {
    	
    	F1000100Dto receiveDto = (F1000100Dto)dto.getRtnObj();
    	
    	// 배열을 만들어서 넣는다. dto에 
    	T1100100Dto input = new T1100100Dto();
    	 			
    	
		TcpDao.getDao().insert("T1100100.insertT1100100", input);
		
    	dto.getCommonDto().setSndRcvDvcd("S");		
        dto.getCommonDto().setRecvCode("0000");
        
        CLOG.info("Service 호출됨.. T1100100! >>>> 수신쪽!! 업무개시 [[ 수신 ]] "+receiveDto.getMessage());
        
    }
}
