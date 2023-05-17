package hmfb.batch;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import hmfb.batch.service.F0400100Service;
import hmfb.core.dto.BatchJobContext;
import hmfb.core.dto.F0400100Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.dto.T0400100Dto;
import hmfb.core.exception.HmfbException;
import hmfb.framework.batch.biz.IChunkBatchJob;
import hmfb.framework.batch.db.BatchDao;
import lombok.extern.log4j.Log4j2;

/**
 * INPUT 데이터  : 
 * OUTPUT     : 이체처리결과 조회 인터페이스  
 *  
 * @author KDK
 *
 */
@Log4j2
public class BJF0400100 implements IChunkBatchJob {
	
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
	public T0400100Dto process(Object param, BatchJobContext ctx) throws HmfbException {
		
		T0400100Dto input = (T0400100Dto)param;
		T0400100Dto output = new T0400100Dto();
		
		F0400100Dto inFirmDto = new F0400100Dto();
		
		inFirmDto.setOridelngDe(input.getOridelngDe());                                  //원거래일자
		inFirmDto.setOritelemsgNo(input.getOritelemsgNo());                              //원거래 전문번호
		inFirmDto.setProcessResultInqireSe(input.getProcessResultInqireSe());            //처리결과 조회 구분
//		inFirmDto.setOridelngRspnsCode(input.getOridelngRspnsCode());                    //원거래 응답코드
//		inFirmDto.setOridelngProcessTime(input.getOridelngProcessTime());                //원거래 처리시간
//		inFirmDto.setDefrayAcnutNo(input.getDefrayAcnutNo());                            //출금계좌번호
//		inFirmDto.setVirtlAcnutNo(input.getVirtlAcnutNo());                              //가상계좌번호
//		inFirmDto.setDelngAmount(input.getDelngAmount().toString());                                //거래금액
//		inFirmDto.setBlceSmbol(input.getBlceSmbol());                                    //출금거래후 잔액부호
//		inFirmDto.setBlce(input.getBlce().toString());                                              //출금거래후 잔액
//		inFirmDto.setThrghAcnutNo1(input.getThrghAcnutNo1());                            //경유계좌번호1
//		inFirmDto.setThrghAcnutNo2(input.getThrghAcnutNo2());                            //경유계좌번호2
//		inFirmDto.setBankCode(input.getBankCode());                                      //입금은행 코드
//		inFirmDto.setAcnutNo(input.getAcnutNo());                                        //입금계좌 번호
//		inFirmDto.setIrSafSe(input.getIrSafSe());                                        //IR / SAF 구분
//		inFirmDto.setFee(input.getFee().toString());                                                //수수료
//		inFirmDto.setCmsCode(input.getCmsCode());                                        //입금인번호
//		inFirmDto.setDefrayAcnutPrntxt(input.getDefrayAcnutPrntxt());                    //출금계좌 인자내역
//		inFirmDto.setThrghAcnut1RcpmnyPrntxt(input.getThrghAcnut1RcpmnyPrntxt());        //경유계좌1 입금인자내역
//		inFirmDto.setThrghAcnut1DefrayPrntxt(input.getThrghAcnut1DefrayPrntxt());        //경유계좌1 출금인자내역
//		inFirmDto.setThrghAcnut2RcpmnyPrntxt(input.getThrghAcnut2RcpmnyPrntxt());        //경유계좌2 입금인자내역
//		inFirmDto.setThrghAcnut2DefrayPrntxt(input.getThrghAcnut2DefrayPrntxt());        //경유계좌2 출금인자내역
//		inFirmDto.setRcpmnyAcnutPrntxt(input.getRcpmnyAcnutPrntxt());                    //입금계좌 인자내역
//		inFirmDto.setCinetDelngInnb(input.getCinetDelngInnb());                          //공동망 거래고유번호
		
		FirmReturnDto returnDto = F0400100Service.getService(F0400100Service.class).f0400100Service(inFirmDto, input.getTelemsgNo());
		F0400100Dto outFirmDto = (F0400100Dto) returnDto.getRtnObj();
		if("0000".equals(returnDto.getCommonDto().getRecvCode())) {
			output.setOridelngRspnsCode(outFirmDto.getOridelngRspnsCode());                    //원거래 응답코드
			output.setOridelngProcessTime(outFirmDto.getOridelngProcessTime());                //원거래 처리시간
			output.setDefrayAcnutNo(outFirmDto.getDefrayAcnutNo());                            //출금계좌번호
			output.setVirtlAcnutNo(outFirmDto.getVirtlAcnutNo());                              //가상계좌번호
			output.setDelngAmount(new BigDecimal(outFirmDto.getDelngAmount()));                //거래금액
			output.setBlceSmbol(outFirmDto.getBlceSmbol());                                    //출금거래후 잔액부호
			output.setBlce(new BigDecimal(outFirmDto.getBlce()));                              //출금거래후 잔액
			output.setThrghAcnutNo1(outFirmDto.getThrghAcnutNo1());                            //경유계좌번호1
			output.setThrghAcnutNo2(outFirmDto.getThrghAcnutNo2());                            //경유계좌번호2
			output.setBankCode(outFirmDto.getBankCode());                                      //입금은행 코드
			output.setAcnutNo(outFirmDto.getAcnutNo());                                        //입금계좌 번호
			output.setIrSafSe(outFirmDto.getIrSafSe());                                        //IR / SAF 구분
			output.setFee(new BigDecimal(outFirmDto.getFee()));                                //수수료
			output.setCmsCode(outFirmDto.getCmsCode());                                        //입금인번호
			output.setDefrayAcnutPrntxt(outFirmDto.getDefrayAcnutPrntxt());                    //출금계좌 인자내역
			output.setThrghAcnut1RcpmnyPrntxt(outFirmDto.getThrghAcnut1RcpmnyPrntxt());        //경유계좌1 입금인자내역
			output.setThrghAcnut1DefrayPrntxt(outFirmDto.getThrghAcnut1DefrayPrntxt());        //경유계좌1 출금인자내역
			output.setThrghAcnut2RcpmnyPrntxt(outFirmDto.getThrghAcnut2RcpmnyPrntxt());        //경유계좌2 입금인자내역
			output.setThrghAcnut2DefrayPrntxt(outFirmDto.getThrghAcnut2DefrayPrntxt());        //경유계좌2 출금인자내역
			output.setRcpmnyAcnutPrntxt(outFirmDto.getRcpmnyAcnutPrntxt());                    //입금계좌 인자내역
			output.setCinetDelngInnb(outFirmDto.getCinetDelngInnb());                          //공동망 거래고유번호
			output.setRspnsMssage("");
		} else {
			output.setRspnsMssage("ERROR");
		}
		output.setRegDate(returnDto.getCommonDto().getTranDt());
		output.setTelemsgNo(returnDto.getCommonDto().getTlgmSeqNo());
		output.setRspnsCode(returnDto.getCommonDto().getRecvCode());
		output.setSendYn("Y");
		BatchDao.getDao().update("T0400100.updateT0400100", output);
		if (log.isDebugEnabled()) {
			log.debug("F0400100 전문 응답 처리 완료");
			log.debug("전문 응답 내용:"+returnDto);
		}
//		D2D 일 경우 dummy 를 리턴. 
		return output;		
	}

}
