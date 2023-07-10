package hmfb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hmfb.core.dto.F0500200Dto;
import hmfb.core.dto.F2000101Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.dto.T2100101Dto;
import hmfb.core.service.BaseService;
import hmfb.db.TcpDao;
/**
 *	업무개시 수신
 */
//public class F2000101Service implements BaseService {
//	
//    private static final Logger CLOG = LoggerFactory.getLogger("CLOGGER");
//    
//    @Override
//    public void process(FirmReturnDto dto) {
//    	
//    	F2000101Dto receiveDto = (F2000101Dto)dto.getRtnObj();
//    	
//    	// 배열을 만들어서 넣는다. dto에 
//    	T2100101Dto input = new T2100101Dto();
//    	
//    	input.setPymentAcunt(receiveDto.getPymentAcunt()); 
//    	input.setPassword(receiveDto.getPassword()); 
//    	input.setBankCode(receiveDto.getBankCode()); 
//    	input.setRevwSmbol(receiveDto.getRevwSmbol()); 		 
//    	input.setPymntMount(receiveDto.getPymntMount()); 	
//    	input.setSmbol(receiveDto.getSmbol()); 		
//    	input.setBlce(receiveDto.getBlce()); 		
//    	input.setBankCode(receiveDto.getBankCode()); 		
//    	input.setRcpmnyAcnut(receiveDto.getRcpmnyAcnut()); 		
//    	input.setFee(receiveDto.getFee()); 			
//    	input.setCmsCode(receiveDto.getCmsCode()); 		
//    	input.setRcpmnyAcnutPrntxt(receiveDto.getRcpmnyAcnutPrntxt()); 
//    	input.setRlnmNo(receiveDto.getRlnmNo()); 	
//    	input.setDpstrNm(receiveDto.getDpstrNm()); 				
//    	input.setCptalCharct(receiveDto.getCptalCharct());
//    	input.setFiller1(receiveDto.getFiller1()); 			
//    	input.setFiller2(receiveDto.getFiller2()); 			
//    	input.setFiller3(receiveDto.getFiller3()); 			
//    	input.setFiller4(receiveDto.getFiller4()); 			
//    	input.setFiller5(receiveDto.getFiller5()); 			
//    	
//    	input.setRspnsCode(receiveDto.getRspnsCode());
//    	input.setRspnsMssage(receiveDto.getRspnsMssage());
//    	input.setSendCode(receiveDto.getSendCode());
//    	
//		TcpDao.getDao().insert("T2100101.insertT2100101", input);
//		
//    	dto.getCommonDto().setSndRcvDvcd("B");
//        dto.getCommonDto().setRecvCode("2000");
//        
//        CLOG.info("Service 호출됨..!"+receiveDto.getMessage());
//        
//    }
//}
	
public class F2000101Service implements BaseService {
    private static final Logger CLOG = LoggerFactory.getLogger("CLOGGER");
    @Override
    public void process(FirmReturnDto dto) {
    	F2000101Dto receiveDto = (F2000101Dto)dto.getRtnObj();
        dto.getCommonDto().setSndRcvDvcd("S");
        dto.getCommonDto().setRecvCode("0000");
        receiveDto.setDpstrNm("김준영");
        CLOG.info("Service F2000101dto 호출됨..!" + receiveDto.getMessage());
    }
}

