package hmfb.core.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**

 * @FileName : DbProperties.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : DB Properties 클레스

 * @변경이력 :

 */

@Data
@ConfigurationProperties(prefix = "hmfb.db")
@Component
public class DbProperties {
	
	private String dbType;
	private String driverClassName;
	private String url;
	private String username;
	private String password;
	
}
