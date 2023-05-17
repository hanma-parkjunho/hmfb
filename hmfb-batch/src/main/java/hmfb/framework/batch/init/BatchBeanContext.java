package hmfb.framework.batch.init;

import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;

import hmfb.framework.batch.constant.BatchConstants;
import hmfb.framework.batch.task.CustomBatchTasklet;
import hmfb.framework.batch.task.PreProcessTasklet;

@Configuration
@EnableBatchProcessing
public class BatchBeanContext {

	@Autowired
	StepBuilderFactory stepBuilderFactory;

	@Bean
	@JobScope
	public Step preProcessStep() {
		return stepBuilderFactory.get("preProcess")
					.tasklet(new PreProcessTasklet()).build();
	}

	/** 
	 * 
	 * @StepScope 필수 : ItemReader/ItemWriter 는 상태를 가지므로 여러 스텝에서 동시 사용되면 오동작 발생. 이런 상황을 방지.
	 * 								Step 이 실행되는 시점에 빈이 생성. 초기화 지연 효과.
	 */
	@Bean
	@JobScope
	public Step customBatchStep() {
		
		DefaultTransactionAttribute txAttr = new DefaultTransactionAttribute(TransactionAttribute.PROPAGATION_NOT_SUPPORTED);
		return stepBuilderFactory.get("CustomBatchProcess")
				.tasklet(new CustomBatchTasklet()).transactionAttribute(txAttr).build();
	}
	
	/**
	 * 
	 * @return
	 */
	@Bean
	public JobParametersValidator jobParamValidator() {
		
		String[] requiredKeys = new String[] {
				
															BatchConstants.BAT_PARAM_JOB_YMD,
															BatchConstants.BAT_PARAM_JOB_CODE,
															BatchConstants.BAT_PARAM_RETURN_YN,
															BatchConstants.BAT_PARAM_RUN_PARAM
														};
		String[] optionalKeys = new String[] {};
		
		return new DefaultJobParametersValidator(requiredKeys, optionalKeys);
	}

}
