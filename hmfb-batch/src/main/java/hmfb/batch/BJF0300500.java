package hmfb.batch;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;

import hmfb.batch.service.F0300500Service;
import hmfb.core.dto.BatchJobContext;
import hmfb.core.dto.F0300500Dto;
import hmfb.core.dto.FirmReturnDto;
import hmfb.core.dto.T0300500Dto;
import hmfb.core.exception.HmfbException;
import hmfb.framework.batch.biz.IChunkBatchJob;
import hmfb.framework.batch.db.BatchDao;
import lombok.extern.log4j.Log4j2;

/**
 * INPUT 데이터  : 
 * OUTPUT     : 주관기관 모계좌별 가상계좌 요청 인터페이스  
 *  
 * @author KDK
 *
 */
@Log4j2
public class BJF0300500 implements IChunkBatchJob {

	@Value("${hmfb.firm.id}")
    private String firmId;
	
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
	public T0300500Dto process(Object param, BatchJobContext ctx) throws HmfbException {
		
		T0300500Dto input = (T0300500Dto)param;
		T0300500Dto output = new T0300500Dto();
		
		F0300500Dto inFirmDto = new F0300500Dto();
		
		inFirmDto.setOrgCode(firmId);								  //기관코드
		inFirmDto.setAgencyCode(input.getAgencyCode());               //대리점코드
		inFirmDto.setRequstAcnutCnt(input.getRequstAcnutCnt());     		//요청계좌건수
//		inFirmDto.setBeginVirtlAcnutno(input.getBeginVirtlAcnutno());     	//시작가상계좌번호
//		inFirmDto.setEndVirtlAcnutno(input.getEndVirtlAcnutno());     		//종료가상계좌번호
		FirmReturnDto returnDto = F0300500Service.getService(F0300500Service.class).f0300500Service(inFirmDto, input.getTelemsgNo());
		F0300500Dto outFirmDto = (F0300500Dto) returnDto.getRtnObj();
		if("0000".equals(returnDto.getCommonDto().getRecvCode())) {
			output.setBeginVirtlAcnutno(outFirmDto.getBeginVirtlAcnutno());     	//시작가상계좌번호
			output.setEndVirtlAcnutno(outFirmDto.getEndVirtlAcnutno());     		//종료가상계좌번호
			output.setRspnsMssage("");
		} else {
			output.setRspnsMssage("ERROR");
		}
		output.setRegDate(returnDto.getCommonDto().getTranDt());
		output.setTelemsgNo(returnDto.getCommonDto().getTlgmSeqNo());
		output.setRspnsCode(returnDto.getCommonDto().getRecvCode());
		output.setSendYn("Y");
		BatchDao.getDao().update("T0300500.updateT0300500", output);
		if (log.isDebugEnabled()) {
			log.debug("F0300500 전문 응답 처리 완료");
			log.debug("전문 응답 내용:"+returnDto);
		}
//		D2D 일 경우 dummy 를 리턴. 
		return output;		
	}

}
