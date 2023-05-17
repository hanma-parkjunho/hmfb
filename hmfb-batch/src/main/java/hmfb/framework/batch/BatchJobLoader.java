package hmfb.framework.batch;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import hmfb.core.dto.BatchJobDTO;
import hmfb.framework.batch.constant.BatchConstants;
import hmfb.framework.batch.listener.BatchJobExecutionListener;
import lombok.extern.log4j.Log4j2;

/**
 * 1. JobLauncher : 배치 어플리케이션을 기동하기 위한 인터페이스 . 그 외 CommandLineJobRunner 를 사용해서 배치 수행 가능.
 * 2. Job	
 * 3. Step : Job 구성하는 세부 단위. 작업의 크기를 분할하기 위한 목적.
 * 		- Chunk방식  : 일정량의 데이터를 한번에 몰아서 입력,가공,출력
 * 		- Tasklet방식 : 처리 방법을 자유롭게 기술. Job - Step - Tasklet 으로 구성 
 * 
 * 4. ItemReader, ItemProcessor, ItemWriter : Chunk방식의 tasklet 을 구현시 사용 
 * 5. JobRepository : Job, Step 의 상태를 관리. SpringBatch 가 정의한 테이블 스키마.
 * 
 * @author KDK
 *
 */
@Log4j2
@Component
public class BatchJobLoader {
	
	@Autowired
	JobBuilderFactory jobBuilderFactory;	
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	@Autowired
	ChunkFlowComposer composer;
	
	@Autowired
	JobParametersValidator jobParamValidator;
	
	@Autowired
	@Qualifier("customBatchStep")
	Step customBatchStep;
	
	@Autowired
	@Qualifier("preProcessStep")
	Step preProcessStep;
		
	public Job load(BatchJobDTO batchJobDto) {
		if (log.isTraceEnabled()) {
			log.trace("[HMFB-BATCH로그]"+getClass().getSimpleName()+".load 실행 ");
		}
		JobBuilder jobBuilder = jobBuilderFactory.get(batchJobDto.getJobCode())
								.validator(jobParamValidator)
//								.incrementer(new RunIdIncrementer()) // 버그가 있었음. preventRestart 를 대신 이용 
								.preventRestart()	// 동일 파라미터 배치 작업 금지. 수시일 경우 BatchJobLauncher 에서 파리미터 추가.
								.listener(new BatchJobExecutionListener());	

		Step processStep = null;
		String jobType = batchJobDto.getJobType();
//		Custom Batch 일 경우 taskletStep 
		if (StringUtils.equals(jobType, BatchConstants.JOB_TYPE_CUSTOM)) {
			processStep = customBatchStep;
		} else {
			processStep = composer.compose(batchJobDto);
		}
		
		return jobBuilder.start(preProcessStep).next(processStep).build();
				
//		TODO : 파티션 고려 안됨. 
		/* 선후행 : jobFlow. 데이터 파이프라인 구축도 되겠음. 
		 * return jobBuilder.start(preStep).on("COMPLETED").to(processStep)
		 * .from(preStep).on("FAILED").to(postStep)
		 * .from(processStep).next(postStep).end() .build();
		 */
	}

}
