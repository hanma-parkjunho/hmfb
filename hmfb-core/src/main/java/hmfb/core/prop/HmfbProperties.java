package hmfb.core.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

/**

 * @FileName : HmfbProperties.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : HMFB Properties 클레스

 * @변경이력 :

 */

@Data
@ConfigurationProperties(prefix = "hmfb")
@Component
public class HmfbProperties {
	
	private AdminProperties admin;
	private LogProperties log;
	private DbProperties db;
	
}
