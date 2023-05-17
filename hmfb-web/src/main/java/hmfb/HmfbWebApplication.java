package hmfb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


/**

  * @FileName : HmfbWebApplication.java

  * @작성자 : 송원호

  * @작성일 : 2022. 12. 28 

  * @프로그램 설명 : 한마펌뱅킹 WEB Application 클레스

  * @변경이력 :

  */

@EnableJpaRepositories
@SpringBootApplication
public class HmfbWebApplication {
    
	/**
	
	  * @Method Name : main
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 한마펌뱅킹 WEB Application main
	
	  * @변경이력 : 
	
	  */
	public static void main(String[] args) {
        SpringApplication.run(HmfbWebApplication.class, args);
    }
}
