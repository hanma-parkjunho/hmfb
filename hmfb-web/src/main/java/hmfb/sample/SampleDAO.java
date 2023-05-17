package hmfb.sample;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**

 * @FileName : SampleDAO.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : Sample DAO 클레스 

 * @변경이력 :

 */

@Repository
public class SampleDAO {
	
	@Autowired
    @Qualifier("defaulSqlSessionTemplate")
    private SqlSession sqlSession;
	
	/**
    
     * @Method Name : getCurrentDataTime
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : SqlSession selectOne String 예제
   
     * @변경이력 : 
   
     */
    public String getCurrentDataTime() {
        return sqlSession.selectOne("hmfb.sample.SampleMapper.getCurrentDateTime");
    }
    
    /**
    
     * @Method Name : insertDate
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : SqlSession insert 예제
   
     * @변경이력 : 
   
     */
    public int insertDate(Object parameter) {
    	return sqlSession.insert("hmfb.sample.SampleMapper.insertDate", parameter);
    }
 
    /**
    
     * @Method Name : selectObject
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : SqlSession selectOne Object 예제
   
     * @변경이력 : 
   
     */
    public Object selectObject(String mapperId) throws Exception {
        return this.sqlSession.selectOne(mapperId);
    }
    
    /**
    
     * @Method Name : selectObject
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : SqlSession selectOne Object parameter 예제
   
     * @변경이력 : 
   
     */
    public Object selectObject(String mapperId, Object parameter) throws Exception {
        return this.sqlSession.selectOne(mapperId, parameter);
    }
    
}
