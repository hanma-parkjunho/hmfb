package hmfb.batch;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;

import hmfb.batch.service.F0300400Service;
import hmfb.core.dto.BatchJobContext;
import hmfb.core.dto.F0300400Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.dto.T0300400Dto;
import hmfb.core.exception.HmfbException;
import hmfb.framework.batch.biz.IChunkBatchJob;
import hmfb.framework.batch.db.BatchDao;
import lombok.extern.log4j.Log4j2;

/**
 * INPUT 데이터  : 
 * OUTPUT     : 은행 대리점 등록 인터페이스
 *  
 * @author KDK
 *
 */
@Log4j2
public class BJF0300400 implements IChunkBatchJob {
	
	@Value("${hmfb.firm.id}")
    private String firmId;
	
	public boolean isExecutable(BatchJobContext ctx) throws HmfbException {
		
		if(log.isDebugEnabled()) {
			log.debug("[업무로그]"+getClass().getSimpleName()+".isExecutable 실행");
		}
		
		DayOfWeek day = LocalDate.now().getDayOfWeek();
//		일요일엔 정상 종료 리턴. (배치를 수행시키지 않는다. 상태는 FAILED 가 아닌 COMPLETED)
		return (day.equals(DayOfWeek.SUNDAY)) ? false : true;
	}
	
	public void preProcess(BatchJobContext ctx) throws HmfbException {
		if(log.isDebugEnabled()) {
			log.debug("[업무로그]"+getClass().getSimpleName()+".preProcess 실행");
			log.debug("[업무로그]"+ctx.getInputDataSelector());
		}
//		DB 조회 조건을 입력
		String ndt = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		ctx.putCondition("regDate", ndt);
		ctx.putCondition("sendYn", "N");		
	}
	/**
	 * 생략 가능 
	 */
	public void postProcess(BatchJobContext ctx) throws HmfbException {
		if(log.isDebugEnabled()) {
			log.debug("[업무로그]"+getClass().getSimpleName()+".postProcess 실행");
		}
	}
	/**
	 *  생략 가능 : 생략 시 itemReader 에서 읽은 객체를 itemWriter 로 bypass.
	 */
	@Override
	public T0300400Dto process(Object param, BatchJobContext ctx) throws HmfbException {
		
		T0300400Dto input = (T0300400Dto)param;
		T0300400Dto output = new T0300400Dto();
		
		F0300400Dto inFirmDto = new F0300400Dto();
		
		inFirmDto.setOrgCode(firmId);								  //기관코드
		inFirmDto.setRcpmnyPrtsacnut(input.getRcpmnyPrtsacnut());     //입금모계좌번호
		inFirmDto.setDefrayPrtsacnut(input.getDefrayPrtsacnut());     //출금모계좌번호
		inFirmDto.setFeePrtsacnut(input.getFeePrtsacnut());           //수수료모계좌번호
//		inFirmDto.setAgencyCode(input.getAgencyCode());               //대리점코드
		inFirmDto.setAgencyNm(input.getAgencyNm());                   //대리점명
		FirmReturnDto returnDto = F0300400Service.getService(F0300400Service.class).f0300400Service(inFirmDto, input.getTelemsgNo());
		F0300400Dto outFirmDto = (F0300400Dto) returnDto.getRtnObj();
		if("0000".equals(returnDto.getCommonDto().getRecvCode())) {
			output.setAgencyCode(outFirmDto.getAgencyCode());               //대리점코드
			output.setRspnsMssage("");
		} else {
			output.setRspnsMssage("ERROR");
		}
		output.setRegDate(returnDto.getCommonDto().getTranDt());
		output.setTelemsgNo(returnDto.getCommonDto().getTlgmSeqNo());
		output.setRspnsCode(returnDto.getCommonDto().getRecvCode());
		output.setSendYn("Y");
		BatchDao.getDao().update("T0300400.updateT0300400", output);
		if (log.isDebugEnabled()) {
			log.debug("F0300400 전문 응답 처리 완료");
			log.debug("전문 응답 내용:"+returnDto);
		}
//		D2D 일 경우 dummy 를 리턴. 
		return output;		
	}

}
