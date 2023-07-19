package hmfb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hmfb.core.dto.F3000101Dto;
import hmfb.core.dto.StdFirmCommonDto;
import hmfb.core.dto.StdFirmReturnDto;
import hmfb.core.dto.T3000101Dto;
import hmfb.core.service.StdBaseService;
import hmfb.db.TcpDao;
/**
 *	타행이체불능분 통지 수신
 *  타행이체불능분 통지는 은행 -> 기관 순으로 인터페이스가 진행하게 되어 기관에서 응답한다
 */
public class F3000101Service implements StdBaseService {
	
    private static final Logger CLOG = LoggerFactory.getLogger("CLOGGER");
    
    @Override
    public void process(StdFirmReturnDto dto) {
    	
    	StdFirmCommonDto commonDto = (StdFirmCommonDto) dto.getCommonDto();
    	F3000101Dto receiveDto = (F3000101Dto) dto.getRtnObj();
    	T3000101Dto input = new T3000101Dto();
    	
    	input.setTelemsgNo(commonDto.getTlgmSeqNo());
    	input.setOritelemsgNo(receiveDto.getOritelemsgNo());
    	input.setPymntAcnut(receiveDto.getPymntAcnut());
    	input.setBankCode(receiveDto.getBankCode());
    	input.setRcpmnyAcnut(receiveDto.getRcpmnyAcnut());
    	input.setAmount(receiveDto.getAmount());
    	input.setNrmltAmount(receiveDto.getNrmltAmount());
    	input.setIncpctyAmount(receiveDto.getIncpctyAmount());
    	input.setSubcnt(receiveDto.getSubcnt());
    	input.setSubseq(receiveDto.getSubseq());
    	input.setDiffbankProcessNo(receiveDto.getDiffbankProcessNo());
    	input.setRcpmnyIncpctyAm(receiveDto.getRcpmnyIncpctyAm());
    	input.setDiffbankProcessCode(receiveDto.getDiffbankProcessCode());
    	input.setSendCode("");
    	input.setSendDt("");
    	input.setSendTm("");
    	input.setRspnsCode(commonDto.getRecvCode());
    	input.setRspnsMssage("");
    	input.setRecvDt(commonDto.getTranDt());
    	input.setRecvTm(commonDto.getTranTm());
    	
        TcpDao.getDao().insert("T3000101.insertT3000101", input);
    	
    	CLOG.info("3000101Service >>>> 타행이제결과 불능분 통지 [[ 수신 ]] "+ receiveDto.getMessage());
    	
    	
    	/*
    	F3000101Dto receiveDto = (F3000101Dto)dto.getRtnObj();
    	
    	// 배열을 만들어서 넣는다. dto에 
    	T3100101Dto input = new T3100101Dto();
    	
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
    	
		TcpDao.getDao().insert("T3000101.insertT3000101", input);
		
    	dto.getCommonDto().setSndRcvDvcd("S");					// setSndRcvDvcd()
        dto.getCommonDto().setRecvCode("0000");
        
        CLOG.info("Service F3000101 호출됨..!"+receiveDto.getMessage());
        */
        
    }
}
