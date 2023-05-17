package hmfb.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import hmfb.core.cmmn.service.CmmnServiceImpl;
import hmfb.core.dto.CmmnDto;
import hmfb.core.dto.UserDto;
import hmfb.core.exception.HmfbException;
import hmfb.core.pagination.PaginationInfo;
import hmfb.web.user.service.UserServiceImpl;
import hmfb.web.utils.SecurityUtils;

/**

 * @FileName : UserController.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : UserController 클레스 

 * @변경이력 :

 */

@Controller
public class UserController {
	
	@Value("${security.defaultSuccessUrl}")
	private String defaultSuccessUrl;
	
	@Value("${pagination.pageUnit}")
	private int pageUnit;
	
	@Value("${pagination.pageSize}")
	private int pageSize;
	
	@Autowired
    private UserServiceImpl userService;
	
	@Autowired
	private CmmnServiceImpl cmmnService;
	
	/**
    
     * @Method Name : login
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : login 페이지 호출
   
     * @변경이력 : 
   
     */
	@GetMapping("/login")
    public String login(Model model){
    	
		if(SecurityUtils.isAuthenticated())
			return "redirect:" + defaultSuccessUrl;
		
        return "user/login";
    }
    
	/**
    
     * @Method Name : login
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : login 페이지 호출
   
     * @변경이력 : 
   
     */
	@GetMapping("/signUp")
    public String signUp(Model model){
    	
		if(SecurityUtils.isAuthenticated())
			return "redirect:" + defaultSuccessUrl;
		
        return "user/signUp";
    }
	
	/**
    
     * @Method Name : userInfo
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 사용자관리 - 사용자정보 페이지 호출
   
     * @변경이력 : 
   
     */
	@RequestMapping("/user/userInfo")
    public String userInfo(@ModelAttribute("userDto") UserDto userDto, Model model) throws HmfbException {	
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(ObjectUtils.isEmpty(userDto.getPageIndex()) ? 1 : userDto.getPageIndex());
		paginationInfo.setRecordCountPerPage(ObjectUtils.isEmpty(userDto.getPageUnit()) ? pageUnit : userDto.getPageUnit());
		paginationInfo.setPageSize(ObjectUtils.isEmpty(userDto.getPageSize()) ? pageSize : userDto.getPageSize());
		paginationInfo.setTotalRecordCount(userService.getUserTotCnt(userDto));
		
		userDto.setFirstIndex(paginationInfo.getCurrentRecordIndex());
		userDto.setLastIndex(paginationInfo.getNextRecordIndex());
		userDto.setPageIndex(paginationInfo.getCurrentPageNo());
		userDto.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		model.addAttribute("searchUserDto", userDto);
		model.addAttribute("userList", userService.getUserList(userDto));
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("userAuthDvcdList", cmmnService.getCmmn(CmmnDto.builder().codeGrp("AUTH_DVCD").build()));
		
        return "user/userInfo";
    }
	
	/**
    
     * @Method Name : authInfo
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 사용자관리 - 권한정보 페이지 호출 
   
     * @변경이력 : 
   
     */
	@RequestMapping("/user/authInfo")
    public String authInfo(@ModelAttribute("userDto") UserDto userDto, Model model) throws HmfbException {	
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(ObjectUtils.isEmpty(userDto.getPageIndex()) ? 1 : userDto.getPageIndex());
		paginationInfo.setRecordCountPerPage(ObjectUtils.isEmpty(userDto.getPageUnit()) ? pageUnit : userDto.getPageUnit());
		paginationInfo.setPageSize(ObjectUtils.isEmpty(userDto.getPageSize()) ? pageSize : userDto.getPageSize());
		paginationInfo.setTotalRecordCount(userService.getUserTotCnt(userDto));
		
		userDto.setFirstIndex(paginationInfo.getCurrentRecordIndex());
		userDto.setLastIndex(paginationInfo.getNextRecordIndex());
		userDto.setPageIndex(paginationInfo.getCurrentPageNo());
		userDto.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		model.addAttribute("searchUserDto", userDto);
		model.addAttribute("userList", userService.getUserList(userDto));
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("userAuthDvcdList", cmmnService.getCmmn(CmmnDto.builder().codeGrp("AUTH_DVCD").build()));
		
        return "user/authInfo";
    }
}
