package hmfb.api.client;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import hmfb.core.dto.F0500200Dto;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class HmfbAipApplicationTest {

	@Test
	void testApiService() throws InterruptedException {
		
		RestTemplate restTemplate = new RestTemplate();
		
		F0500200Dto formData = new F0500200Dto ();
		formData.setReceptInfo("9999991234567");
		formData.setBankCode("088");
		formData.setAcnutNo("12345687");
		ResponseEntity<F0500200Dto> response = restTemplate.postForEntity("http://localhost:8050/hmfbApi/api/0500200", formData, F0500200Dto.class); 
		
		assertThat(response.getBody()).isNotNull();
		if (log.isDebugEnabled()) {
			log.debug("응답내용은:"+response.getBody().toString());
		}
	}

}
