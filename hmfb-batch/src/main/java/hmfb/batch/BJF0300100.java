package hmfb.batch;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import hmfb.batch.service.F0300100Service;
import hmfb.core.dto.BatchJobContext;
import hmfb.core.dto.F0300100Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.dto.T0300100Dto;
import hmfb.core.exception.HmfbException;
import hmfb.framework.batch.biz.IChunkBatchJob;
import hmfb.framework.batch.db.BatchDao;
import lombok.extern.log4j.Log4j2;

/**
 * INPUT 데이터  : 
 * OUTPUT     : 경유계좌등록 인터페이스  
 *  
 * @author KDK
 *
 */
@Log4j2
public class BJF0300100 implements IChunkBatchJob {
	
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
	public T0300100Dto process(Object param, BatchJobContext ctx) throws HmfbException {
		
		T0300100Dto input = (T0300100Dto)param;
		T0300100Dto output = new T0300100Dto();
		
		F0300100Dto inFirmDto = new F0300100Dto();
		
		inFirmDto.setAgremSn(input.getAgremSn());                  				//협약일련번호
		inFirmDto.setPmsId(input.getPmsId());                    				//PMS-ID
		inFirmDto.setAgremInsttId(input.getAgremInsttId());             		//협약기관ID
		inFirmDto.setWctAcnutno(input.getWctAcnutno());               			//사업비 계좌번호
		inFirmDto.setWctAcnut1BankCode(input.getWctAcnut1BankCode());        	//사업비계좌1 은행코드
		inFirmDto.setWctAcnut1No(input.getWctAcnut1No());              			//사업비계좌1 번호
		inFirmDto.setWctAcnut2BankCode(input.getWctAcnut2BankCode());        	//사업비계좌2 은행코드
		inFirmDto.setWctAcnut2No(input.getWctAcnut2No());              			//사업비계좌2 번호
		inFirmDto.setSpcltyInsttBizrno(input.getSpcltyInsttBizrno());        	//전담기관 사업자번호
		inFirmDto.setSpcltyInsttNm(input.getSpcltyInsttNm());            		//전담기관 명칭
		inFirmDto.setAgremInstt1Bizrno(input.getAgremInstt1Bizrno());        	//협약기관1 사업자번호
		inFirmDto.setAgremInstt1Nm(input.getAgremInstt1Nm());            		//협약기관1 명칭
		inFirmDto.setAgremInstt2Bizrno(input.getAgremInstt2Bizrno());        	//협약기관2 사업자번호
		inFirmDto.setAgremInstt2Nm(input.getAgremInstt2Nm());            		//협약기관2 명칭
		inFirmDto.setGvrnDntAmount(input.getGvrnDntAmount().toString());            //정부출연금액
		inFirmDto.setLocgovBndCashAmount(input.getLocgovBndCashAmount().toString());      //지자체부담 현금금액
		inFirmDto.setPrvateBndCashAmount(input.getPrvateBndCashAmount().toString());      //민간부담 현금금액
		inFirmDto.setAgremLmtAmount(input.getAgremLmtAmount().toString());           //협약한도금액
		inFirmDto.setGvrnDntRcpmnyDe(input.getGvrnDntRcpmnyDe());          		//정부출연금 입금일자
		inFirmDto.setLocgovBndRcpmnyDe(input.getLocgovBndRcpmnyDe());        	//지자체부담금 입금일자
		inFirmDto.setPrvateBndRcpmnyDe(input.getPrvateBndRcpmnyDe());        	//민간부담금 입금일자
		inFirmDto.setTaskId(input.getTaskId());                   				//과제ID
		inFirmDto.setTaskNm(input.getTaskNm());                   				//과제명
		inFirmDto.setBsnsClCode(input.getBsnsClCode());               			//사업분류코드
		inFirmDto.setBsnsClNm(input.getBsnsClNm());                 			//사업분류명
		FirmReturnDto returnDto = F0300100Service.getService(F0300100Service.class).f0300100Service(inFirmDto, input.getTelemsgNo());
		//F0300100Dto outFirmDto = (F0300100Dto) returnDto.getRtnObj();
		if("0000".equals(returnDto.getCommonDto().getRecvCode())) {
			output.setRspnsMssage("");
		} else {
			output.setRspnsMssage("ERROR");
		}
		output.setRegDate(returnDto.getCommonDto().getTranDt());
		output.setTelemsgNo(returnDto.getCommonDto().getTlgmSeqNo());
		output.setRspnsCode(returnDto.getCommonDto().getRecvCode());
		output.setSendYn("Y");
		BatchDao.getDao().update("T0300100.updateT0300100", output);
		if (log.isDebugEnabled()) {
			log.debug("F0300100 전문 응답 처리 완료");
			log.debug("전문 응답 내용:"+returnDto);
		}
//		D2D 일 경우 dummy 를 리턴. 
		return output;		
	}

}
