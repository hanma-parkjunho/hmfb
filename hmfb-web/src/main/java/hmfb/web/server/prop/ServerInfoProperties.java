package hmfb.web.server.prop;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**

 * @FileName : ServerInfoProperties.java

 * @작성자 : KDK

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : ServerInfoProperties 클래스

 * @변경이력 :

 */
@Data
@Component
@ConfigurationProperties(prefix = "hmfb-servers")
public class ServerInfoProperties {

	private List<ServerInfo> serverInfoList;	
}
