package hmfb.batch;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import hmfb.batch.service.F2000850Service;
import hmfb.core.dto.BatchJobContext;
import hmfb.core.dto.F2000101Dto;
import hmfb.core.dto.F2000850Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.dto.T2000850Dto;
import hmfb.core.dto.T2100850Dto;
import hmfb.core.exception.HmfbException;
import hmfb.framework.batch.biz.IChunkBatchJob;
import hmfb.framework.batch.db.BatchDao;
import lombok.extern.log4j.Log4j2;

/**
 * 샘플코드에서 사용한 데이터 예시
 * ------------------------------------
 * 배치 유형      : D2D
 * INPUT 데이터  : TDEP10001.selectAccountListByCondition
 * OUTPUT     : 수취인 조회 인터페이스  
 *  
 * @author KDK
 *
 */
@Log4j2
public class BJF2000850 implements IChunkBatchJob {
	
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
	public T2000850Dto process(Object param, BatchJobContext ctx) throws HmfbException {
		
		T2000850Dto input = (T2000850Dto)param;
		T2000850Dto output = new T2000850Dto();
		
		F2000850Dto inFirmDto = new F2000850Dto();

		F2000850Service.getService(F2000850Service.class).f2000850Service(inFirmDto, input.getTelemsgNo());
		
		output.setSendCode("02");
		output.setTelemsgNo(input.getTelemsgNo());
		
		BatchDao.getDao().update("T2000850.updateT2000850", output);

		// D2D 일 경우 dummy 를 리턴. 
		return output;		
	}
}
