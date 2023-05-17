package hmfb.framework.batch.biz;

import hmfb.core.dto.BatchJobContext;
import hmfb.core.exception.HmfbException;
/**
 * 업무 배치를 구현할 때 이 인터페이스를 구현해야 함.
 * @author KDK
 *
 */
public interface IBatchJob {
	/**
	 * 해당 배치가 실행 가능한 조건인지 검증. 배치 모니터링에서 정상 종료로 표시됨. <br/>
	 * 예) 일배치일 경우 휴일이면 실행 안되는 조건, 업무 마감일 때만 실행 등. <br/>
	 * @param batchJobDto
	 * @return
	 * @throws HmfbException
	 */
	default public boolean isExecutable(BatchJobContext ctx) throws HmfbException { return true; }
	/** 전처리 : 본처리 수행 전 한번만 수행 */
	public void preProcess(BatchJobContext ctx) throws HmfbException;

	/** 후처리 : 본처리 에러 여부에 상관없이 본처리 수행 후 한번만 수행.<br/>
	 * 전처리에서 에러가 발생하면 본처리 skip 하고 후처리 수행.
	 * @param
	 * @param t	예외가 발생한 경우 발생한 예외 객체 
	 */
	default public void postProcess(BatchJobContext ctx, Throwable t) throws HmfbException {}
}
