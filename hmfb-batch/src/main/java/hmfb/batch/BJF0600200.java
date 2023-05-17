package hmfb.batch;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import hmfb.batch.service.F0600200Service;
import hmfb.core.dto.BatchJobContext;
import hmfb.core.dto.F0600200Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.dto.T0600200Dto;
import hmfb.core.exception.HmfbException;
import hmfb.framework.batch.biz.IChunkBatchJob;
import hmfb.framework.batch.db.BatchDao;
import lombok.extern.log4j.Log4j2;

/**
 * INPUT 데이터  : 
 * OUTPUT     : 거래 명세 통지 결번 요청 인터페이스  
 *  
 * @author KDK
 *
 */
@Log4j2
public class BJF0600200 implements IChunkBatchJob {
	
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
	public T0600200Dto process(Object param, BatchJobContext ctx) throws HmfbException {
		
		T0600200Dto input = (T0600200Dto) param;
		T0600200Dto output = new T0600200Dto();
		
		F0600200Dto inFirmDto = new F0600200Dto();
		
		inFirmDto.setDelngDe(input.getDelngDe());                    //거래일자
		inFirmDto.setDelngSn(input.getDelngSn());                    //거래일련번호
//		inFirmDto.setAcnutNo(input.getAcnutNo());                    //거래계좌번호
//		inFirmDto.setSeCode(input.getSeCode());                      //거래구분코드
//		inFirmDto.setDelngAmount(input.getDelngAmount().toString());            //거래금액
//		inFirmDto.setBlceSmbol(input.getBlceSmbol());                //잔액부호
//		inFirmDto.setBlce(input.getBlce().toString());                          //계좌잔액
//		inFirmDto.setDelngSumry(input.getDelngSumry());              //거래적요
//		inFirmDto.setDelngTime(input.getDelngTime());                //거래시간
//		inFirmDto.setBankCode(input.getBankCode());                  //거래은행코드
//		inFirmDto.setDelngBhfCode(input.getDelngBhfCode());          //거래점코드
//		inFirmDto.setCmsCode(input.getCmsCode());                    //입금인 번호(CMS 코드)
//		inFirmDto.setCheckBilNo(input.getCheckBilNo());              //수표/어음번호
//		inFirmDto.setCsrccAmount(input.getCsrccAmount().toString());            //자기앞수표 금액
//		inFirmDto.setPrsnlchkAmount(input.getPrsnlchkAmount().toString());      //가계수표 금액
//		inFirmDto.setGenchkAmount(input.getGenchkAmount().toString());          //일반수표 금액
//		inFirmDto.setDelngMediaTy(input.getDelngMediaTy().toString());          //거래매체유형
		
		FirmReturnDto returnDto = F0600200Service.getService(F0600200Service.class).f0600200Service(inFirmDto, input.getTelemsgNo());
		F0600200Dto outFirmDto = (F0600200Dto) returnDto.getRtnObj();
		if("0000".equals(returnDto.getCommonDto().getRecvCode())) {
//			output.setDelngDe(outFirmDto.getDelngDe());                    //거래일자
//			output.setDelngSn(outFirmDto.getDelngSn());                    //거래일련번호
			output.setAcnutNo(outFirmDto.getAcnutNo());                    //거래계좌번호
			output.setSeCode(outFirmDto.getSeCode());                      //거래구분코드
			output.setDelngAmount(new BigDecimal(outFirmDto.getDelngAmount()));            //거래금액
			output.setBlceSmbol(outFirmDto.getBlceSmbol());                //잔액부호
			output.setBlce(new BigDecimal(outFirmDto.getBlce()));                          //계좌잔액
			output.setDelngSumry(outFirmDto.getDelngSumry());              //거래적요
			output.setDelngTime(outFirmDto.getDelngTime());                //거래시간
			output.setBankCode(outFirmDto.getBankCode());                  //거래은행코드
			output.setDelngBhfCode(outFirmDto.getDelngBhfCode());          //거래점코드
			output.setCmsCode(outFirmDto.getCmsCode());                    //입금인 번호(CMS 코드)
			output.setCheckBilNo(outFirmDto.getCheckBilNo());              //수표/어음번호
			output.setCsrccAmount(new BigDecimal(outFirmDto.getCsrccAmount()));            //자기앞수표 금액
			output.setPrsnlchkAmount(new BigDecimal(outFirmDto.getPrsnlchkAmount()));      //가계수표 금액
			output.setGenchkAmount(new BigDecimal(outFirmDto.getGenchkAmount()));          //일반수표 금액
			output.setDelngMediaTy(outFirmDto.getDelngMediaTy());          //거래매체유형
			output.setRspnsMssage("");
		} else {
			output.setRspnsMssage("ERROR");
		}
		output.setRegDate(returnDto.getCommonDto().getTranDt());
		output.setTelemsgNo(returnDto.getCommonDto().getTlgmSeqNo());
		output.setRspnsCode(returnDto.getCommonDto().getRecvCode());
		output.setSendYn("Y");
		BatchDao.getDao().update("T0600200.updateT0600200", output);
		if (log.isDebugEnabled()) {
			log.debug("F0600200 전문 응답 처리 완료");
			log.debug("전문 응답 내용:"+returnDto);
		}
//		D2D 일 경우 dummy 를 리턴. 
		return output;		
	}

}
