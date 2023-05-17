package hmfb.batch.sample;

import java.lang.reflect.InvocationTargetException;
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
/**
 * 샘플코드에서 사용한 데이터 예시
 * ------------------------------------
 * 배치 유형      : F2D (CSV)
 * INPUT 데이터  : /springbank/src/test/resources/input/files/fwtest_YYYYMMDD.csv  => 일자별 파일명이 변경되는 경우.
 * OUTPUT 데이터 : TDEP10001.openAccount 
 * 
 * @author kmk
 *
 */
public class BJDEP10001 implements IChunkBatchJob {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean isExecutable(BatchJobContext ctx) throws HmfbException {
		
		if(logger.isDebugEnabled()) {
			logger.debug("[업무로그]"+getClass().getSimpleName()+".isExecutable 실행");
		}

		DayOfWeek day = LocalDate.now().getDayOfWeek();
//		일요일엔 정상 종료 리턴. (배치를 수행시키지 않는다. 상태는 FAILED 가 아닌 COMPLETED)
		return (day.equals(DayOfWeek.SUNDAY)) ? false : true;
	}
	
	@Override
	public void preProcess(BatchJobContext ctx) throws HmfbException {
		if(logger.isDebugEnabled()) {
			logger.debug("[업무로그]"+getClass().getSimpleName()+".preProcess 실행");
			logger.debug("[업무로그]"+ctx.getInputDataSelector());
		}
//		파일명 symobol replace : 실제로 두번째 인자는 공통모듈이 제공하는 today() 함수를 사용.
		ctx.replaceInputFileName("YYYYMMDD", "20221220");  
		
//		파일 IO 주입
		FileIOMetaDTO ioMeta = new FileIOMetaDTO();
		ioMeta.setInputClassName("hmfb.core.dto.AccountDDTO");
		ioMeta.setInputAttributeNames(new String[] {"accountNo","productCode","cifNo","balance","openDate","status"});
		ioMeta.setOutputAttributeNames(new String[] {"accountNo","productCode","cifNo","balance","openDate","status"});
		
		ctx.setIoMeta(ioMeta);
	}

	/**
	 * 생략 가능 
	 */
	@Override
	public void postProcess(BatchJobContext ctx, Throwable t) throws HmfbException {
		if(logger.isDebugEnabled()) {
			logger.debug("[업무로그]"+getClass().getSimpleName()+".postProcess 실행");
		}
		if (t != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("[업무로그]예외발생 시 후처리에서 할 일은 여기에 구현 ");
			}
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
