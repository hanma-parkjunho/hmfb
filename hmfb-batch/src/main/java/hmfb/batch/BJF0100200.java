package hmfb.batch;

import java.time.DayOfWeek;
import java.time.LocalDate;

import hmfb.batch.service.F0100200Service;
import hmfb.core.dto.BatchJobContext;
import hmfb.core.dto.F0100200Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.dto.T0100100Dto;
import hmfb.core.exception.HmfbException;
import hmfb.framework.batch.biz.IChunkBatchJob;
import hmfb.framework.batch.db.BatchDao;
import lombok.extern.log4j.Log4j2;

/**
 * INPUT 데이터  : 
 * OUTPUT     : 업무종료 인터페이스  
 *  
 * @author KDK
 *
 */
@Log4j2
public class BJF0100200 implements IChunkBatchJob {
	
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
	public T0100100Dto process(Object param, BatchJobContext ctx) throws HmfbException {
		
		T0100100Dto output = new T0100100Dto();
		
		F0100200Dto inFirmDto = new F0100200Dto();
		
		FirmReturnDto returnDto = F0100200Service.getService(F0100200Service.class).f0100200Service(inFirmDto);

		if("0000".equals(returnDto.getCommonDto().getRecvCode())) {
			output.setRspnsMssage("");
		} else {
			output.setRspnsMssage("ERROR");
		}
		output.setJobDt(returnDto.getCommonDto().getTranDt());
		output.setJobTm(returnDto.getCommonDto().getTranTm());
		output.setJobSe("02");
		output.setRspnsCode(returnDto.getCommonDto().getRecvCode());
		BatchDao.getDao().insert("T0100100.insertT0100100", output);
		if (log.isDebugEnabled()) {
			log.debug("F0100200 전문 응답 처리 완료");
			log.debug("전문 응답 내용:"+returnDto);
		}
//		D2D 일 경우 dummy 를 리턴. 
		return output;		
	}

}
