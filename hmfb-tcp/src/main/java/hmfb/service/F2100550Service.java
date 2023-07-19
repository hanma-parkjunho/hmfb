package hmfb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hmfb.core.dto.F2000550Dto;
import hmfb.core.dto.F2100101Dto;
import hmfb.core.dto.F2100550Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.dto.StdFirmCommonDto;
import hmfb.core.dto.StdFirmReturnDto;
import hmfb.core.dto.T2100101Dto;
import hmfb.core.dto.T2100550Dto;
import hmfb.core.service.BaseService;
import hmfb.core.service.StdBaseService;
import hmfb.db.TcpDao;
/**
 *	자동이체신청 수신
 */
public class F2100550Service implements StdBaseService {
	
    private static final Logger CLOG = LoggerFactory.getLogger("CLOGGER");
    
    @Override
    public void process(StdFirmReturnDto dto) {
    	
    	StdFirmCommonDto commonDto = (StdFirmCommonDto) dto.getCommonDto();
    	F2100550Dto receiveDto = (F2100550Dto) dto.getRtnObj();
    	T2100550Dto input = new T2100550Dto();
    	
    	input.setDsrbtr(receiveDto.getDsrbtr());
    	input.setProcessAt(receiveDto.getProcessAt());
    	input.setIncpctyCode(receiveDto.getIncpctyCode());
    	
    	input.setRspnsCode(commonDto.getRecvCode());
    	input.setRecvDt(commonDto.getTranDt());
    	input.setRecvTm(commonDto.getTranTm());
    	
    	input.setTelemsgNo(commonDto.getTlgmSeqNo());
    	
        TcpDao.getDao().update("T2100550.updateT2100550", input);
    	
    	CLOG.info("2100550Service >>>> 자동이체신청 [[ 수신 ]] "+ receiveDto.getMessage());
    	
    	/*
    	F2100550Dto receiveDto = (F2100550Dto) dto.getRtnObj();
    	
    	// 배열을 만들어서 넣는다. dto에 
    	T2100550Dto input = new T2100550Dto();
    	
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
    	
		TcpDao.getDao().insert("T2000550.insertT2000550", input);
		
    	dto.getCommonDto().setSndRcvDvcd("S");					// setSndRcvDvcd()
        dto.getCommonDto().setRecvCode("0000");
        
        CLOG.info("Service F2000550 호출됨..!"+receiveDto.getMessage());
        */
        
    }
}
