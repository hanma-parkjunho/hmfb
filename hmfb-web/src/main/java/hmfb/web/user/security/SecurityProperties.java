package hmfb.web.user.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

/**

 * @FileName : SecurityProperties.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : SecurityProperties 클래스

 * @변경이력 :

 */

@Data
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

	private String usernameParameter;
	private String passwordParameter;
	private String loginPage;
	private String loginProcessingUrl;
	private String defaultSuccessUrl;
	private String logoutUrl;
	private String [] antMatchers;
	
}
