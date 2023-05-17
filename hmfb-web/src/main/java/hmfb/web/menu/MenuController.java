package hmfb.web.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import hmfb.core.cmmn.service.CmmnServiceImpl;
import hmfb.core.dto.CmmnDto;
import hmfb.core.dto.MenuDto;
import hmfb.core.exception.HmfbException;
import hmfb.web.menu.service.MenuServiceImpl;

/**

 * @FileName : MenuController.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : MenuController 클레스 

 * @변경이력 :

 */

@Controller
public class MenuController {
	
	@Value("${pagination.pageUnit}")
	private int pageUnit;
	
	@Value("${pagination.pageSize}")
	private int pageSize;
	
	@Autowired
	MenuServiceImpl menuService;
	
	@Autowired
	private CmmnServiceImpl cmmnService;
	
	/**
    
     * @Method Name : menuMngInfo
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 메뉴관리 - 메뉴관리정보 페이지 호출 
   
     * @변경이력 : 
   
     */
	@RequestMapping("/menu/menuMngInfo")
    public String menuMngInfo(@ModelAttribute("menuDto") MenuDto menuDto, Model model) throws HmfbException {
		model.addAttribute("menuList", menuService.getMenuList(menuDto));
		return "menu/menuMngInfo";
    }
	
	/**
    
     * @Method Name : menuAuthInfo
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 메뉴관리 - 메뉴권한정보 페이지 호출 
   
     * @변경이력 : 
   
     */
	@RequestMapping("/menu/menuAuthInfo")
    public String menuAuthInfo(@ModelAttribute("menuDto") MenuDto menuDto, Model model) throws HmfbException {
		model.addAttribute("selMenuDto", menuDto);
		model.addAttribute("authDvcdList", cmmnService.getCmmn(CmmnDto.builder().codeGrp("AUTH_DVCD").build()));
		if(!ObjectUtils.isEmpty(menuDto.getAuthDvcd()))
			model.addAttribute("menuAuthList", menuService.getMenuAuthList(menuDto));
		return "menu/menuAuthInfo";
    }
}
