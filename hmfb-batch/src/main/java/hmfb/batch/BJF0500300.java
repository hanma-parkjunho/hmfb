package hmfb.batch;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import hmfb.batch.service.F0500300Service;
import hmfb.core.dto.BatchJobContext;
import hmfb.core.dto.F0500300Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.dto.T0500300Dto;
import hmfb.core.exception.HmfbException;
import hmfb.framework.batch.biz.IChunkBatchJob;
import hmfb.framework.batch.db.BatchDao;
import lombok.extern.log4j.Log4j2;

/**
 * INPUT 데이터  : 
 * OUTPUT     : 해지이자 정보 조회 인터페이스  
 *  
 * @author KDK
 *
 */
@Log4j2
public class BJF0500300 implements IChunkBatchJob {
	
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
	public T0500300Dto process(Object param, BatchJobContext ctx) throws HmfbException {
		
		T0500300Dto input = (T0500300Dto)param;
		T0500300Dto output = new T0500300Dto();
		
		F0500300Dto inFirmDto = new F0500300Dto();
		
		inFirmDto.setVirtlAcnutno(input.getVirtlAcnutno());       //계좌번호
		inFirmDto.setBizrno(input.getBizrno());                   //실명번호(사업자번호)
		inFirmDto.setStdrDe(input.getStdrDe());                   //기준일자
//		inFirmDto.setNewDe(input.getNewDe());                     //신규일
//		inFirmDto.setGoodsCode(input.getGoodsCode());             //상품코드
//		inFirmDto.setAcnutBlce(input.getAcnutBlce().toString());             //계좌잔액
//		inFirmDto.setTrmnatBlce(input.getTrmnatBlce().toString());           //해지금액
//		inFirmDto.setBfrxIntr(input.getBfrxIntr().toString());               //세전이자
//		inFirmDto.setAfttxIntr(input.getAfttxIntr().toString());             //세후이자
//		inFirmDto.setCrrx(input.getCrrx().toString());                       //법인세
//		inFirmDto.setIncmtax(input.getIncmtax().toString());                 //소득세
//		inFirmDto.setIhnts(input.getIhnts().toString());                     //주민세
//		inFirmDto.setAgspt(input.getAgspt().toString());                     //농특세
//		inFirmDto.setFee(input.getFee().toString());                         //수수료금액
//		inFirmDto.setDelngStdrDe(input.getDelngStdrDe());         //거래기준일자
//		inFirmDto.setDelngSeCn(input.getDelngSeCn());             //거래구분내용
//		inFirmDto.setGoodsNm(input.getGoodsNm());                 //상품명
//		inFirmDto.setApplcTaskNm(input.getApplcTaskNm());         //적용과세명
//		inFirmDto.setIncomeBeginDe(input.getIncomeBeginDe());     //소득시작일자
//		inFirmDto.setIncomeEndDe(input.getIncomeEndDe());         //소득종료일자
		FirmReturnDto returnDto = F0500300Service.getService(F0500300Service.class).f0500300Service(inFirmDto, input.getTelemsgNo());
		F0500300Dto outFirmDto = (F0500300Dto) returnDto.getRtnObj();
		if("0000".equals(returnDto.getCommonDto().getRecvCode())) {
			output.setNewDe(outFirmDto.getNewDe());                     //신규일
			output.setGoodsCode(outFirmDto.getGoodsCode());             //상품코드
			output.setAcnutBlce(new BigDecimal(outFirmDto.getAcnutBlce()));             //계좌잔액
			output.setTrmnatBlce(new BigDecimal(outFirmDto.getTrmnatBlce()));           //해지금액
			output.setBfrxIntr(new BigDecimal(outFirmDto.getBfrxIntr()));               //세전이자
			output.setAfttxIntr(new BigDecimal(outFirmDto.getAfttxIntr()));             //세후이자
			output.setCrrx(new BigDecimal(outFirmDto.getCrrx()));                       //법인세
			output.setIncmtax(new BigDecimal(outFirmDto.getIncmtax()));                 //소득세
			output.setIhnts(new BigDecimal(outFirmDto.getIhnts()));                     //주민세
			output.setAgspt(new BigDecimal(outFirmDto.getAgspt()));                     //농특세
			output.setFee(new BigDecimal(outFirmDto.getFee()));                         //수수료금액
			output.setDelngStdrDe(outFirmDto.getDelngStdrDe());         //거래기준일자
			output.setDelngSeCn(outFirmDto.getDelngSeCn());             //거래구분내용
			output.setGoodsNm(outFirmDto.getGoodsNm());                 //상품명
			output.setApplcTaskNm(outFirmDto.getApplcTaskNm());         //적용과세명
			output.setIncomeBeginDe(outFirmDto.getIncomeBeginDe());     //소득시작일자
			output.setIncomeEndDe(outFirmDto.getIncomeEndDe());         //소득종료일자
			output.setRspnsMssage("");
		} else {
			output.setRspnsMssage("ERROR");
		}
		output.setRegDate(returnDto.getCommonDto().getTranDt());
		output.setTelemsgNo(returnDto.getCommonDto().getTlgmSeqNo());
		output.setRspnsCode(returnDto.getCommonDto().getRecvCode());
		output.setSendYn("Y");
		BatchDao.getDao().update("T0500300.updateT0500300", output);
		if (log.isDebugEnabled()) {
			log.debug("F0500300 전문 응답 처리 완료");
			log.debug("전문 응답 내용:"+returnDto);
		}
//		D2D 일 경우 dummy 를 리턴. 
		return output;		
	}

}
