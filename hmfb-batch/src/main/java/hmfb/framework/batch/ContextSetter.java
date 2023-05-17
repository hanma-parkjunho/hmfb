package hmfb.framework.batch;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.context.ApplicationContext;

import hmfb.core.dto.BatchJobContext;
import hmfb.core.dto.BatchJobDTO;
import hmfb.core.management.ApplicationContextProvider;
import hmfb.framework.batch.constant.BatchConstants;
import hmfb.framework.batch.prop.BatchProps;

public class ContextSetter {

	public static BatchJobContext createBatchJobContext(JobParameters params, BatchJobDTO batchJobDto) {
		
		BatchJobContext ctx = new BatchJobContext();
		
		try {
			
			ctx.setJobYmd(params.getString(BatchConstants.BAT_PARAM_JOB_YMD));
			ctx.setJobCode(params.getString(BatchConstants.BAT_PARAM_JOB_CODE));
			ctx.setReturnYn(params.getString(BatchConstants.BAT_PARAM_RETURN_YN));
			ctx.setRunParam(params.getString(BatchConstants.BAT_PARAM_RUN_PARAM));
			ctx.setJobUuid(params.getString(BatchConstants.BAT_PARAM_JOB_UUID));
			
			BeanUtils.copyProperties(ctx, batchJobDto);			
			
			ApplicationContext context = ApplicationContextProvider.getApplicationContext();
			BatchProps batchProps = context.getBean(BatchProps.class);
			
			/*
			 * Chunk 배치이고 F2D 또는 D2F 이면 
			 */
			if (StringUtils.equals(batchJobDto.getJobType(), BatchConstants.JOB_TYPE_F2D)) {
				String input = batchProps.getInputRootDir() + File.separator + ctx.getInputDataSelector();
				ctx.setInputDataSelector(input); // 절대 경로로 재설정	
			}
			if (StringUtils.equals(batchJobDto.getJobType(), BatchConstants.JOB_TYPE_D2F)) {
				String output = batchProps.getOutputRootDir() + File.separator + ctx.getOutputDataSelector();
				ctx.setOutputDataSelector(output); // 절대 경로로 재설정	
			} 
			
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		} 
		
		return ctx;
	}
	
	public static void updateBatchJobContext(BatchJobContext ctx, StepExecution execution) {
		/*
		 * ctx.setTotalCount(execution.getReadCount()); 
		 * ctx.setCompleted();
		 * ctx.setCurrentIndex(0);
		 */
		
	}
	
}
