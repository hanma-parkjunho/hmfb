package hmfb.batch;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import hmfb.batch.service.F0500100Service;
import hmfb.core.dto.BatchJobContext;
import hmfb.core.dto.F0500100Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.dto.T0500100Dto;
import hmfb.core.exception.HmfbException;
import hmfb.framework.batch.biz.IChunkBatchJob;
import hmfb.framework.batch.db.BatchDao;
import lombok.extern.log4j.Log4j2;

/**
 * INPUT 데이터  : 
 * OUTPUT     : 모계좌잔액 조회 인터페이스  
 *  
 * @author KDK
 *
 */
@Log4j2
public class BJF0500100 implements IChunkBatchJob {
	
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
	public T0500100Dto process(Object param, BatchJobContext ctx) throws HmfbException {
		
		T0500100Dto input = (T0500100Dto)param;
		T0500100Dto output = new T0500100Dto();
		
		F0500100Dto inFirmDto = new F0500100Dto();
		
		inFirmDto.setPrtsacnutNo(input.getPrtsacnutNo());               //모계좌번호 
		inFirmDto.setVirtlAcnutNo(input.getVirtlAcnutNo());             //가상계좌번호 
		//inFirmDto.setBlceSmbol(input.getBlceSmbol());                   //계좌 잔액부호
		//inFirmDto.setBlce(input.getBlce().toString());                             //계좌 잔액 
		//inFirmDto.setDefrayPosblAmount(input.getDefrayPosblAmount().toString());   //계좌 출금가능금액
		
		FirmReturnDto returnDto = F0500100Service.getService(F0500100Service.class).f0500100Service(inFirmDto, input.getTelemsgNo());
		F0500100Dto outFirmDto = (F0500100Dto) returnDto.getRtnObj();
		if("0000".equals(returnDto.getCommonDto().getRecvCode())) {
			output.setBlceSmbol(outFirmDto.getBlceSmbol());                   				  //계좌 잔액부호
			output.setBlce(new BigDecimal(outFirmDto.getBlce()));                             //계좌 잔액 
			output.setDefrayPosblAmount(new BigDecimal(outFirmDto.getDefrayPosblAmount()));   //계좌 출금가능금액
			output.setRspnsMssage("");
		} else {
			output.setRspnsMssage("ERROR");
		}
		output.setRegDate(returnDto.getCommonDto().getTranDt());
		output.setTelemsgNo(returnDto.getCommonDto().getTlgmSeqNo());
		output.setRspnsCode(returnDto.getCommonDto().getRecvCode());
		output.setSendYn("Y");
		BatchDao.getDao().update("T0500100.updateT0500100", output);
		if (log.isDebugEnabled()) {
			log.debug("F0500100 전문 응답 처리 완료");
			log.debug("전문 응답 내용:"+returnDto);
		}
//		D2D 일 경우 dummy 를 리턴. 
		return output;		
	}

}
