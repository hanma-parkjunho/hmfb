package hmfb.batch;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import hmfb.batch.service.F3000101Service;
import hmfb.core.dto.BatchJobContext;
import hmfb.core.dto.F3000101Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.dto.T3100101Dto;
import hmfb.core.exception.HmfbException;
import hmfb.framework.batch.biz.IChunkBatchJob;
import hmfb.framework.batch.db.BatchDao;
import lombok.extern.log4j.Log4j2;

/**
 * 샘플코드에서 사용한 데이터 예시
 * ------------------------------------
 * 배치 유형      : D2D
 * INPUT 데이터  : TDEP10001.selectAccountListByCondition
 * OUTPUT     : 타행이체 결과 불능분 통지  
 *  
 * @author KDK
 *
 */
@Log4j2
public class BJF3000101 implements IChunkBatchJob {
	
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
	public T3100101Dto process(Object param, BatchJobContext ctx) throws HmfbException {
		
		T3100101Dto input = (T3100101Dto)param;
		T3100101Dto output = new T3100101Dto();
		
		F3000101Dto inFirmDto = new F3000101Dto();
//		inFirmDto.setTelemsgNo(input.getTelemsgNo());
		inFirmDto.setOrgCode(input.getOrgCode());
		inFirmDto.setCompanyCode(input.getCompanyCode());
		inFirmDto.setBankCode(input.getBankCode());
//		inFirmDto.setDelngAmount(input.getDelngAmount().toString());
//		inFirmDto.setDpstrNm(input.getDpstrNm());
		FirmReturnDto returnDto = F3000101Service.getService(F3000101Service.class).f3000101Service(inFirmDto, input.getTelemsgNo());
		F3000101Dto outFirmDto = (F3000101Dto) returnDto.getRtnObj();
		
		if("0000".equals(returnDto.getCommonDto().getRecvCode())) {
			output.setProfessCode(outFirmDto.getProfessCode());
			output.setRspnsMssage("");
		} else {
			output.setRspnsMssage("ERROR");
		}
		
		BatchDao.getDao().update("T3000101.updateT3000101", output);
		if (log.isDebugEnabled()) {
			log.debug("F3000101 전문 응답 처리 완료");
			log.debug("전문 응답 내용:" + returnDto);
		}
//		D2D 일 경우 dummy 를 리턴.
		return output;
	}
}
