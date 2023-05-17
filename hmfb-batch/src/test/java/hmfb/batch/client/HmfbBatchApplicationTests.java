package hmfb.batch.client;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import hmfb.core.dto.BatchExeParamDTO;
import hmfb.core.dto.BatchExeResultDTO;
import hmfb.framework.batch.constant.BatchConstants;
import lombok.extern.log4j.Log4j2;

//@SpringBootTest(classes = {HmfbBatchApplication.class}, 
//				webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT	// 없을 경우 TestRestTemplate 을 autowired 시 오류 발생. 
//)
@Log4j2
class HmfbBatchApplicationTests {

	/*
	 * C:\develop\tools\curl78\bin\curl -X POST -H "Content-type: application/json"  -d "{\"accountNo\":\"1234567890\"}" http://localhost:8080/dep/BKDEP10001	 
	 */
//	@Autowired 
//	private TestRestTemplate restTemplate;
	
	@Test
	void executeF2DBatch() throws InterruptedException {
		
		RestTemplate restTemplate = new RestTemplate();
		
		BatchExeParamDTO formData = new BatchExeParamDTO ();
		formData.setJobYmd(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
//		업무구분(3)+실행주기(1)
		formData.setJobCode("DEPD10001");
		formData.setReturnYn("Y");
		formData.setRunParam("param1 param2 param3");
		ResponseEntity<BatchExeResultDTO> response = restTemplate.postForEntity("http://localhost:8060/batch/execute", formData, BatchExeResultDTO.class); 
		
		assertThat(response.getBody()).isNotNull();
		if (log.isDebugEnabled()) {
			log.debug("응답내용은:"+response.getBody().toString());
		}
		
		if (StringUtils.equals(response.getBody().getExitCode(), BatchConstants.BAT_STATUS_COMPLETED)) {
			// 성공 
		} else {
			// 실패 
		}
	}
	
	@Test
	void executeD2DBatch() throws InterruptedException {
		
		RestTemplate restTemplate = new RestTemplate();
		
		BatchExeParamDTO formData = new BatchExeParamDTO ();
		formData.setJobYmd(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
//		업무구분(3)+실행주기(1)
		formData.setJobCode("DEPD10003");
		formData.setReturnYn("Y");
		formData.setRunParam("param1 param2 param3");
		ResponseEntity<BatchExeResultDTO> response = restTemplate.postForEntity("http://localhost:8060/batch/execute", formData, BatchExeResultDTO.class); 
		
		assertThat(response.getBody()).isNotNull();
		if (log.isDebugEnabled()) {
			log.debug("응답내용은:"+response.getBody().toString());
		}
		
		if (StringUtils.equals(response.getBody().getExitCode(), BatchConstants.BAT_STATUS_COMPLETED)) {
			// 성공 
		} else {
			// 실패 
		}
	}
	
	@Test
	void executeCTMBatch() throws InterruptedException {
		
		RestTemplate restTemplate = new RestTemplate();
		
		BatchExeParamDTO formData = new BatchExeParamDTO ();
		formData.setJobYmd(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
//		업무구분(3)+실행주기(1)
		formData.setJobCode("DEPD10006");
		formData.setReturnYn("Y");
		formData.setRunParam("param1 param2 param3");
		ResponseEntity<BatchExeResultDTO> response = restTemplate.postForEntity("/batch/execute", formData, BatchExeResultDTO.class); 
		
		assertThat(response.getBody()).isNotNull();
		if (log.isDebugEnabled()) {
			log.debug("응답내용은:"+response.getBody().toString());
		}
		
		if (StringUtils.equals(response.getBody().getExitCode(), BatchConstants.BAT_STATUS_COMPLETED)) {
			// 성공 
		} else {
			// 실패 
		}
	}
	
	@Test
	void testCallFirmbankingService() throws InterruptedException {
		
		RestTemplate restTemplate = new RestTemplate();
		
		BatchExeParamDTO formData = new BatchExeParamDTO ();
		formData.setJobYmd(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
//		업무구분(3)+실행주기(1)
		formData.setJobCode("F0500200");
		formData.setReturnYn("Y");
		formData.setRunParam("");
		ResponseEntity<BatchExeResultDTO> response = restTemplate.postForEntity("http://localhost:8060/batch/execute", formData, BatchExeResultDTO.class); 
		
		assertThat(response.getBody()).isNotNull();
		if (log.isDebugEnabled()) {
			log.debug("응답내용은:"+response.getBody().toString());
		}
		
		if (StringUtils.equals(response.getBody().getExitCode(), BatchConstants.BAT_STATUS_COMPLETED)) {
			// 성공 
			if (log.isDebugEnabled()) log.debug("성공");
		} else {
			// 실패 
			if (log.isDebugEnabled()) log.debug("성공");
		}
	}
	
	@Test
	void summaryBatch() throws InterruptedException {
		
		RestTemplate restTemplate = new RestTemplate();
		
		BatchExeParamDTO formData = new BatchExeParamDTO ();
		formData.setJobYmd(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
//		업무구분(3)+실행주기(1)
		formData.setJobCode("DEPD10001");
		ResponseEntity<BatchExeResultDTO> response = restTemplate.postForEntity("/batch/summary", formData, BatchExeResultDTO.class); 
		
		assertThat(response.getBody()).isNotNull();
		if (log.isDebugEnabled()) {
			log.debug("응답내용은:"+response.getBody().toString());
		}
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK); 
	}
	
}
