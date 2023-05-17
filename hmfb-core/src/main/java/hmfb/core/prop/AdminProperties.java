package hmfb.core.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**

 * @FileName : SecurityProperties.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : SecurityProperties 클래스

 * @변경이력 :

 */

@Data
@ConfigurationProperties(prefix = "hmfb.admin")
@Component
public class AdminProperties {

	private Long seqNo;
    private String userId;
    private String pswd;
    private String flnm;
    private String phnNo;
    private String depart;
    private String authDvcd;
    private String authDvNm;
	
}
