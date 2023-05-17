package hmfb.web.menu.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import hmfb.core.dto.MenuAuthDto;
import hmfb.core.dto.MenuDto;
import hmfb.core.exception.HmfbException;
import hmfb.core.idgnr.service.IdGnrService;
import hmfb.core.utils.DateUtil;

/**

 * @FileName : MenuServiceImpl.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : Menu ServiceImpl 클레스 

 * @변경이력 :

 */

@Service
@Transactional
public class MenuServiceImpl {
	
	@Autowired
    @Qualifier("defaulSqlSessionTemplate")
    private SqlSession sqlSession;
	
	@Autowired
    private IdGnrService menuSeqNoGnrService;
	
	/**
    
     * @Method Name : getMenuList
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 메뉴 내역 조회
   
     * @변경이력 : 
   
     */
	public List<MenuDto> getMenuList(MenuDto menuDto) throws HmfbException {
		return sqlSession.selectList("hmfb.web.menu.MenuMapper.getMenuList", menuDto);
	}
	
	/**
    
     * @Method Name : getMenuAuthList
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 메뉴 권한 내역 조회
   
     * @변경이력 : 
   
     */
	public List<MenuDto> getMenuAuthList(MenuDto menuDto) throws HmfbException {
		return sqlSession.selectList("hmfb.web.menu.MenuMapper.getMenuAuthList", menuDto);
	}
	
	/**
    
	 * @Method Name : delMenus
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 메뉴 삭제
   
     * @변경이력 : 
   
     */
    public int delMenus(List<MenuDto> menuDtoList) throws HmfbException  {
    	return sqlSession.delete("hmfb.web.menu.MenuMapper.delMenus", menuDtoList);
    }
    
    /**
    
	 * @Method Name : delAuthMenus
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 메뉴별권한 삭제
   
     * @변경이력 : 
   
     */
    public int delMenuAuths(List<MenuDto> menuDtoList) throws HmfbException  {
    	return sqlSession.delete("hmfb.web.menu.MenuMapper.delMenuAuths", menuDtoList);
    }
    
    /**
    
	 * @Method Name : saveUser
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 메뉴 저장
   
     * @변경이력 : 
   
     */
    public int saveMenu(MenuDto	menuDto) throws HmfbException  {
    	String dttm = DateUtil.getCurrentDttm();
    	if(ObjectUtils.isEmpty(menuDto.getSeqNo())) {
    		menuDto.setSeqNo(menuSeqNoGnrService.getNextStringId());
        	menuDto.setRegDt(dttm.substring(0, 8));
        	menuDto.setRegTm(dttm.substring(8));
        	return sqlSession.insert("hmfb.web.menu.MenuMapper.insertMenu", menuDto);
    	} else {
        	menuDto.setUpdDt(dttm.substring(0, 8));
        	menuDto.setUpdTm(dttm.substring(8));
        	return sqlSession.update("hmfb.web.menu.MenuMapper.updateMenu", menuDto);
    	}
    }
	
    /**
    
	 * @Method Name : saveMenuAuths
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 메뉴 권한 저장
   
     * @변경이력 : 
   
     */
    public void saveMenuAuths(String authDvcd, List<?> menuDtoList) throws HmfbException  {
    	sqlSession.insert("hmfb.web.menu.MenuMapper.delMenuAuth", authDvcd);
    	String dttm = DateUtil.getCurrentDttm();
    	menuDtoList.stream()
    		.filter(Map.class::isInstance)
    		.map(Map.class::cast)
    		.forEach(map -> {
    		sqlSession.insert("hmfb.web.menu.MenuMapper.insertMenuAuth", MenuAuthDto.builder()
    				.menuSeqNo(map.get("menuSeqNo").toString())
    				.authDvcd(map.get("authDvcd").toString())
    				.regDt(dttm.substring(0, 8))
    				.regTm(dttm.substring(8))
    				.build());
    	});
    }
    
}
