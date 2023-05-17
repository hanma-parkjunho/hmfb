package hmfb.core.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("node")
public class ServerNodeController {
		
	@Autowired
	ServerNodeService service;
	
	@RequestMapping("shutdown")
	public String shutdown() {
		log.info("shutdown 쓰레드:"+Thread.currentThread());
		service.shutdown();
		
		return "0";
	}
	
	@RequestMapping("healthcheck")
	public String doHealthCheck() {		
		return "0";
	}
}
