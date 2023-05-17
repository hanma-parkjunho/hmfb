package hmfb.batch.sample;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

import hmfb.batch.sample.service.F0500200Service;
import hmfb.core.dto.AccountDDTO;
import hmfb.core.dto.BatchJobContext;
import hmfb.core.dto.F0500200Dto;
import hmfb.core.exception.HmfbException;
import hmfb.framework.batch.biz.IChunkBatchJob;
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
public class BJF0500200 implements IChunkBatchJob {
	
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
		ctx.putCondition("status", "00");
		ctx.putCondition("balance", new BigDecimal(3000));
		
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
	public AccountDDTO process(Object input, BatchJobContext ctx) throws HmfbException {
		
		AccountDDTO output = new AccountDDTO();
		
		F0500200Dto inFirmDto = new F0500200Dto();
		/* 
		 * 실제 업무 코드에서 아래 유형대로 구현
		 * DB 에서 읽은 데이터(input)로 전문 객체를(inFirmDto) 조립
		inFirmDto.setRsdtNo(input.getRsdtNo());
		inFirmDto.setBnkCd(input.getBnkCd());
		inFirmDto.setAcctNo(input.getAcctNo());
		inFirmDto.setAccount(input.getAccount());
		inFirmDto.setName(input.getName());
		inFirmDto.setFiller();
		*/
//			테스트를 위한 코드 
//		inFirmDto.setRsdtNo("7701011011511");
//		inFirmDto.setBnkCd("088");
//		inFirmDto.setAcctNo("123456789012345");
//		inFirmDto.setAccount("1234567890123");
//		inFirmDto.setName("");
//		inFirmDto.setFiller("");
		
//		bean name 으로 조회. 실제 업무 소스에 동일한 타입으로 생성되어 있어서 로딩 시 충돌 발생.
		F0500200Dto returnDto = F0500200Service.getService("batch.f0500200Service", F0500200Service.class).f0500200Service(inFirmDto);
		if (log.isDebugEnabled()) {
			log.debug("F0500200 전문 응답 처리 완료");
			log.debug("전문 응답 내용:"+returnDto);
		}
		
		/*
		응답 내용을 DB 에 insert 또는 update 할 경우.
		BatchDao.getDao().update("TDEP10001.updateAccount", returnDto);
		BatchDao.getDao().insert("TDEP20001.insertTransaction", returnDto);
		*/
//		D2D 일 경우 dummy 를 리턴. 
		return output;		
	}

}
