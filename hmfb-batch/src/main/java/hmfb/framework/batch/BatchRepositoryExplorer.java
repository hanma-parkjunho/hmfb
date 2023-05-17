package hmfb.framework.batch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hmfb.core.dto.BatchExeParamDTO;
import hmfb.core.dto.BatchExeResultDTO;
import hmfb.core.exception.ErrorCode;
import hmfb.core.exception.HmfbException;
import lombok.extern.log4j.Log4j2;
/**
 * 
 * BATCH_STEP_EXECUTION 테이블
 * - 테스트 시나리오 : 파일에 6건의 데이터가 존재. 4번째 또는 5번째 행에서 오류 발생하도록 설정. 
 * 
 * 커밋횟수 읽기성공   쓰기성공                                                 롤백횟수.
 * --------------------------------------------
 * commit | read | filter | write | read_skip | write_skip | process_skip | rollback
 *    "1"	     "3"	  "0"	   "2"	        "0"         	"0"          	"0"             	"1"   <= 커밋횟수1, 커밋건수2, 롤백횟수1, 4행에서 오류
 *    "2"	     "4"	  "0"	   "4"	        "0"         	"0"          	"0"             	"1"   <= 커밋횟수2, 커밋건수2, 롤백횟수1, 5행에서 오류   
 * --------------------------------------------
 * Read Skippable
 * --------------------------------------------
 *    "3"	     "5"	  "0"	   "5"	        "1"         	"0"          	"0"             	"0"   <= 커밋횟수3, 커밋건수?, 롤백횟수0, 4행에서 오류
 *    "3"	     "5"	  "0"	   "5"	        "1"         	"0"          	"0"             	"0"   <= 커밋횟수3, 커밋건수?, 롤백횟수0, 5행에서 오류   
 *
 * 총건수    : 없음
 * 완료건수 : read_count - max(read_skip, write_skip, process_skip)-rollback
 *    	
 * @author KDK
 *
 */
@Log4j2
@Component
public class BatchRepositoryExplorer {
	
	/** JobExplorer is a read-only version of the JobRepository */
	@Autowired
	JobExplorer jobExplorer;
	
	@Autowired
	JobOperator jobOperator;
	
	private long getLastJobExecutionId(String jobName) {
		
		JobInstance instance = jobExplorer.getLastJobInstance(jobName);	
		JobExecution jobExecution = jobExplorer.getLastJobExecution(instance);		
		
		return jobExecution.getId();
	}
	
	public BatchExeResultDTO summary(BatchExeParamDTO param) {
		
		try {
			String summary = jobOperator.getSummary(getLastJobExecutionId(param.getJobCode()));
			if (log.isDebugEnabled()) {
				log.debug(summary);
			}
			return null;
		} catch (NoSuchJobExecutionException e) {
			throw new RuntimeException(new HmfbException(ErrorCode.E814, 
					String.format("배치실행 정보를 확인하세요. 배치식별자[%s],Execution ID [%ld]", param.getJobCode(), param.getExecutionId())));
		}
	}
}
