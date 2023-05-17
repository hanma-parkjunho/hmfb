package hmfb.sample;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**

 * @FileName : SampleMapper.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : Sample Mapper 클레스 

 * @변경이력 :

 */

@Repository
@Mapper
public interface  SampleMapper {
	
	/**
    
     * @Method Name : getColumn1
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : Sample Mapper 
   
     * @변경이력 : 
   
     */
	public String getColumn1(int rownum);
}


