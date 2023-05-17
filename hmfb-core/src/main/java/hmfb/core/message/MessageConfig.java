package hmfb.core.message;

import java.io.IOException;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**

 * @FileName : DataSourceConfig.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : MessageSource Config 클레스

 * @변경이력 :

 */

@Configuration
public class MessageConfig {
 
	/**
	
	  * @Method Name : messageSource
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : MessageSource 빈 생성
	
	  * @변경이력 : 
	
	  */
    @Bean
    public MessageSource messageSource() throws IOException {
    	ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:messages/message", 
        		                   "classpath:hmfb/core/idgnr/messages/idgnr",
        		                   "classpath:hmfb/core/exception/message/exception");
        messageSource.setDefaultEncoding("UTF-8");
        // locale에 해당하는 file이 없을 경우 system locale을 사용
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }
    
    /**
	
	  * @Method Name : messageSourceAccessor
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : MessageSourceAccessor 빈 생성
	
	  * @변경이력 : 
	
	  */
    @Bean
    public MessageSourceAccessor messageSourceAccessor() throws IOException {
        return new MessageSourceAccessor(messageSource());
    }
}
