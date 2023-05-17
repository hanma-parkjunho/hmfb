package hmfb.web.server;

import java.io.IOException;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import hmfb.core.exception.ErrorCode;
import hmfb.core.exception.HmfbException;
import hmfb.web.server.service.ServerServiceImpl;
import lombok.extern.log4j.Log4j2;
/**
 * /hmfb-web/src/main/java/hmfb/web/server/AdminServerController.java 를 테스트
 * hmfb-web 에 security 설정 시 로그인 후 요청해야 함.(Request Parse Error)
 * 
 * @author KDK
 *
 */
@Log4j2
@RestController
@RequestMapping("server")
public class ServerRestController {
	
	@Autowired
	private ServerServiceImpl ServerService;
	
	@Value("${hmfb.log.logPath}")
	private String logPath;
		
	@RequestMapping("startServer/{systemId}")
	public String startServer(@PathVariable String systemId) throws HmfbException {
		if (log.isInfoEnabled()) log.info("서버를 기동합니다:"+systemId);
		String installPath = System.getProperty("installPath");
		if (!StringUtils.hasText(installPath)) 
			throw new HmfbException(ErrorCode.E113, "installPath 가 설정되지 않았습니다.");
		try {
			return ServerService.executeStartScriptFile(installPath, systemId, "server_start");
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping("stopServer/{systemId}")
	public String stopServer(@PathVariable String systemId) throws HmfbException {
		if (!StringUtils.hasText(systemId)) {
			throw new HmfbException(ErrorCode.E114, "systemId 가 없습니다.");
		}
		if (systemId.contentEquals("WEB")) {
			throw new HmfbException(ErrorCode.E115, "Web Admin 서버는 관리자 화면에서 종료할 수 없습니다.");
		}
		String ip = ServerService.getServerInfo(systemId).getIp();
		String port = ServerService.getServerInfo(systemId).getPort();
		try {
			RestTemplate restTemplate = new RestTemplateBuilder()
					.setConnectTimeout(Duration.ofSeconds(10))
					.setReadTimeout(Duration.ofSeconds(60))
					.build();
			restTemplate.postForObject(ip+":"+port+"/node/shutdown", "", String.class);
			if (log.isInfoEnabled()) {
				log.info(systemId + " 서버 종료를 요청합니다. 비동기 요청이므로 결과는 서버 로그를 확인하거나 서버 상태를 조회해야 합니다");
				log.info("로그 최상위 경로 :"+ logPath);
			}
			return "0";
		} catch (Exception e) {
			try {
				log.error(e);
				if (log.isInfoEnabled()) log.info("Graceful 종료 실패 후 강제 종료 요청..");
				String installPath = System.getProperty("installPath");
				if (!StringUtils.hasText(installPath)) {
					throw new HmfbException(ErrorCode.E113, "installPath 가 설정되지 않았습니다.");
				}
				return ServerService.executeStopScriptFile(installPath, systemId, "server_stop");
			} catch (InterruptedException e1) {
				throw new RuntimeException(e);
			} catch (IOException e1) {
				throw new RuntimeException(e1);
			}			
		} 
	}
	
	
	
	
}
