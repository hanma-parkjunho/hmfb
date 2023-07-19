package hmfb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hmfb.core.dto.F4000101Dto;
import hmfb.core.dto.F4000201Dto;
import hmfb.core.dto.F4100201Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.dto.StdFirmCommonDto;
import hmfb.core.dto.StdFirmReturnDto;
import hmfb.core.dto.T4000101Dto;
import hmfb.core.dto.T4000201Dto;
import hmfb.core.dto.T4100201Dto;
import hmfb.core.service.BaseService;
import hmfb.core.service.StdBaseService;
import hmfb.db.TcpDao;
/**
 *	당,타행예금주성명조회 수신
 */
public class F4100201Service implements StdBaseService {
	
    private static final Logger CLOG = LoggerFactory.getLogger("CLOGGER");
    
    @Override
    public void process(StdFirmReturnDto dto) {
    	
    	StdFirmCommonDto commonDto = (StdFirmCommonDto) dto.getCommonDto();
    	F4100201Dto receiveDto = (F4100201Dto) dto.getRtnObj();
    	T4100201Dto input = new T4100201Dto();
    	
    	input.setTelemsgNo(commonDto.getTlgmSeqNo());
    	input.setAcnutNo(receiveDto.getAcnutNo());
    	input.setRcpmnyBhf(receiveDto.getRcpmnyBhf());
    	input.setRcpmnyPymntSe(receiveDto.getRcpmnyPymntSe());
    	input.setDelngSe(receiveDto.getDelngSe());
    	input.setAmount(receiveDto.getAmount());
    	input.setAltrtvAmount(receiveDto.getAltrtvAmount());
    	input.setEtc(receiveDto.getEtc());
    	input.setSmbol(receiveDto.getSmbol());
    	input.setBlce(receiveDto.getBlce());
    	input.setNm(receiveDto.getNm());
    	input.setCheckBilNo(receiveDto.getCheckBilNo());
    	input.setDelngDe(receiveDto.getDelngDe());
    	input.setDelngTime(receiveDto.getDelngTime());
    	input.setSn(receiveDto.getSn());
    	input.setOridelngNo(receiveDto.getOridelngNo());
    	input.setOridelngDe(receiveDto.getOridelngDe());
    	input.setRcpmnyerCode(receiveDto.getRcpmnyerCode());
    	input.setCsrcc(receiveDto.getCsrcc());
    	input.setPrsnlchk(receiveDto.getPrsnlchk());
    	input.setSendCode("");
    	input.setSendDt("");
    	input.setSendTm("");
    	input.setRspnsCode(commonDto.getRecvCode());
    	input.setRspnsMssage("");
    	input.setRecvDt(commonDto.getTranDt());
    	input.setRecvTm(commonDto.getTranTm());
    	
        TcpDao.getDao().insert("T4000101.insertT4000101", input);
    	
    	CLOG.info("4100201Service >>>> 예금거래명세 결번요구 [[ 수신 ]] "+ receiveDto.getMessage());
    	
    	/*
    	F4000201Dto receiveDto = (F4000201Dto)dto.getRtnObj();
    	
    	// 배열을 만들어서 넣는다. dto에 
    	T4000201Dto input = new T4000201Dto();
    	
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
    	
		TcpDao.getDao().insert("T4000201.insertT4000201", input);
		
    	dto.getCommonDto().setSndRcvDvcd("S");					// setSndRcvDvcd()
        dto.getCommonDto().setRecvCode("0000");
        
        CLOG.info("Service F4000201 호출됨..!"+receiveDto.getMessage());
        */
        
    }
}
