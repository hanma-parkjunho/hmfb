package hmfb.web;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;
/**
 * 
 * @author KDK
 *
 */
@Log4j2
public class AdminServerTests {

	@Test
	void startUp() throws InterruptedException {
		/*
		 * springboot org.apache.catalina.connector.ClientAbortException: java.nio.channels.ClosedChannelException
		 * embeded Tomcat 에서 ClientAbortException 발생 : ReadTimeout 설정 조치. 
		 */
		RestTemplate restTemplate = new RestTemplateBuilder()
										.setConnectTimeout(Duration.ofSeconds(10))
										.setReadTimeout(Duration.ofSeconds(60))
										.build();
//		hmfb-api 서버 기동  
//		restTemplate.postForObject("http://localhost:8080/admin/startServer", "API", String.class);
		restTemplate.getForObject("http://localhost:8080/admin/startServer/API", String.class);
		
		if (log.isDebugEnabled()) log.debug("시작 요청함. 비동기이므로 결과는 서버 로그를 확인해야 함");
	}
	
	@Test
	void shutdown() throws InterruptedException {
		/*
		 * springboot org.apache.catalina.connector.ClientAbortException: java.nio.channels.ClosedChannelException
		 * embeded Tomcat 에서 ClientAbortException 발생 : ReadTimeout 설정 조치. 
		 */
		RestTemplate restTemplate = new RestTemplateBuilder()
										.setConnectTimeout(Duration.ofSeconds(10))
										.setReadTimeout(Duration.ofSeconds(60))
										.build();
//		hmfb-api 서버 종료 
		restTemplate.postForObject("http://localhost:8080/admin/stopServer", "API", String.class);
		
		if (log.isDebugEnabled()) log.debug("종료 요청함. 비동기이므로 결과는 서버 로그를 확인해야 함");
	}
}
