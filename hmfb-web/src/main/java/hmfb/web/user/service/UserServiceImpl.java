package hmfb.web.user.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import hmfb.core.dto.MenuDto;
import hmfb.core.dto.UserDto;
import hmfb.core.enc.asc.AES256;
import hmfb.core.exception.ErrorCode;
import hmfb.core.exception.HmfbException;
import hmfb.core.idgnr.service.IdGnrService;
import hmfb.core.utils.DateUtil;
import hmfb.web.user.security.UserDetail;

/**

 * @FileName : UserService.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : UserService 클래스

 * @변경이력 :

 */

@Service
@Transactional
public class UserServiceImpl {
	
	@Autowired
    @Qualifier("defaulSqlSessionTemplate")
    private SqlSession sqlSession;
	
	@Autowired
    private IdGnrService userSeqNoGnrService;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	AES256 aesEncryptor;
	
	@Autowired
    private MessageSource messageSource;
	
    /**
    
     * @Method Name : getUser
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 사용자정보 조회
   
     * @변경이력 : 
   
     */
    public UserDto getUser(String userId) throws HmfbException {
    	UserDto userDto = sqlSession.selectOne("hmfb.web.user.UserMapper.getUser", userId);
    	if(!ObjectUtils.isEmpty(userDto))
    		userDto.setPhnNo(aesEncryptor.decrypt(userDto.getPhnNo()));
    	return sqlSession.selectOne("hmfb.web.user.UserMapper.getUser", userId);
    }
    
    /**
    
	 * @Method Name : getUserAuthTreeMenu
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 사용자 권한 트리 메뉴 조회
   
     * @변경이력 : 
   
     */
    public void getUserMenu(UserDetail user) {
    	List<MenuDto> menuList = sqlSession.selectList("hmfb.web.user.UserMapper.getUserAuthMenuList", user.getAuthDvcd());
    	user.setAllMenuUrls(menuList.stream().filter(v -> v.getUrl() != null).map(v -> v.getUrl()).collect(Collectors.toCollection(ArrayList::new)));
    	user.setAuthMenuUrls(menuList.stream().filter(v -> v.getUrl() != null && v.getAuthDvcd() != null).map(v -> v.getUrl()).collect(Collectors.toCollection(ArrayList::new)));
    	List<MenuDto> userAuthMenuList = menuList.stream().filter(v -> v.getAuthDvcd() != null).collect(Collectors.toList());
    	user.setUserAuthTreeMenu(toTreeMenu(getUprnMenuList(menuList, userAuthMenuList, userAuthMenuList)));
    }
    
    /**
    
	 * @Method Name : getUprnMenuList
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 사용자 권한 상위 메뉴 조회
   
     * @변경이력 : 
   
     */
    private List<MenuDto> getUprnMenuList(List<MenuDto> menuList, List<MenuDto> filterList, List<MenuDto> userAuthMenuList) {
    	List<MenuDto> uprnMenuList = menuList.stream().filter(v -> filterList.stream()
    			.map(MenuDto::getUprnSeqNo)
        		.collect(Collectors.toSet()).contains(v.getSeqNo()))
    	.collect(Collectors.toList());
    	uprnMenuList.stream().forEach(userAuthMenuList::add);
    	return !ObjectUtils.isEmpty(uprnMenuList) ? getUprnMenuList(menuList, uprnMenuList, userAuthMenuList) : userAuthMenuList;
    }
    
    /**
    
	 * @Method Name : toTreeMenu
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 트리 메뉴 변환
   
     * @변경이력 : 
   
     */
    public MenuDto toTreeMenu(List<MenuDto> userAuthMenuList) {
		Map<String, List<MenuDto>> menuByUprnSeqNoMap = userAuthMenuList.stream().filter(v -> v.getUprnSeqNo() != null).collect(Collectors.groupingBy(MenuDto::getUprnSeqNo));
		userAuthMenuList.forEach(menuDto-> {
			List<MenuDto> Childern = menuByUprnSeqNoMap.get(menuDto.getSeqNo());
			if(Childern != null) Collections.sort(Childern, new MenuOdrComparator());
			menuDto.setChildern(Childern);
		});
		Optional<MenuDto> root = userAuthMenuList.stream().filter(v -> v.getUprnSeqNo() == null).findFirst(); 
		return 	root.isPresent() ? root.get() : null;
	}
	
    /**
    
	 * @Method Name : MenuOdrComparator
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 메뉴 순번 Comparator
   
     * @변경이력 : 
   
     */
	class MenuOdrComparator implements Comparator<MenuDto> {
	    @Override
	    public int compare(MenuDto m1, MenuDto m2) {
	        if (Integer.parseInt(m1.getOdr()) > Integer.parseInt(m2.getOdr())) {
	            return 1;
	        } else if (Integer.parseInt(m1.getOdr()) < Integer.parseInt(m2.getOdr())) {
	            return -1;
	        }
	        return 0;
	    }
	}
    
	/**
    
     * @Method Name : getUserList
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 사용자정보 내역조회
   
     * @변경이력 : 
   
     */
    public List<UserDto> getUserList(UserDto userDto) throws HmfbException {
    	List<UserDto> userList = sqlSession.selectList("hmfb.web.user.UserMapper.getUserList", userDto);
    	
    	for(UserDto user : userList)
    		user.setPhnNo(aesEncryptor.decrypt(user.getPhnNo()));
    	
    	return userList;
    }
    
    /**
    
     * @Method Name : getUserTotCnt
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 사용자정보 총 건수 조회
   
     * @변경이력 : 
   
     */
    public int getUserTotCnt(UserDto userDto) throws HmfbException {
    	return (Integer)sqlSession.selectOne("hmfb.web.user.UserMapper.getUserTotCnt", userDto);
    }
    
	/**
	     
	 * @Method Name : saveUser
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 사용자정보 저장
   
     * @변경이력 : 
   
     */
    public int saveUser(UserDto	userDto) throws HmfbException  {
    	String dttm = DateUtil.getCurrentDttm();
    	userDto.setSeqNo(userSeqNoGnrService.getNextLongId());
    	userDto.setPswd(bCryptPasswordEncoder.encode(userDto.getPswd()));
    	userDto.setPhnNo(aesEncryptor.encrypt(userDto.getPhnNo()));
    	if(ObjectUtils.isEmpty(userDto.getAuthDvcd()))
    		userDto.setAuthDvcd("AGMNG");
    	userDto.setRegDt(dttm.substring(0, 8));
    	userDto.setRegTm(dttm.substring(8));
    	return sqlSession.insert("hmfb.web.user.UserMapper.saveUser", userDto);
    }
    
    /**
    
	 * @Method Name : updateUserPswd
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 사용자 비밀번호 변경
   
     * @변경이력 : 
   
     */
    public int updateUserPswd(UserDto userDto) throws HmfbException  {
    	UserDto user = this.getUser(userDto.getUserId());
    	
    	if(!bCryptPasswordEncoder.matches(userDto.getOldPswd(), user.getPswd())){
            throw new HmfbException(ErrorCode.E103, messageSource.getMessage(ErrorCode.E103.getMessage(), new Object[] {}, Locale.getDefault()));
        }
    	
    	String dttm = DateUtil.getCurrentDttm();
    	
    	userDto.setUserId(userDto.getUserId());
    	userDto.setPswd(bCryptPasswordEncoder.encode(userDto.getNewPswd()));
    	userDto.setUpdDt(dttm.substring(0, 8));
    	userDto.setUpdTm(dttm.substring(8));
    	return sqlSession.update("hmfb.web.user.UserMapper.updateUserPswd", userDto);
    }
    
    /**
    
	 * @Method Name : delUsers
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 사용자 삭제
   
     * @변경이력 : 
   
     */
    public void delUsers(List<UserDto> userDtoList) throws HmfbException  {
    	String dttm = DateUtil.getCurrentDttm();
    	userDtoList.stream().forEach(userDto -> {
    		userDto.setUpdDt(dttm.substring(0, 8));
        	userDto.setUpdTm(dttm.substring(8));
    		sqlSession.delete("hmfb.web.user.UserMapper.deleteUser", userDto);
    	});
    }
    
    /**
    
	 * @Method Name : saveAuthDvcds
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 사용자 권한 수정
   
     * @변경이력 : 
   
     */
    public void saveAuthDvcds(List<UserDto> userDtoList) throws HmfbException  {
    	String dttm = DateUtil.getCurrentDttm();
    	userDtoList.stream().forEach(userDto -> {
    		userDto.setUpdDt(dttm.substring(0, 8));
        	userDto.setUpdTm(dttm.substring(8));
    		sqlSession.insert("hmfb.web.user.UserMapper.saveAuthDvcds", userDto);
    	});
    }
    
}
