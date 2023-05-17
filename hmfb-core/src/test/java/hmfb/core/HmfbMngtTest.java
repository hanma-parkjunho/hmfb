package hmfb.core;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class HmfbMngtTest {
	
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
		
		restTemplate.postForObject("http://localhost:8070/node/shutdown", "", String.class);
		
		if (log.isDebugEnabled()) log.debug("종료 요청함. 비동기이므로 결과는 서버 로그를 확인해야 함");
	}
}
