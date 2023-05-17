package hmfb.framework.batch.task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.AfterChunkError;
import org.springframework.batch.core.annotation.AfterProcess;
import org.springframework.batch.core.annotation.AfterRead;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.annotation.BeforeRead;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.annotation.OnProcessError;
import org.springframework.batch.core.annotation.OnReadError;
import org.springframework.batch.core.annotation.OnWriteError;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.Nullable;

import hmfb.core.dto.BatchExecutionDTO;
import hmfb.core.dto.BatchJobContext;
import hmfb.core.exception.HmfbException;
import hmfb.framework.batch.BatchExecutionManager;
import hmfb.framework.batch.biz.IChunkBatchJob;
import hmfb.framework.batch.constant.BatchConstants;
import lombok.extern.log4j.Log4j2;
/**
 * before/after Step 은 tx context 가 아님.
 * ===============================
 * chunk 가 실행되는 순서
 * ===============================
 * chunk size 2일경우
 * 
 * 1. 전처리 step 실행
 * 2. 본처리 step 실행 : beforeChunk - beforRead - afterRead - beforRead - afterRead - process - 업무 - afterProcess 
 *                   - process - 업무 - afterProcess - insert 2번 수행 - afterWrite 
 *                   - AfterChunk
 * @author KDK
 *
 */
@Log4j2
public class ChunkBatchItemProcessor implements ItemProcessor {
	
	IChunkBatchJob batchJob = null;
	BatchJobContext ctx = null;
		
	@Override
	public Object process(Object item) throws Exception {
		if (log.isTraceEnabled()) {
			log.trace("[HMFB-BATCH로그]"+getClass().getSimpleName()+".process 실행");
			log.trace("item:"+item);
		}
		return batchJob.process(item, ctx);
	}
	/*********************************************************************************
	 * 
	 * ChunkOrient Step 의 before/after EventHandler
	 * - 현재 업무 배치의 전처리기/후처리기 호출에 이용되고 있음. 
	 * 
	 *********************************************************************************/
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		
		if (log.isTraceEnabled()) {
			log.trace("[HMFB-BATCH로그]"+getClass().getSimpleName()+".beforeStep 실행");
		}
		
		BatchJobContext ctx = (BatchJobContext)stepExecution.getJobExecution()
										.getExecutionContext().get(BatchConstants.KEY_BAT_CTX);		
		this.ctx = ctx;		
		//업무 BatchJob 인스턴스 생성
		try {
			Class clazz = Class.forName(ctx.getProgramName());
			this.batchJob = (IChunkBatchJob) clazz.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		try {
//			file io 에 대한 정의를 전처리에서 필수적으로 해야 한다. 
			batchJob.preProcess(ctx);
		} catch (HmfbException e) {
			throw new RuntimeException(e);
		}

		ExecutionContext stepExecutionContext = stepExecution.getExecutionContext();
		stepExecutionContext.put(BatchConstants.KEY_FILE_IO, ctx.getIoMeta());
		if (StringUtils.equals(ctx.getJobType().substring(0, 1), BatchConstants.JOB_IO_TYPE_FILE)) {
			String filePath = ctx.getInputDataSelector();
			int totalCount = countLines(filePath, '\n');
			ctx.setTotalCount(totalCount);
		}
//		배치실행
		BatchExecutionDTO lastExecution = BatchExecutionManager.getService().selectExecution(ctx.getJobUuid());
		lastExecution.setProcStatus(BatchConstants.PROC_STATUS_RUNNING);
		lastExecution.setTotalCount(ctx.getTotalCount());
		lastExecution.setCompletedCount(ctx.getCompleted());
		
		BatchExecutionManager.getService().updateExecution(lastExecution);
	}

	private int countLines(String filePath, int lfChar) {
				
		int total = 0;
		int lastChar = 0;
		Reader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filePath));
			int readChar;
			while((readChar = reader.read()) != -1) {
				if (readChar == lfChar) total++;
				lastChar = readChar;
			}
			if (lastChar != lfChar) total++;
			
			return total;
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					log.error("close error", e);
				}
			}
		}
	}
	@AfterStep
	public ExitStatus afterStep(StepExecution stepExecution) {
		if (log.isTraceEnabled()) {
			log.trace("[HMFB-BATCH로그]"+getClass().getSimpleName()+".afterStep 실행");
		}
		List<Throwable> throwables = stepExecution.getFailureExceptions();
		
		try {
			Throwable t = null;
			if (throwables != null && throwables.size() >0) {
				t = (Throwable)throwables.get(0);
			}
			batchJob.postProcess(ctx, t);
						
			return stepExecution.getExitStatus();
			
		} catch (HmfbException e) {
			throw new RuntimeException(e);
		}
		
	}
	/*********************************************************************************
	 * 
	 * ItemReader, ItemWriter, ItemProcessor 의 before/after EventHandler
	 * - 현재 건수 관리에 이용되고 있음. 
	 * 
	 *********************************************************************************/
	@BeforeRead
	void beforeRead() {
		if (log.isTraceEnabled()) {
			log.trace("[HMFB-BATCH로그]"+getClass().getSimpleName()+".beforeRead 실행");
		}
	}
	@AfterRead
	void afterRead(Object item) {
		if (log.isTraceEnabled()) {
			log.trace("[HMFB-BATCH로그]"+getClass().getSimpleName()+".afterRead 실행(item)["+item+"]");
		}		
		ctx.setCurrentIndex(ctx.getCurrentIndex()+1);
	}	
	@OnReadError
	void onReadError(Exception e) throws HmfbException {
		log.error("[HMFB-BATCH로그]"+getClass().getSimpleName()+".onReadError 실행 :", e);
//		읽을 때 오류가 발생해도 
		ctx.setCurrentIndex(ctx.getCurrentIndex()+1);
		throw new HmfbException(e);
	}
	@AfterProcess
	void afterProcess(Object item, @Nullable Object result) {
		if (log.isTraceEnabled()) {
			log.trace("[HMFB-BATCH로그]"+getClass().getSimpleName()+".afterProcess 실행");
		}
	}
	@OnProcessError
	void onProcessError(Object item, Exception e) throws HmfbException {
//		log 가 필요. skippable 일 경우 ex 을 throw 해도 에러 스택이 로깅되지 않는다. 
		log.error("[HMFB-BATCH로그]"+getClass().getSimpleName()+".onProcessError 실행 :", e);
	}
	@AfterWrite
	void afterWrite(List items) {
		if (log.isTraceEnabled()) {
			log.trace("[HMFB-BATCH로그]"+getClass().getSimpleName()+".afterWrite 실행");
		}
//		청크별 write
		ctx.setCompleted(ctx.getCompleted()+items.size());
	}
	@OnWriteError
	void onWriteError(Exception e, List items) throws HmfbException {
//		log 가 필요. skippable 일 경우 ex 을 throw 해도 에러 스택이 로깅되지 않는다. 
		log.error("[HMFB-BATCH로그]"+getClass().getSimpleName()+".onWriteError 실행 :", e);
		throw new HmfbException(e);
	}
	@BeforeChunk
	void beforeChunk(ChunkContext context) {
		if (log.isTraceEnabled()) {
			log.trace("[HMFB-BATCH로그]"+getClass().getSimpleName()+".beforeChunk 실행");
		}
	}
	@AfterChunk
	void AfterChunk(ChunkContext context) {
		if (log.isTraceEnabled()) {
			log.trace("[HMFB-BATCH로그]"+getClass().getSimpleName()+".AfterChunk 실행");
		}
//		배치실행
		BatchExecutionDTO lastExecution = BatchExecutionManager.getService().selectExecution(ctx.getJobUuid());
		
		lastExecution.setTotalCount(ctx.getTotalCount());
		lastExecution.setCompletedCount(ctx.getCompleted());
		
		BatchExecutionManager.getService().updateExecution(lastExecution);
	}
	
	@AfterChunkError
	void afterChunkError(ChunkContext context) {
		if (log.isTraceEnabled()) {
			log.trace("[HMFB-BATCH로그]"+getClass().getSimpleName()+".afterChunkError 실행");
		}
	}
}
