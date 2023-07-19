package hmfb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hmfb.core.dto.F7100200Dto;
import hmfb.core.dto.StdFirmCommonDto;
import hmfb.core.dto.StdFirmReturnDto;
import hmfb.core.dto.T7100200Dto;
import hmfb.core.service.StdBaseService;
import hmfb.db.TcpDao;
/**
 *	당,타행예금주성명조회 수신
 */
public class F7100200Service implements StdBaseService {
	
    private static final Logger CLOG = LoggerFactory.getLogger("CLOGGER");
    
    @Override
    public void process(StdFirmReturnDto dto) {
    	
    	StdFirmCommonDto commonDto = (StdFirmCommonDto) dto.getCommonDto();
    	F7100200Dto receiveDto = (F7100200Dto) dto.getRtnObj();
    	T7100200Dto input = new T7100200Dto();
    	
    	input.setOvrdftLmt(receiveDto.getOvrdftLmt());
    	input.setBlceSmbol(receiveDto.getBlceSmbol());
    	input.setNowBlce(receiveDto.getNowBlce());
    	input.setBlcePrsnlchk(receiveDto.getBlcePrsnlchk());
    	input.setEtcBhfAm(receiveDto.getEtcBhfAm());
    	input.setDrtPosblSmbol(receiveDto.getDrtPosblSmbol());
    	input.setDrtPosblAmount(receiveDto.getDrtPosblAmount());
    	
    	input.setRspnsCode(commonDto.getRecvCode());
    	input.setRecvDt(commonDto.getTranDt());
    	input.setRecvTm(commonDto.getTranTm());
    	
    	input.setTelemsgNo(commonDto.getTlgmSeqNo());
    	input.setInqireAcnutNo(receiveDto.getInqireAcnutNo());
    	
        TcpDao.getDao().update("T7100200.updateT7100200", input);
    	
    	CLOG.info("7100200Service >>>> 모계좌잔액조회 [[ 수신 ]] "+ receiveDto.getMessage());
    	
    	/*
    	F7000200Dto receiveDto = (F7000200Dto)dto.getRtnObj();
    	
    	// 배열을 만들어서 넣는다. dto에 
    	T7000200Dto input = new T7000200Dto();
    	
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
    	
		TcpDao.getDao().insert("T7000200.insertT7000200", input);
		
    	dto.getCommonDto().setSndRcvDvcd("S");					// setSndRcvDvcd()
        dto.getCommonDto().setRecvCode("0000");
        
        CLOG.info("Service F7000200 호출됨..!"+receiveDto.getMessage());
        */
        
    }
}
