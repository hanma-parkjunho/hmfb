package hmfb.batch;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import hmfb.batch.service.F0500200Service;
import hmfb.core.dto.BatchJobContext;
import hmfb.core.dto.F0500200Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.dto.T0500200Dto;
import hmfb.core.exception.HmfbException;
import hmfb.framework.batch.biz.IChunkBatchJob;
import hmfb.framework.batch.db.BatchDao;
import lombok.extern.log4j.Log4j2;

/**
 * INPUT 데이터  : 
 * OUTPUT     : 수취인 조회 인터페이스  
 *  
 * @author KDK
 *
 */
@Log4j2
public class BJF0500200 implements IChunkBatchJob {
	
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
	public T0500200Dto process(Object param, BatchJobContext ctx) throws HmfbException {
		
		T0500200Dto input = (T0500200Dto)param;
		T0500200Dto output = new T0500200Dto();
		
		F0500200Dto inFirmDto = new F0500200Dto();
//		inFirmDto.setTelemsgNo(input.getTelemsgNo());
		inFirmDto.setReceptInfo(input.getReceptInfo());
		inFirmDto.setBankCode(input.getBankCode());
		inFirmDto.setAcnutNo(input.getAcnutNo());
//		inFirmDto.setDelngAmount(input.getDelngAmount().toString());
//		inFirmDto.setDpstrNm(input.getDpstrNm());
		FirmReturnDto returnDto = F0500200Service.getService(F0500200Service.class).f0500200Service(inFirmDto, input.getTelemsgNo());
		F0500200Dto outFirmDto = (F0500200Dto) returnDto.getRtnObj();
		if("0000".equals(returnDto.getCommonDto().getRecvCode())) {
			output.setDpstrNm(outFirmDto.getDpstrNm());				//수취인
			output.setRspnsMssage("");
		} else {
			output.setRspnsMssage("ERROR");
		}
		output.setRegDate(returnDto.getCommonDto().getTranDt());
		output.setTelemsgNo(returnDto.getCommonDto().getTlgmSeqNo());
		output.setRspnsCode(returnDto.getCommonDto().getRecvCode());
		output.setSendYn("Y");
		BatchDao.getDao().update("T0500200.updateT0500200", output);
		if (log.isDebugEnabled()) {
			log.debug("F0500200 전문 응답 처리 완료");
			log.debug("전문 응답 내용:"+returnDto);
		}
//		D2D 일 경우 dummy 를 리턴. 
		return output;		
	}

}
