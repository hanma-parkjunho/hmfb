package hmfb.batch;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import hmfb.batch.service.F0200300Service;
import hmfb.core.dto.BatchJobContext;
import hmfb.core.dto.F0200300Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.dto.T0200300Dto;
import hmfb.core.exception.HmfbException;
import hmfb.framework.batch.biz.IChunkBatchJob;
import hmfb.framework.batch.db.BatchDao;
import lombok.extern.log4j.Log4j2;

/**
 * INPUT 데이터  : 
 * OUTPUT     : 3자이체 인터페이스  
 *  
 * @author KDK
 *
 */
@Log4j2
public class BJF0200300 implements IChunkBatchJob {
	
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
	public T0200300Dto process(Object param, BatchJobContext ctx) throws HmfbException {
		
		T0200300Dto input = (T0200300Dto)param;
		T0200300Dto output = new T0200300Dto();
		
		F0200300Dto inFirmDto = new F0200300Dto();
		
		inFirmDto.setDefrayAcnutNo(input.getDefrayAcnutNo());               //출금계좌번호
		inFirmDto.setVirtlAcnutNo(input.getVirtlAcnutNo());                 //가상계좌번호
		inFirmDto.setRevwSmbol(input.getRevwSmbol());                       //복기부호
		inFirmDto.setDelngAmount(input.getDelngAmount().toString());        //거래금액
		//inFirmDto.setBlceSmbol(input.getBlceSmbol());                     //출금거래후 잔액부호
		//inFirmDto.setBlce(input.getBlce().toString());                    //출금거래후 잔액
		inFirmDto.setThrghAcnutNo(input.getThrghAcnutNo());					//경유계좌번호
		inFirmDto.setBankCode(input.getBankCode());                         //입금은행 코드
		inFirmDto.setAcnutNo(input.getAcnutNo());                           //입금계좌 번호
		inFirmDto.setIrSafSe(input.getIrSafSe());                           //IR / SAF 구분
		//inFirmDto.setFee(input.getFee().toString());                      //수수료
		inFirmDto.setCmsCode(input.getCmsCode());                           //입금인번호(CMS코드)
		inFirmDto.setDefrayAcnutPrntxt(input.getDefrayAcnutPrntxt());       //출금계좌 인자내역
		inFirmDto.setThrghAcnutRcpmnyPrntxt(input.getThrghAcnutRcpmnyPrntxt()); //경유계좌 입금인자내역
		inFirmDto.setThrghAcnutDefrayPrntxt(input.getThrghAcnutDefrayPrntxt() );//경유계좌 출금인자내역
		inFirmDto.setRcpmnyAcnutPrntxt(input.getRcpmnyAcnutPrntxt());       //입금계좌 인자내역
		//inFirmDto.setCinetDelngInnb(input.getCinetDelngInnb());           //공동망 거래고유번호
		FirmReturnDto returnDto = F0200300Service.getService(F0200300Service.class).f0200300Service(inFirmDto, input.getTelemsgNo());
		F0200300Dto outFirmDto = (F0200300Dto) returnDto.getRtnObj();
		if("0000".equals(returnDto.getCommonDto().getRecvCode())) {
			output.setBlceSmbol(outFirmDto.getBlceSmbol());                     //출금거래후 잔액부호
			output.setBlce(new BigDecimal(outFirmDto.getBlce()));               //출금거래후 잔액
			output.setFee(new BigDecimal(outFirmDto.getFee()));                 //수수료
			output.setCinetDelngInnb(outFirmDto.getCinetDelngInnb());           //공동망 거래고유번호
			output.setRspnsMssage("");
		} else {
			output.setRspnsMssage("ERROR");
		}
		output.setRegDate(returnDto.getCommonDto().getTranDt());
		output.setTelemsgNo(returnDto.getCommonDto().getTlgmSeqNo());
		output.setRspnsCode(returnDto.getCommonDto().getRecvCode());
		output.setSendYn("Y");
		BatchDao.getDao().update("T0200300.updateT0200300", output);
		if (log.isDebugEnabled()) {
			log.debug("F0200300 전문 응답 처리 완료");
			log.debug("전문 응답 내용:"+returnDto);
		}
//		D2D 일 경우 dummy 를 리턴. 
		return output;		
	}

}
