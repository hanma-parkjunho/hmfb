package hmfb.framework.batch.biz;

import hmfb.core.dto.BatchJobContext;
import hmfb.core.exception.HmfbException;
/**
 * 업무 배치를 구현할 때 이 인터페이스를 구현해야 함.
 * @author KDK
 *
 */
public interface ICustomBatchJob extends IBatchJob {
	
	/** 본처리 */
	public void process(BatchJobContext ctx) throws HmfbException;
}
