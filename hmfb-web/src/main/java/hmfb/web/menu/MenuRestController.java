package hmfb.web.menu;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import hmfb.core.dto.MenuDto;
import hmfb.core.exception.HmfbException;
import hmfb.web.menu.service.MenuServiceImpl;

/**

 * @FileName : MenuRestController.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : MenuRestController 클레스 

 * @변경이력 :

 */

@RestController
public class MenuRestController {
	
	@Autowired
	MenuServiceImpl menuService;
	
	/**
    
     * @Method Name : delMenus
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 메뉴 삭제
   
     * @변경이력 : 
   
     */
	@PostMapping("/menu/delMenus")
	public ResponseEntity<?> delMenus(@RequestBody List<MenuDto> menuDtoList, Model model) throws HmfbException  {
		menuService.delMenuAuths(menuDtoList);
		menuService.delMenus(menuDtoList);
		return ResponseEntity.ok().body("");
	}
	
	/**
    
     * @Method Name : saveMenu
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 메뉴 저장
   
     * @변경이력 : 
   
     */
	@PostMapping("/menu/saveMenu")
	public ResponseEntity<?> saveMenu(@ModelAttribute MenuDto menuDto, Model model) throws HmfbException  {
		menuService.saveMenu(menuDto);
		return ResponseEntity.ok().body("");
	}
	
	/**
    
     * @Method Name : saveMenuAuths
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 메뉴 권한 저장
   
     * @변경이력 : 
   
     */
	@PostMapping("/menu/saveMenuAuths")
	public ResponseEntity<?> saveMenuAuths(@RequestBody Map<String, Object> parmas, Model model) throws HmfbException  {
		String authDvcd = parmas.get("authDvcd").toString();
		List<?> menuDtoList = (List<?>) parmas.get("saveMenuAuths");
		menuService.saveMenuAuths(authDvcd, menuDtoList);
		return ResponseEntity.ok().body("");
	}
	
}
