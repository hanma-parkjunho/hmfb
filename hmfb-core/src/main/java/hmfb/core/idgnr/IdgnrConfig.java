package hmfb.core.idgnr;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hmfb.core.exception.HmfbException;
import hmfb.core.idgnr.service.IdGnrService;
import hmfb.core.idgnr.service.TableIdGnrService;
import hmfb.core.idgnr.service.UUIdGnrService;
import hmfb.core.idgnr.strategy.IdGnrStrategy;
import hmfb.core.idgnr.strategy.IdGnrStrategyImpl;

/**

 * @FileName : IdgnrConfig.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : ID Generation Config 클레스

 * @변경이력 :

 */

@Configuration
public class IdgnrConfig {
	
	@Autowired
	DataSource defaultDataSource;
	
	/**
	 
	 * @Method Name : uuidGnrService
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : UUId 빈 생성
	
	  * @변경이력 : 
	
	  */
	@Bean
	public IdGnrService uuidGnrService() throws NoSuchAlgorithmException, UnknownHostException, HmfbException {
		UUIdGnrService idGnrService = new UUIdGnrService();
		idGnrService.setAddress(InetAddress.getLocalHost().getHostAddress());
		return idGnrService;
	}
	
	/**
	
	  * @Method Name : userSeqNoGnrService
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 사용자 일련번호 빈 생성
	
	  * @변경이력 : 
	
	  */
	@Bean
	public IdGnrService userSeqNoGnrService() {
		TableIdGnrService idGnrService = new TableIdGnrService();
		idGnrService.setDataSource(defaultDataSource);
		idGnrService.setBlockSize(1);
		idGnrService.setTable("IDGNR");
		idGnrService.setTableName("USER_SEQ_NO");
		return idGnrService;
	}
	
	/**
	
	  * @Method Name : userSeqNoGnrService
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 사용자 일련번호 빈 생성
	
	  * @변경이력 : 
	
	  */
	@Bean
	public IdGnrService menuSeqNoGnrService() {
		TableIdGnrService idGnrService = new TableIdGnrService();
		idGnrService.setDataSource(defaultDataSource);
		idGnrService.setStrategy(menuSeqNoStrategy());
		idGnrService.setBlockSize(1);
		idGnrService.setTable("IDGNR");
		idGnrService.setTableName("MENU_SEQ_NO");
		return idGnrService;
	}
	
	/**
	
	  * @Method Name : userSeqNoStrategy
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 메뉴 일련번호 전략 빈 생성
	
	  * @변경이력 : 
	
	  */
	@Bean
	public IdGnrStrategy menuSeqNoStrategy() {
		IdGnrStrategyImpl idGnrStrategyImpl = new IdGnrStrategyImpl();
		idGnrStrategyImpl.setPrefix("");
		idGnrStrategyImpl.setCipers(10);
		idGnrStrategyImpl.setFillChar('0');
		return idGnrStrategyImpl;
	}
}
