package hmfb.batch;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import hmfb.batch.service.F0300300Service;
import hmfb.core.dto.BatchJobContext;
import hmfb.core.dto.F0300300Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.dto.T0300300Dto;
import hmfb.core.exception.HmfbException;
import hmfb.framework.batch.biz.IChunkBatchJob;
import hmfb.framework.batch.db.BatchDao;
import lombok.extern.log4j.Log4j2;

/**
 * INPUT 데이터  : 
 * OUTPUT     : 가상계좌별 과세정보 등록 인터페이스  
 *  
 * @author KDK
 *
 */
@Log4j2
public class BJF0300300 implements IChunkBatchJob {
	
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
	public T0300300Dto process(Object param, BatchJobContext ctx) throws HmfbException {
		
		T0300300Dto input = (T0300300Dto)param;
		T0300300Dto output = new T0300300Dto();
		
		F0300300Dto inFirmDto = new F0300300Dto();
		
		inFirmDto.setPrtsacnutNo(input.getPrtsacnutNo());       //모계좌번호
		inFirmDto.setVirtlAcnutNo(input.getVirtlAcnutNo());     //가상계좌번호
		inFirmDto.setTaxtTy(input.getTaxtTy());                 //과세유형
		inFirmDto.setBsnmInfo(input.getBsnmInfo());             //실명/사업자번호
		inFirmDto.setInsttNm(input.getInsttNm());               //기관명
		inFirmDto.setInsttRprsntvNm(input.getInsttRprsntvNm()); //기관대표자명
		
		FirmReturnDto returnDto = F0300300Service.getService(F0300300Service.class).f0300300Service(inFirmDto, input.getTelemsgNo());
		//F0300300Dto outFirmDto = (F0300300Dto) returnDto.getRtnObj();
		if("0000".equals(returnDto.getCommonDto().getRecvCode())) {
			output.setRspnsMssage("");
		} else {
			output.setRspnsMssage("ERROR");
		}
		output.setRegDate(returnDto.getCommonDto().getTranDt());
		output.setTelemsgNo(returnDto.getCommonDto().getTlgmSeqNo());
		output.setRspnsCode(returnDto.getCommonDto().getRecvCode());
		output.setSendYn("Y");
		BatchDao.getDao().update("T0300300.updateT0300300", output);
		if (log.isDebugEnabled()) {
			log.debug("F0300300 전문 응답 처리 완료");
			log.debug("전문 응답 내용:"+returnDto);
		}
//		D2D 일 경우 dummy 를 리턴. 
		return output;		
	}

}
