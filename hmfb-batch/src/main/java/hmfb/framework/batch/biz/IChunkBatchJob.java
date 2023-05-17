package hmfb.framework.batch.biz;

import hmfb.core.dto.BatchJobContext;
import hmfb.core.exception.HmfbException;
/**
 * 업무 배치를 구현할 때 이 인터페이스를 구현해야 함.
 * 업무 배치에서 process 를 구현하지 않으면 itemREader 에서 itemWriter 로 input 객체를 bypass.
 * @author KDK
 *
 */
public interface IChunkBatchJob extends IBatchJob {
	/** 본처리 */
	default public Object process(Object input, BatchJobContext ctx) throws HmfbException { return input; };
}
