
package hmfb.framework.batch.task;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

/**
 * spring batch 4.0.4 부터 제공되는 클래스. 하위 버전 호환을 위해 자체 생성.  
 *
 */
public class DummyItemWriter implements ItemWriter<Object> {

	@Override
	public void write(List<? extends Object> items) throws Exception {
		Thread.sleep(500);
	}


}