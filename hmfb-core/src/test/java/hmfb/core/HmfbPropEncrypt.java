package hmfb.core;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import hmfb.core.enc.EncryptionConfig;
import hmfb.core.enc.asc.AES256;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EncryptionConfig.class})
public class HmfbPropEncrypt {
	
	@Autowired
	AES256 aesEncryptor;
	
	@Autowired
	StringEncryptor stringEncryptor;
	
	@Test
    public void test() throws Exception {
        
		String str = "일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십";
		
		log.info("===============================");
		log.info("aesEncryptor start");
		String enc = aesEncryptor.encrypt(str);
		log.info(enc.length());
		log.info(enc);
		log.info(aesEncryptor.decrypt(enc));
		log.info("aesEncryptor end");
		log.info("===============================");
		
		
		log.info("===============================");
		log.info("stringEncryptor start");
		String enc2 = stringEncryptor.encrypt(str);
		log.info(enc2.length());
		log.info(enc2);
		log.info(stringEncryptor.decrypt(enc2));
		log.info("stringEncryptor end");
		log.info("===============================");
		
    }
}
