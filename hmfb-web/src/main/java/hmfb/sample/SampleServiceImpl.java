package hmfb.sample;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hmfb.core.exception.HmfbException;
import lombok.extern.log4j.Log4j2;

/**

 * @FileName : SampleServiceImpl.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : Sample ServiceImpl 클레스 

 * @변경이력 :

 */

@Log4j2
@Service
public class SampleServiceImpl {
	
	@Autowired
	SampleDAO sampleDAO;
	
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
    
     * @Method Name : error
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : ServiceImpl 오류 처리 예제
   
     * @변경이력 : 
   
     */
	
	public void error() throws HmfbException {
		if(true)
			throw new HmfbException("SampleServiceImpl error 오류 발생!!");
	}
	
	/**
    
     * @Method Name : transactionAspect
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : TransactionAspect rollback 예제
   
     * @변경이력 : 
   
     */
	public void transactionAspect() throws HmfbException {
		
		Map<String,Object> parmasMap = new HashMap<String, Object>();
		parmasMap.put("idx", "transactionAspect");
		
		sampleDAO.insertDate(parmasMap);
		
		sampleDAO.insertDate(parmasMap);
		
		if(sampleDAO.insertDate(parmasMap) > 0) {
			throw new HmfbException("SampleServiceImpl transactionAspect 오류 발생!!");
		}
		
	}
	
}
