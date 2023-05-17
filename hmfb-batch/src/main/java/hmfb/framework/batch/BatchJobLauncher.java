package hmfb.framework.batch;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hmfb.core.dto.BatchExeParamDTO;
import hmfb.core.dto.BatchExeResultDTO;
import hmfb.core.dto.BatchExecutionDTO;
import hmfb.core.dto.BatchJobContext;
import hmfb.core.dto.BatchJobDTO;
import hmfb.core.exception.ErrorCode;
import hmfb.core.exception.HmfbException;
import hmfb.core.log.service.LogService;
import hmfb.framework.batch.constant.BatchConstants;
import hmfb.framework.batch.db.BatchDao;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class BatchJobLauncher {
	
	@Autowired
	private BatchDao batchDao;
	
	@Autowired
	LogService logService;
	
	@Autowired
	BatchJobLoader jobLoader;
	
	@Autowired
	JobLauncher  jobLauncher;
	/*
	 * As of version 5.0, the @EnableBatchProcessing annotation automatically registers a job operator bean in the application context.
	 */
	@Autowired
	JobOperator jobOperator;
	
	@Autowired
	BatchExecutionManager executionMgr;

	public BatchJobLauncher() {
	}
	
	@SuppressWarnings("finally")
	public BatchExeResultDTO execute(BatchExeParamDTO param) {
		
//		
		BatchExeResultDTO result = new BatchExeResultDTO(param.getJobCode());
		JobExecution resultJobExecution =  null;
		try {
			
			BatchExecutionDTO batchExecution = executionMgr.insertExecution(param);
			
			BatchJobDTO batchJobDto = batchDao.selectOne("framework.getBatchJob", batchExecution.getJobCode());
			if (batchJobDto == null) {
				throw new RuntimeException(new HmfbException(ErrorCode.E812, 
									String.format("배치 식별자가 존재하지 않습니다. 배치기본정보를 확인하세요. 배치식별자[%s]",param.getJobCode())));
			}
//			사용여부가 Y 가 아니면 오류 
			if (!StringUtils.equals(batchJobDto.getUseYn(), BatchConstants.YN_Y)) {
				throw new RuntimeException(new HmfbException(ErrorCode.E821, 
									String.format("해당 배치의 사용여부가 사용안함으로 설정되어 있습니다. 배치기본정보를 확인하세요. 배치식별자[%s]",param.getJobCode())));
			}
			
			JobParametersBuilder jpb = new JobParametersBuilder()
												.addString(BatchConstants.BAT_PARAM_JOB_YMD, param.getJobYmd())
												.addString(BatchConstants.BAT_PARAM_JOB_CODE, param.getJobCode())
												.addString(BatchConstants.BAT_PARAM_RETURN_YN, param.getReturnYn())
												.addString(BatchConstants.BAT_PARAM_RUN_PARAM, param.getRunParam())
												.addString(BatchConstants.BAT_PARAM_JOB_UUID, batchExecution.getJobUuid());
//			수시 배치일 경우 parameter 를 다르게 해서 여러번 수행 가능하게 한다.
//			일배치가 성공할 경우 재실행하면 다음 오류 발생 > A job instance already exists and is complete for parameters={...}
			/*
			 * jobUuid 를 파라미터로 등록하면 아래 로직이 필요없음. 
			 * if (StringUtils.equals(batchJobDto.getJobCycle(),
			 * BatchConstants.BAT_CYCLE_ANY)) { // String strNow =
			 * LocalDate.now().format(DateTimeFormatter.ofPattern(UncConstants.FMT_YMD_HMS))
			 * ; jpb = jpb.addDate(BatchConstants.BAT_PARAM_JOB_UUID, new Date(), true); }
			 */
			JobParameters jobParams = jpb.toJobParameters();
			
			Job job = jobLoader.load(batchJobDto);
			resultJobExecution =  jobLauncher.run(job, jobParams);
			
//			TODO : 결과 저장. 응답 생성
			if (resultJobExecution.getExitStatus().equals(ExitStatus.COMPLETED)) {
				
				result.setExitCode(BatchConstants.BAT_STATUS_COMPLETED);
				
				result.setJobExecutionId(resultJobExecution.getId());				
				result.setInstanceId(resultJobExecution.getJobInstance().getInstanceId());
			} else {
				setResultOnFail(result, resultJobExecution, null);
			}
			
		} catch (JobExecutionAlreadyRunningException e) {			// unchecked & 업무 오류 기준?: 인프라 조직에서 care 해야 하나
			logService.log(ErrorCode.E815, e); 
			setResultOnFail(result, resultJobExecution, e);
		} catch (JobRestartException e) {
			logService.log(ErrorCode.E816, e); 
			setResultOnFail(result, resultJobExecution, e);
		} catch (JobInstanceAlreadyCompleteException e) {			// unchecked & 업무 오류 
			logService.log(ErrorCode.E817, e); 
			setResultOnFail(result, resultJobExecution, e);
		} catch (JobParametersInvalidException e) {
			logService.log(ErrorCode.E818, e); 
			setResultOnFail(result, resultJobExecution, e);
		} catch (Throwable t) {
			logService.log(ErrorCode.E801, new RuntimeException(t));
			setResultOnFail(result, resultJobExecution, t);
		} finally {
//			reporting 
			report(resultJobExecution, result);
			return result;
		}
	}
	
	private void setResultOnFail(BatchExeResultDTO result, JobExecution execution, Throwable cause) {
		
		result.setExitCode(BatchConstants.BAT_STATUS_FAILED);
//		job load 중 오류가 발생하면  execution 이 null 
		if ( execution == null ) {
			result.setExitDescription(ExceptionUtils.getStackTrace(cause));
		} else {
			result.setJobExecutionId(execution.getId());				
			result.setInstanceId(execution.getJobInstance().getInstanceId());
			
			List<Throwable> throwables = execution.getAllFailureExceptions();
			Throwable t = throwables.get(0);
			result.setExitDescription(ExceptionUtils.getStackTrace(t));
		}
		BatchJobContext ctx = (BatchJobContext)execution.getExecutionContext().get(BatchConstants.KEY_BAT_CTX);
		
		BatchExecutionDTO lastExecution = BatchExecutionManager.getService().selectExecution(ctx.getJobUuid());
		String ndtm = LocalDateTime.now().format(DateTimeFormatter.ofPattern(BatchConstants.FMT_YMD_MLS));		
		lastExecution.setEndDtm(ndtm);
		String errMsg = result.getExitDescription();
		if (StringUtils.length(errMsg) > 2000) {
			errMsg = StringUtils.substring(errMsg, 0, 2000);
		}
		lastExecution.setExitMessage(errMsg);
		lastExecution.setProcStatus(BatchConstants.PROC_STATUS_FAILED);
		
		BatchExecutionManager.getService().updateExecution(lastExecution);
	}
	
	private void report(JobExecution resultJobExecution, BatchExeResultDTO result) {
		
		ExecutionContext executionContext = null;
		BatchJobContext jobContext = null;
		if (resultJobExecution != null) {
			executionContext = resultJobExecution.getExecutionContext();
			jobContext = (BatchJobContext)executionContext.get(BatchConstants.KEY_BAT_CTX);
		}
		
		if (log.isInfoEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append("\n=============== 배치 실행 결과 ===============\n");
			sb.append("%-15s:[%d]\n");
			sb.append("%-15s:[%s]\n");
			sb.append("%-15s:[%s]\n");
			sb.append("%-15s:[%d]\n");
			sb.append("%-15s:[%d]\n");
			sb.append("%-15s:[%d]\n");
			sb.append("%-15s:[%d]\n");				
			sb.append("%-15s:\n[%s]\n");

			log.info(String.format(sb.toString(), 
					"Execution ID", ObjectUtils.defaultIfNull(result.getJobExecutionId(),0), 
					"jobCode", StringUtils.defaultIfBlank(result.getJobCode(),""),
					"Exit Code",StringUtils.defaultIfBlank(result.getExitCode(),""),
					"Total Count", (jobContext!=null ? jobContext.getTotalCount():0),
					"CountPerChunk", (jobContext!=null ? jobContext.getCommitCount():0),
					"Completed",(jobContext!=null ? jobContext.getCompleted():0),
					"Read Count",(jobContext!=null ? jobContext.getCurrentIndex():0), 
					"Error Message",StringUtils.defaultIfBlank(result.getExitDescription(),"")));
		}
	}
	/**
	 * stop signal 을 해당 execution 에 성공적으로 보내면 true 를 반환
	 * true 가 반환됐다고 해당 execution 이 종료된 것은 아니다. polling 을 사용해서 상태를 확인해야 보장이 가능하다.
	 *    
	 * @param param
	 * @return 
	 */
	public boolean stop(BatchExeParamDTO param) {
		
		try {
			return jobOperator.stop(param.getExecutionId());
		} catch (NoSuchJobExecutionException e) {
			throw new RuntimeException(new HmfbException(ErrorCode.E813, 
					String.format("배치실행 정보를 확인하세요. Execution ID [%ld]", param.getExecutionId())));
		} catch (JobExecutionNotRunningException e) {
			throw new RuntimeException(new HmfbException(ErrorCode.E814, 
					String.format("배치실행 정보를 확인하세요. Execution ID [%ld]", param.getExecutionId())));
		}
	}

}
