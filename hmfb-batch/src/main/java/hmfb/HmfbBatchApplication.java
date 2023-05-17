package hmfb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**

 * @FileName : HmfbBatchApplication.java

 * @작성자 : 송원호 

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : 한마펌뱅킹 BATCH Application 클레스

 * @변경이력 :
 * 		김덕기 2023.01.02	개정

 */

@EnableJpaRepositories
@SpringBootApplication
public class HmfbBatchApplication {
    
	/**
	
	  * @Method Name : main
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 한마펌뱅킹 BATCH Application main
	
	  * @변경이력 : 
	
	  */
	public static void main(String[] args) {
        
		SpringApplication.run(HmfbBatchApplication.class, args);
    }
}
