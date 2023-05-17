package hmfb.web.batch.manage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import hmfb.core.dto.BatchJobDTO;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@ConditionalOnExpression("${hmfb.admin.enable-schedule:true}")
@EnableScheduling
public class BatchSchedulingConfig implements SchedulingConfigurer {

	@Autowired
	BatchAdminDAO batchAdminDao;
	
	@Bean
	public TaskScheduler batchScheduler() {
		
//		int corePoolSize = Runtime.getRuntime().availableProcessors();
//		int corePoolSize = 10; // 유량제어 
//		TODO : RejectHandler 
		ConcurrentTaskScheduler scheduler =  new ConcurrentTaskScheduler();
//		scheduler
		return scheduler;
	}
	
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		
		taskRegistrar.setScheduler(batchScheduler());
		
		List<CronTask> cronTaskList = new ArrayList<CronTask>();
//		배치 작업 목록을 조회해서 스케줄 등록 
		scheduleJobList(cronTaskList);

		taskRegistrar.setCronTasksList(cronTaskList);
	}
	
	private void scheduleJobList(List<CronTask> cronTaskList) {

//		수시 외 배치작업 조회 : "A"(수시) 
		List<BatchJobDTO> batchList = batchAdminDao.selectList("batchadmin.getListBatchJob", null);
		for (BatchJobDTO batchJob : batchList ) {
			
			String jobCode = batchJob.getJobCode();
			String jobDesc = batchJob.getJobDesc();
			String useYn = batchJob.getUseYn();
			String runParam = batchJob.getRunParam();
			String cronExpression = batchJob.getCronExpression();
//			사용여부가 Y 가 아니면 skip.
			if (!"Y".contentEquals(useYn)) {
				if (log.isInfoEnabled()) {
					log.info(String.format("\"%s(%s)은 사용여부가 사용안함으로 설정되어 있습니다.", jobDesc, jobCode)); 
				}
				continue;
			}
			
//			실행주기가 없으면 skip.
			if (!StringUtils.hasText(cronExpression)) {
				if (log.isInfoEnabled()) {
					log.info(String.format("스케줄링할 배치는 cron 표현식이 필수입니다. %s(%s)", jobCode, jobDesc)); 
				}
				continue;
			}

			if (log.isInfoEnabled()) {
				log.info(String.format("%s(%s)이 스케줄에 등록되었습니다.", jobDesc, jobCode));
			}
			cronTaskList.add(new CronTask(new JobExecutor(jobCode, runParam), cronExpression)); 
		}
	}
	
	/**
	 * 기능 테스트용(삭제 예정)
	 */
	private void testSchedule(List<CronTask> cronTaskList) {
		/*
		cronTaskList.add(new CronTask(new Runnable() {			
			@Override
			public void run() {
				if (log.isDebugEnabled()) {
					log.debug("뭐지:"+new Date());
				}
			}
		}, "* * * * * *")); // 초마다 실행
		*/ 
	}
	
	class JobExecutor implements Runnable {
		
		private String jobCode;
		private String runParam;
		
		JobExecutor(String jobCode, String runParam) {
			this.jobCode = jobCode;
			this.runParam = runParam;
		}
		
		public void run() {
			
			RestTemplate restTemplate = new RestTemplate();
			
			Map<String,String> formData = new HashMap<String,String> ();
			formData.put("jobYmd",LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
//			업무구분(3)+실행주기(1)
			formData.put("jobCode", jobCode);
			formData.put("returnYn","Y");
			formData.put("runParam",runParam);
			ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8060/batch/execute", formData, String.class); 
			
			if (log.isDebugEnabled()) {
				log.debug("응답코드:"+response.getStatusCode());
				log.debug("응답내용:"+response.getBody().toString());
			}
		}
	}
}
