package hmfb;

import org.springframework.boot.SpringApplication;

/**

 * @FileName : HmfbTcpApplication.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : 한마펌뱅킹 TCP Application 클레스

 * @변경이력 :

 */

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class HmfbTcpApplication {
    
	/**
	
	  * @Method Name : main
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 한마펌뱅킹 TCP Application main
	
	  * @변경이력 : 
	
	  */
	public static void main(String[] args) {
        SpringApplication.run(HmfbTcpApplication.class, args);
    }
}
