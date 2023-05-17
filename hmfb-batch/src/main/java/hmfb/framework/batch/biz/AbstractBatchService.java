package hmfb.framework.batch.biz;

import org.springframework.context.ApplicationContext;

import hmfb.core.management.ApplicationContextProvider;

/**
 * 배치에서 사용할 서비스의 추상 클래스 
 * @author KDK
 *
 */
abstract public class AbstractBatchService {
	
	public static <T> T getService(Class<T> clazz) {
		ApplicationContext context = ApplicationContextProvider.getApplicationContext();
		return context.getBean(clazz);
	}
	public static <T> T getService(String name, Class<T> clazz) {
		ApplicationContext context = ApplicationContextProvider.getApplicationContext();
		return context.getBean(name, clazz);
	}
}
