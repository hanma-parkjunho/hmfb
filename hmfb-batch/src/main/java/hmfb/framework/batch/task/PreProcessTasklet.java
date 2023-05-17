package hmfb.framework.batch.task;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import hmfb.core.dto.BatchJobContext;
import hmfb.core.exception.HmfbException;
import hmfb.framework.batch.biz.IBatchJob;
import hmfb.framework.batch.constant.BatchConstants;
import lombok.extern.log4j.Log4j2;
/**
 * 모든 배치의 실행 가능 여부 메소드 호출
 * 업무 배치의 전처리 호출은 beforeStep 에서 호출 : 
 * - 업무 배치에서 인스턴스 변수를 전처리에서 설정할 경우 
 *    업무 배치의 preProcess/process/postProcess 에서 해당 변수 공유 가능.
 * 
 * @author KDK
 *
 */
@Log4j2
public class PreProcessTasklet implements Tasklet {
	
	IBatchJob batchJob = null;
	BatchJobContext ctx = null;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
		BatchJobContext ctx = (BatchJobContext)stepExecution.getJobExecution()
				.getExecutionContext().get(BatchConstants.KEY_BAT_CTX);

		this.ctx = ctx;

//		업무 BatchJob 인스턴스 생성
		try {
			Class clazz = Class.forName(ctx.getProgramName());
			this.batchJob = (IBatchJob) clazz.getDeclaredConstructor().newInstance();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (!isExecutable()) {
//			UNKNOWN : assumed not continuable
//			contribution.setExitStatus(ExitStatus.UNKNOWN);
			contribution.setExitStatus(new ExitStatus(BatchConstants.NORMAL_TERMINATED));
		}
		/*
		대체 방법 : StepListener 에서. ExitStatus 의 status code 를 customizing.
	    public ExitStatus afterStep(StepExecution stepExecution) {
	        String exitCode = stepExecution.getExitStatus().getExitCode();
	        if (!exitCode.equals(ExitStatus.FAILED.getExitCode()) &&
	              stepExecution.getSkipCount() > 0) {
	            return new ExitStatus("COMPLETED WITH SKIPS");
	        }
		}
		*/
		return RepeatStatus.FINISHED;
	}

	private boolean isExecutable()  {
		
		try {
			return batchJob.isExecutable(ctx);
		} catch (HmfbException e) {
			log.error("", e);
			return false;
		}
	}
}
