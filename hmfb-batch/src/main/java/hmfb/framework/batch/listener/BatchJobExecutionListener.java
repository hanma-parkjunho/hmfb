package hmfb.framework.batch.listener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;

import hmfb.core.dto.BatchExecutionDTO;
import hmfb.core.dto.BatchJobContext;
import hmfb.core.dto.BatchJobDTO;
import hmfb.framework.batch.BatchExecutionManager;
import hmfb.framework.batch.ContextSetter;
import hmfb.framework.batch.constant.BatchConstants;
import hmfb.framework.batch.db.BatchDao;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class BatchJobExecutionListener extends JobExecutionListenerSupport {
		
	@Override
	public void beforeJob(JobExecution jobExecution) {
		
		if (log.isTraceEnabled()) {
			log.trace("[HMFB-BATCH로그]:"+getClass().getSimpleName()+".beforeJob 실행");
		}
		
		JobParameters params = jobExecution.getJobParameters();
		String jobCode = params.getString(BatchConstants.BAT_PARAM_JOB_CODE);

		BatchJobDTO batchJobDto = BatchDao.getDao().selectOne("framework.getBatchJob", jobCode);
		BatchJobContext ctx = ContextSetter.createBatchJobContext(params, batchJobDto);

		ExecutionContext executionContext = jobExecution.getExecutionContext();
		executionContext.put(BatchConstants.KEY_BAT_CTX, ctx);
		
		if (log.isTraceEnabled()) {
			log.trace("[HMFB-BATCH로그]"+getClass().getSimpleName()+". 실행내역 상태 갱신");
		}

		BatchExecutionDTO lastExecution = BatchExecutionManager.getService().selectExecution(ctx.getJobUuid());
		lastExecution.setJobExecutionId(jobExecution.getId());
		lastExecution.setCommitCount(batchJobDto.getCommitCount());
		lastExecution.setInputDataSelector(batchJobDto.getInputDataSelector());
		lastExecution.setOutputDataSelector(batchJobDto.getOutputDataSelector());
		lastExecution.setExceptionPolicy(batchJobDto.getExceptionPolicy());
		lastExecution.setProcStatus(BatchConstants.PROC_STATUS_START);
		
		BatchExecutionManager.getService().updateExecution(lastExecution);
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {			
		if (log.isTraceEnabled()) {
			log.trace("[HMFB-BATCH로그]:"+getClass().getSimpleName()+".afterJob 실행");
		}		
		BatchJobContext ctx = (BatchJobContext)jobExecution.getExecutionContext().get(BatchConstants.KEY_BAT_CTX);
		
		BatchExecutionDTO lastExecution = BatchExecutionManager.getService().selectExecution(ctx.getJobUuid());
		
		String ndtm = LocalDateTime.now().format(DateTimeFormatter.ofPattern(BatchConstants.FMT_YMD_MLS));		
		lastExecution.setEndDtm(ndtm);
		lastExecution.setProcStatus(BatchConstants.PROC_STATUS_COMPLETED);
		
		BatchExecutionManager.getService().updateExecution(lastExecution);
	}
}
