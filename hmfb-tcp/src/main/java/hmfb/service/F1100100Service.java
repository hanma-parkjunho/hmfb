package hmfb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hmfb.core.dto.StdFirmCommonDto;
import hmfb.core.dto.StdFirmReturnDto;
import hmfb.core.dto.T1100100Dto;
import hmfb.core.service.StdBaseService;
import hmfb.db.TcpDao;

/**
 *	업무개시 [[ 수신 ]]
 */
public class F1100100Service implements StdBaseService {
	
    private static final Logger CLOG = LoggerFactory.getLogger("CLOGGER");
    
    @Override
    public void process(StdFirmReturnDto dto) {
    	
    	//F1100100Dto receiveDto = (F1100100Dto) dto.getRtnObj();
    	StdFirmCommonDto commonDto = (StdFirmCommonDto) dto.getCommonDto();
    	T1100100Dto input = new T1100100Dto();

    	input.setRspnsCode(commonDto.getRecvCode());
    	input.setJobDe(commonDto.getTranDt());
    	input.setJobSe("01");
    	input.setTelemsgNo(commonDto.getTlgmSeqNo());
    	
    	TcpDao.getDao().update("T1100100.updateT1100100", input);

    	/*
    	// 배열을 만들어서 넣는다. dto에 
    	//T1100100Dto input = new T1100100Dto();
    	receiveDto.getRecvCode();
    	 			
    	
		TcpDao.getDao().insert("T1100100.insertT1100100", input);
		
    	dto.getCommonDto().setSndRcvDvcd("S");		
        dto.getCommonDto().setRecvCode("0000");
        */
        
        CLOG.info("1100100Service >>>> 업무개시 [[ 수신 ]] "+ commonDto.getMessage());
        
    }
}
