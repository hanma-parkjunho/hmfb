package hmfb.core.management;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.mysql.cj.util.StringUtils;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ServerNodeService {

	@Value("${project.sys}")
    private String systemId;
	
	public void shutdown() {

		if (StringUtils.nullSafeEqual(systemId, "TCP")) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					stopNettyServer("firmServer");
					stopNettyServer("virtualActServer");
				}
			});
			t.setDaemon(false);
			t.start();
		}
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				exitApplication();
			}
		});
		t2.setDaemon(false);
		t2.start();
		
	}

	public void stopNettyServer(String serverBeanName) {

		try {
			Object nettySvrObj = ApplicationContextProvider.getApplicationContext().getBean(serverBeanName);
			if (nettySvrObj == null) {
				return;
			}
			
			Method m = nettySvrObj.getClass().getMethod("shutdown", null);
			m.invoke(nettySvrObj, null);
			
		} catch (Exception e) {
			log.error("failed to shutdown netty server...", e);
		}
	}

	public void exitApplication() {

		ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();

		SpringApplication.exit(ctx);
	}
}
