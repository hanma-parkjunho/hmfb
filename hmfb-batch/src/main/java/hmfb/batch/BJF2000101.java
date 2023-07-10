package hmfb.batch;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import hmfb.batch.service.F2000101Service;
import hmfb.core.dto.BatchJobContext;
import hmfb.core.dto.F2000101Dto;
import hmfb.core.dto.FileIOMetaDTO;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.dto.T2100101Dto;
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
public class BJF2000101 implements IChunkBatchJob {
	
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
		
		// 파일 IO 주입
		FileIOMetaDTO ioMeta = new FileIOMetaDTO();
		ioMeta.setOutputAttributeNames(new String[]
		{"accountNo", "productCode", "cifNo" , "balance"});
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
	public T2100101Dto process(Object param, BatchJobContext ctx) throws HmfbException {
		
		T2100101Dto input = (T2100101Dto)param;
		T2100101Dto output = new T2100101Dto();
		
		F2000101Dto inFirmDto = new F2000101Dto();
		inFirmDto.setTelemsgNo(input.getTelemsgNo());
		if(log.isDebugEnabled()) {
			log.debug(inFirmDto+"inFirmDto 값 확인 ");
		}
					
		FirmReturnDto returnDto = F2000101Service.getService(F2000101Service.class).f2000101Service(inFirmDto, input.getTelemsgNo());
		
		if("0000".equals(returnDto.getCommonDto().getRecvCode())) {
//			output.setProfessCode(outFirmDto.getProfessCode());				// 수취인
			output.setRspnsMssage("들어옴");
		} else {
			output.setRspnsMssage("ERROR");
		}
		
		BatchDao.getDao().update("T2000101.updateT2000101", output);		// 
		if (log.isDebugEnabled()) {
			log.debug("F2000101 전문 응답 처리 완료");								// 
			log.debug("전문 응답 내용:" + returnDto);
		}
//		D2D 일 경우 dummy 를 리턴. 
		return output;		
	}
}
