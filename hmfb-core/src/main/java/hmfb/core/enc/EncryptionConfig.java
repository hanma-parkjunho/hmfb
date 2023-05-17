package hmfb.core.enc;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import hmfb.core.enc.asc.AES256;

/**

 * @FileName : EncryptionConfig.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : EncryptionConfig 클레스

 * @변경이력 :

 */

@Configuration
public class EncryptionConfig {
	
	//암호화 키
	private final String encKey = "d0kag3hgh4kzl8r2kqtb012345678901";
	                               
	/**
	
	  * @Method Name : stringEncryptor
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 프로퍼티 암호화 Bean
	
	  * @변경이력 : 
	
	  */
	@Bean
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(encKey);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setPoolSize("1");
        encryptor.setConfig(config);
        return encryptor;
    }
	
	/**
	
	  * @Method Name : aesEncryptor
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : AES256 암호화 Bean
	
	  * @변경이력 : 
	
	  */
	@Bean
	public AES256 aesEncryptor() {
		return new AES256(encKey);
	}
	
}
