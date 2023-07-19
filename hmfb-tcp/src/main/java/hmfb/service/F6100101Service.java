package hmfb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hmfb.core.dto.F6100101Dto;
import hmfb.core.dto.StdFirmCommonDto;
import hmfb.core.dto.StdFirmReturnDto;
import hmfb.core.dto.T6100101Dto;
import hmfb.core.service.StdBaseService;
import hmfb.db.TcpDao;
/**
 *	당,타행예금주성명조회 수신
 */
public class F6100101Service implements StdBaseService {
	
    private static final Logger CLOG = LoggerFactory.getLogger("CLOGGER");
    
    @Override
    public void process(StdFirmReturnDto dto) {
    	
    	StdFirmCommonDto commonDto = (StdFirmCommonDto) dto.getCommonDto();
    	F6100101Dto receiveDto = (F6100101Dto) dto.getRtnObj();
    	T6100101Dto input = new T6100101Dto();
    	
    	input.setDpstrNm(receiveDto.getDpstrNm());
    	
    	input.setRspnsCode(commonDto.getRecvCode());
    	input.setRecvDt(commonDto.getTranDt());
    	input.setRecvTm(commonDto.getTranTm());
    	
    	input.setTelemsgNo(commonDto.getTlgmSeqNo());
    	
        TcpDao.getDao().update("T6100101.updateT6100101", input);
    	
    	CLOG.info("6100101Service >>>> 당타행예금주성명조회 [[ 수신 ]] "+ receiveDto.getMessage());
    	
    	/*
    	F6000101Dto receiveDto = (F6000101Dto)dto.getRtnObj();
    	
    	// 배열을 만들어서 넣는다. dto에 
    	T6100101Dto input = new T6100101Dto();
    	
    	input.setOrgCode(receiveDto.getOrgCode()); 				// 식별코드
    	input.setCompanyCode(receiveDto.getCompanyCode()); 		// 업체코드
    	input.setBankCode(receiveDto.getBankCode()); 			// 은행코드
    	input.setProfessCode(receiveDto.getProfessCode()); 		// 전문코드
    	input.setBusinessSort(receiveDto.getBusinessSort()); 	// 업무구분
    	input.setResponseCnt(receiveDto.getResponseCnt()); 		// 송신횟수
    	input.setProfessNum(receiveDto.getProfessNum()); 		// 전문번호
    	input.setRequestDate(receiveDto.getRequestDate()); 		// 전송일자
    	input.setRequestTime(receiveDto.getRequestTime()); 		// 전송시간
    	input.setReplyCode(receiveDto.getReplyCode()); 			// 응답코드
    	input.setDiscernCode(receiveDto.getDiscernCode()); 		// 식별코드2
    	input.setSdsArea(receiveDto.getSdsArea()); 				// SDS영역
    	input.setCustomerArea(receiveDto.getCustomerArea()); 	// 고객영역
    	input.setY2KSort(receiveDto.getY2KSort()); 				// Y2K구분
    	input.setBankArea(receiveDto.getBankArea()); 			// 은행영역
    	
		TcpDao.getDao().insert("T6100101.insertT6100101", input);
		
    	dto.getCommonDto().setSndRcvDvcd("S");					// setSndRcvDvcd()
        dto.getCommonDto().setRecvCode("0000");
        
        CLOG.info("Service F6000101 호출됨..!"+receiveDto.getMessage());
        */
        
    }
}
