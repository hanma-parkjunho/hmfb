package hmfb.core.cmmn.service;

import java.util.List;
import javax.transaction.Transactional;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import hmfb.core.dto.CmmnDto;
import hmfb.core.dto.UserDto;
import hmfb.core.exception.HmfbException;
import hmfb.core.utils.DateUtil;

/**

 * @FileName : CmmnServiceImpl.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : CmmnServiceImpl 클래스

 * @변경이력 :

 */

@Service
@Transactional
public class CmmnServiceImpl {
	
	@Autowired
    @Qualifier("defaulSqlSessionTemplate")
    private SqlSession sqlSession;
	
    /**
    
     * @Method Name : getCmmnList
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 공통코드 조회
   
     * @변경이력 : 
   
     */
    public List<CmmnDto> getCmmn(CmmnDto cmmnDto) throws HmfbException {
    	return sqlSession.selectList("hmfb.core.cmmn.CmmnMapper.getCmmn", cmmnDto);
    }
    
    /**
    
     * @Method Name : getCmmnList
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 공통코드 조회
   
     * @변경이력 : 
   
     */
    public CmmnDto getCmmn(String codeGrp, String code) throws HmfbException {
    	return sqlSession.selectOne("hmfb.core.cmmn.CmmnMapper.getCmmn", CmmnDto.builder().codeGrp(codeGrp).code(code).build());
    }
   
    /**
    
     * @Method Name : getUserList
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 공통코드정보 목록 내역조회
   
     * @변경이력 : 
   
     */
    public List<UserDto> getCmmnList(CmmnDto cmmnDto) throws HmfbException {
    	return sqlSession.selectList("hmfb.core.cmmn.CmmnMapper.getCmmnList", cmmnDto);
    }
    
    /**
    
     * @Method Name : getUserTotCnt
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 공통코드정보 총 건수 조회
   
     * @변경이력 : 
   
     */
    public int getCmmnTotCnt(CmmnDto cmmnDto) throws HmfbException {
    	return (Integer)sqlSession.selectOne("hmfb.core.cmmn.CmmnMapper.getCmmnTotCnt", cmmnDto);
    }
    
    /**
    
	 * @Method Name : saveCmmn
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 사용자 저장
   
     * @변경이력 : 
   
     */
    public int saveCmmn(CmmnDto cmmnDto) throws HmfbException  {
    	String dttm = DateUtil.getCurrentDttm();
    	if(ObjectUtils.isEmpty(this.getCmmn(cmmnDto.getCodeGrp(), cmmnDto.getCode()))) {
    		cmmnDto.setRegDt(dttm.substring(0, 8));
        	cmmnDto.setRegTm(dttm.substring(8));
    		return sqlSession.insert("hmfb.core.cmmn.CmmnMapper.insertCmmn", cmmnDto);
    	} else {
    		cmmnDto.setUpdDt(dttm.substring(0, 8));
        	cmmnDto.setUpdTm(dttm.substring(8));
    		return sqlSession.update("hmfb.core.cmmn.CmmnMapper.updateCmmn", cmmnDto);
    	}	
    }
    
    /**
    
	 * @Method Name : delUsers
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 사용자 삭제
   
     * @변경이력 : 
   
     */
    public void delCmmns(List<CmmnDto> cmmnDtoList) throws HmfbException  {
    	cmmnDtoList.stream().forEach(cmmnDto -> {
    		sqlSession.delete("hmfb.core.cmmn.CmmnMapper.delCmmns", cmmnDto);
    	}); 
    }
}
