package hmfb.batch;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import hmfb.batch.service.F0300200Service;
import hmfb.core.dto.BatchJobContext;
import hmfb.core.dto.F0300200Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.dto.T0300200Dto;
import hmfb.core.exception.HmfbException;
import hmfb.framework.batch.biz.IChunkBatchJob;
import hmfb.framework.batch.db.BatchDao;
import lombok.extern.log4j.Log4j2;

/**
 * INPUT 데이터  : 
 * OUTPUT     : 경유계좌해지 인터페이스  
 *  
 * @author KDK
 *
 */
@Log4j2
public class BJF0300200 implements IChunkBatchJob {
	
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
	public T0300200Dto process(Object param, BatchJobContext ctx) throws HmfbException {
		
		T0300200Dto input = (T0300200Dto)param;
		T0300200Dto output = new T0300200Dto();
		
		F0300200Dto inFirmDto = new F0300200Dto();
		
		inFirmDto.setAgremSn(input.getAgremSn());             	//협약일련번호
		inFirmDto.setPmsId(input.getPmsId());               	//PMS-ID
		inFirmDto.setAgremInsttId(input.getAgremInsttId());     //협약기관ID
		inFirmDto.setTrmnatSe(input.getTrmnatSe());         //해지구분
		
		FirmReturnDto returnDto = F0300200Service.getService(F0300200Service.class).f0300200Service(inFirmDto, input.getTelemsgNo());
		//F0300200Dto outFirmDto = (F0300200Dto) returnDto.getRtnObj();
		if("0000".equals(returnDto.getCommonDto().getRecvCode())) {
			output.setRspnsMssage("");
		} else {
			output.setRspnsMssage("ERROR");
		}
		output.setRegDate(returnDto.getCommonDto().getTranDt());
		output.setTelemsgNo(returnDto.getCommonDto().getTlgmSeqNo());
		output.setRspnsCode(returnDto.getCommonDto().getRecvCode());
		output.setSendYn("Y");
		BatchDao.getDao().update("T0300200.updateT0300200", output);
		if (log.isDebugEnabled()) {
			log.debug("F0300200 전문 응답 처리 완료");
			log.debug("전문 응답 내용:"+returnDto);
		}
		//		D2D 일 경우 dummy 를 리턴. 
		return output;		
	}

}
