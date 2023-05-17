package hmfb.batch.sample;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hmfb.core.dto.AccountDDTO;
import hmfb.core.dto.BatchJobContext;
import hmfb.core.dto.FileIOMetaDTO;
import hmfb.core.exception.HmfbException;
import hmfb.framework.batch.biz.IChunkBatchJob;
import hmfb.framework.batch.constant.BatchConstants;

/**
 * 샘플코드에서 사용한 데이터 예시
 * ------------------------------------
 * 배치 유형      : D2F (FIXED)
 * INPUT 데이터  : TDEP10001.selectAccountListByCondition
 * OUTPUT 데이터 : /springbank/src/test/resources/output/files/export_account_YYYYMMDD.sam  => 일자별 파일명이 변경되는 경우. 
 * 
 * @author KDK
 *
 */
public class BJDEP10004 implements IChunkBatchJob {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public boolean isExecutable(BatchJobContext ctx) throws HmfbException {
		
		if(logger.isDebugEnabled()) {
			logger.debug("[업무로그]"+getClass().getSimpleName()+".isExecutable 실행");
		}
		
		DayOfWeek day = LocalDate.now().getDayOfWeek();
//		일요일엔 정상 종료 리턴. (배치를 수행시키지 않는다. 상태는 FAILED 가 아닌 COMPLETED)
		return (day.equals(DayOfWeek.SUNDAY)) ? false : true;
	}
	
	public void preProcess(BatchJobContext ctx) throws HmfbException {
		if(logger.isDebugEnabled()) {
			logger.debug("[업무로그]"+getClass().getSimpleName()+".preProcess 실행");
			logger.debug("[업무로그]"+ctx.getInputDataSelector());
		}
//		DB 조회 조건을 입력
		ctx.putCondition("status", "00");
		ctx.putCondition("balance", new BigDecimal(3000));
		
//		파일 IO 주입
		FileIOMetaDTO ioMeta = new FileIOMetaDTO();
		ioMeta.setFileEncoding("KSC5601");
		ctx.replaceOutputFileName("YYYYMMDD", "20221220");  
		ioMeta.setFileType(BatchConstants.FILE_TYPE_FIXED);
		
		ioMeta.setOutputAttributeNames(new String[] {"accountNo","productCode","cifNo","balance","openDate","status"});
		ioMeta.setOutputLineFormat("%1s%1s%1s%1s%1s%1s");
		ctx.setIoMeta(ioMeta);
	}
	/**
	 * 생략 가능 
	 */
	public void postProcess(BatchJobContext ctx) throws HmfbException {
		if(logger.isDebugEnabled()) {
			logger.debug("[업무로그]"+getClass().getSimpleName()+".postProcess 실행");
		}
	}
	/**
	 *  생략 가능 : 생략 시 itemReader 에서 읽은 객체를 itemWriter 로 bypass.
	 */
	@Override
	public AccountDDTO process(Object input, BatchJobContext ctx) throws HmfbException {
		
		AccountDDTO output = new AccountDDTO();
		if(logger.isDebugEnabled()) {
			logger.debug("[업무로그]"+getClass().getSimpleName()+".process 실행");
		}
//		writer 에 기록될 output 생성. 
		try {
			BeanUtils.copyProperties(output, input);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new HmfbException(e);
		}
		
		return output;		
	}

}
