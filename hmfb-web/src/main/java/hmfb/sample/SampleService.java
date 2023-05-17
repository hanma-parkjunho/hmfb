package hmfb.sample;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import hmfb.core.exception.ErrorCode;
import hmfb.core.exception.HmfbException;
import hmfb.core.log.service.LogService;
import lombok.extern.log4j.Log4j2;

/**

 * @FileName : SampleService.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : Sample Service 예제 클레스 

 * @변경이력 :

 */

@Log4j2
@Service
public class SampleService {
	
	@Autowired
	LogService logService;
	
	@Autowired
	SampleDAO sampleDAO;
	
	@Autowired
	SampleMapper sampleMapper;
	
	@Autowired
    private MessageSource messageSource;
	
	private final PlatformTransactionManager transactionManager;
	
	/**
    
     * @Method Name : SampleService
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : SampleService 생성자
   
     * @변경이력 : 
   
     */
	public SampleService(PlatformTransactionManager transactionManager) {
	    this.transactionManager = transactionManager;
	}
	
	/**
    
     * @Method Name : getTxStatus
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : Transaction 초기화
   
     * @변경이력 : 
   
     */
	private TransactionStatus getTxStatus() {
		return transactionManager.getTransaction(new DefaultTransactionDefinition());
	}
	
	/**
    
     * @Method Name : getCurrentDataTimeByDao
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : DAO 예제
   
     * @변경이력 : 
   
     */
	public void getCurrentDataTimeByDao() {
		log.info(sampleDAO.getCurrentDataTime());
	}
	
	/**
    
     * @Method Name : getColunm1ByMapper
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : Mapper 예제
   
     * @변경이력 : 
   
     */
	public void getColunm1ByMapper() {
		log.info(sampleMapper.getColumn1(1));
	}
	
	/**
    
     * @Method Name : error
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : Service 오류 처리 예제
   
     * @변경이력 : 
   
     */
	public void error() throws HmfbException {
		if(true) {
			throw new HmfbException("error 발생!!");
		}
	}
	
	/**
    
     * @Method Name : error
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : Service 오류 처리 예제
   
     * @변경이력 : 
   
     */
	public void runtimeError(String str) {
		if(true) {
			throw new RuntimeException(new HmfbException("SampleService runtimeError 오류 발생!!"));
		}
	}
	
	/**
    
     * @Method Name : rollbackNo
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : No rollbac 예제
   
     * @변경이력 : 
   
     */
	public void rollbackNo() throws HmfbException {
		
		sampleDAO.insertDate("rollbackNo");
		
		sampleDAO.insertDate("rollbackNo");
		
		if(sampleDAO.insertDate("rollbackNo") > 0) {
			throw new HmfbException("No rollback 오류 발생!!");
		}
		
	}
	
	/**
    
     * @Method Name : rollbackYes
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : Transactional rollback 예제
   
     * @변경이력 : 
   
     */
	@Transactional(rollbackFor = HmfbException.class)
	public void rollbackYes() throws HmfbException {
		
		Map<String,Object> parmasMap = new HashMap<String, Object>();
		parmasMap.put("idx", "rollbackYes");
		
		sampleDAO.insertDate(parmasMap);
		
		sampleDAO.insertDate(parmasMap);
		
		if(sampleDAO.insertDate(parmasMap) > 0) {
			throw new HmfbException("SampleService rollbackYes 오류 발생!!");
		}
		
	}
	
	/**
    
     * @Method Name : modifySqlseesion
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : TransactionStatus rollback, commit 예제
   
     * @변경이력 : 
   
     */
	public void modifySqlseesion() {
		
		TransactionStatus txStatus = getTxStatus();
		
		Map<String,Object> parmasMap = new HashMap<String, Object>();
		parmasMap.put("idx", "modifySqlseesion rollback");
		
		try {
		
			if(sampleDAO.insertDate(parmasMap) > 0)
				throw new HmfbException("SampleService modifySqlseesion 오류 발생!! ");
			
		} catch (HmfbException ex) {
			//오류 발생 시 Service에서 throws 하지 않아 AfterThrowing에서 오류 로그 처리되지 않음 오류 로그 처리 필요
			transactionManager.rollback(txStatus);
			logService.log(ErrorCode.E500, ex); 
			log.error(ex.getMessage(), ex.getCause());
		}
		
		txStatus = getTxStatus();
		parmasMap.put("idx", "modifySqlseesion commit");
		sampleDAO.insertDate(parmasMap);
		transactionManager.commit(txStatus);

	}
}
