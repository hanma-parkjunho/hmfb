package hmfb.web.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import hmfb.core.dto.UserDto;
import hmfb.core.exception.HmfbException;
import hmfb.web.user.service.UserServiceImpl;

/**

 * @FileName : UserRestController.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : UserRestController 클레스 

 * @변경이력 :

 */

@RestController
public class UserRestController {
	
	@Autowired
    private UserServiceImpl userService;
	
	/**
    
     * @Method Name : getUser
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 사용자 조회
   
     * @변경이력 : 
   
     */
	@PostMapping("/user/getUser")
	public ResponseEntity<?> getUser(@RequestParam String userId, Model model) throws HmfbException {
		UserDto userDto = userService.getUser(userId);
		model.addAttribute("userId", userDto != null ? userDto.getUserId() : "");
		return ResponseEntity.ok().body(model);
	}
	
	
	/**
    
     * @Method Name : saveUser
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 사용자 저장
   
     * @변경이력 : 
   
     */
	@PostMapping("/user/saveUser")
	public ResponseEntity<?> saveUser(@ModelAttribute UserDto userDto, Model model) throws HmfbException  {
		userService.saveUser(userDto);
		model.addAttribute("seqNo", userDto.getSeqNo() != null ? userDto.getSeqNo() : "");
		return ResponseEntity.ok().body(model);
	}
	
	/**
    
     * @Method Name : updateUserPswd
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 사용자 비밀번호 변경
   
     * @변경이력 : 
   
     */
	@PostMapping("/user/updateUserPswd")
	public ResponseEntity<?> updateUserPswd(@ModelAttribute UserDto userDto, Model model) throws HmfbException  {
		userService.updateUserPswd(userDto);
		return ResponseEntity.ok().body("");
	}
	
	/**
    
     * @Method Name : delUsers
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 사용자 삭제
   
     * @변경이력 : 
   
     */
	@PostMapping("/user/delUsers")
	public ResponseEntity<?> delUsers(@RequestBody List<UserDto> userDtoList, Model model) throws HmfbException  {
		userService.delUsers(userDtoList);
		return ResponseEntity.ok().body("");
	}
	
	/**
    
     * @Method Name : saveAuthDvcds
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 사용자 권한 변경
   
     * @변경이력 : 
   
     */
	@PostMapping("/user/saveAuthDvcds")
	public ResponseEntity<?> saveAuthDvcds(@RequestBody List<UserDto> userDtoList, Model model) throws HmfbException  {
		userService.saveAuthDvcds(userDtoList);
		return ResponseEntity.ok().body("");
	}
}
