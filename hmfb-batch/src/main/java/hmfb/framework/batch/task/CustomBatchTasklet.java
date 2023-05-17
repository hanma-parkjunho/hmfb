package hmfb.framework.batch.task;

import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import hmfb.core.dto.BatchJobContext;
import hmfb.core.exception.HmfbException;
import hmfb.framework.batch.biz.ICustomBatchJob;
import hmfb.framework.batch.constant.BatchConstants;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomBatchTasklet implements Tasklet, StepExecutionListener {
	
	ICustomBatchJob batchJob = null;
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {		
		
		BatchJobContext ctx = (BatchJobContext)chunkContext.getStepContext().getStepExecution().getJobExecution()
																.getExecutionContext().get(BatchConstants.KEY_BAT_CTX);
		try {
			batchJob.process(ctx);
			return RepeatStatus.FINISHED;
		} catch (Throwable t) {
			contribution.setExitStatus(ExitStatus.FAILED);
			throw new RuntimeException(t);
		}
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		
		if (log.isTraceEnabled()) {
			log.trace("[HMFB-BATCH로그]"+getClass().getSimpleName()+".beforeStep 실행");
		}
		
		BatchJobContext ctx = (BatchJobContext)stepExecution.getJobExecution()
											.getExecutionContext().get(BatchConstants.KEY_BAT_CTX);
//		업무 BatchJob 인스턴스 생성
		try {			
			Class clazz = Class.forName(ctx.getProgramName());
			this.batchJob = (ICustomBatchJob) clazz.getDeclaredConstructor().newInstance();			
		} catch (Exception e) { 
			throw new RuntimeException(e); 
		}
		try {
			batchJob.preProcess(ctx);
		} catch (HmfbException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		
		if (log.isTraceEnabled()) {
			log.trace("[HMFB-BATCH로그]"+getClass().getSimpleName()+".afterStep 실행");
		}
		BatchJobContext ctx = (BatchJobContext)stepExecution.getJobExecution()
				.getExecutionContext().get(BatchConstants.KEY_BAT_CTX);

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
	
}
