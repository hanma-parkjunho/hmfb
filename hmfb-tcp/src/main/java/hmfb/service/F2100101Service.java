package hmfb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hmfb.core.dto.F0500200Dto;
import hmfb.core.dto.F2000101Dto;
import hmfb.core.dto.F2100101Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.dto.StdFirmCommonDto;
import hmfb.core.dto.StdFirmReturnDto;
import hmfb.core.dto.T2000101Dto;
import hmfb.core.dto.T2100101Dto;
import hmfb.core.service.BaseService;
import hmfb.core.service.StdBaseService;
import hmfb.db.TcpDao;
/**
 *	당타행지급이체 수신
 */
public class F2100101Service implements StdBaseService {
	
    private static final Logger CLOG = LoggerFactory.getLogger("CLOGGER");
    
    @Override
    public void process(StdFirmReturnDto dto) {
    	
    	StdFirmCommonDto commonDto = (StdFirmCommonDto) dto.getCommonDto();
    	F2100101Dto receiveDto = (F2100101Dto) dto.getRtnObj();
    	T2100101Dto input = new T2100101Dto();
    	
    	input.setSmbol(receiveDto.getSmbol());
    	input.setBlce(receiveDto.getBlce());
    	input.setFee(receiveDto.getFee());
    	input.setRlnmNo(receiveDto.getRlnmNo());
    	
    	input.setRspnsCode(commonDto.getRecvCode());
    	input.setRecvDt(commonDto.getTranDt());
    	input.setRecvTm(commonDto.getTranTm());
    	
    	input.setTelemsgNo(commonDto.getTlgmSeqNo());
    	
    	TcpDao.getDao().update("T2100101.updateT2100101", input);
    	
    	CLOG.info("2100101Service >>>> 당타행지급이체 [[ 수신 ]] "+ receiveDto.getMessage());
    	
    	/*
    	F2000101Dto receiveDto = (F2000101Dto)dto.getRtnObj();
    	
    	// 배열을 만들어서 넣는다. dto에 
    	T2100101Dto input = new T2100101Dto();
    	
    	input.setPymentAcunt(receiveDto.getPymentAcunt()); 
    	input.setPassword(receiveDto.getPassword()); 
    	input.setBankCode(receiveDto.getBankCode()); 
    	input.setRevwSmbol(receiveDto.getRevwSmbol()); 		 
    	input.setPymntMount(receiveDto.getPymntMount()); 	
    	input.setSmbol(receiveDto.getSmbol()); 		
    	input.setBlce(receiveDto.getBlce()); 		
    	input.setBankCode(receiveDto.getBankCode()); 		
    	input.setRcpmnyAcnut(receiveDto.getRcpmnyAcnut()); 		
    	input.setFee(receiveDto.getFee()); 			
    	input.setCmsCode(receiveDto.getCmsCode()); 		
    	input.setRcpmnyAcnutPrntxt(receiveDto.getRcpmnyAcnutPrntxt()); 
    	input.setRlnmNo(receiveDto.getRlnmNo()); 	
    	input.setDpstrNm(receiveDto.getDpstrNm()); 				
    	input.setCptalCharct(receiveDto.getCptalCharct());
    	input.setFiller1(receiveDto.getFiller1()); 			
    	input.setFiller2(receiveDto.getFiller2()); 			
    	input.setFiller3(receiveDto.getFiller3()); 			
    	input.setFiller4(receiveDto.getFiller4()); 			
    	input.setFiller5(receiveDto.getFiller5()); 			
    	
    	input.setRspnsCode(receiveDto.getRspnsCode());
    	input.setRspnsMssage(receiveDto.getRspnsMssage());
    	input.setSendCode(receiveDto.getSendCode());
    	
		TcpDao.getDao().insert("T2100101.insertT2100101", input);
		
    	dto.getCommonDto().setSndRcvDvcd("B");
        dto.getCommonDto().setRecvCode("2000");
        
        CLOG.info("Service 호출됨..!"+receiveDto.getMessage());
        */
        
    }
}