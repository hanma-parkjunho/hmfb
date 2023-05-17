package hmfb.web.cmmn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import hmfb.core.cmmn.service.CmmnServiceImpl;
import hmfb.core.dto.CmmnDto;
import hmfb.core.exception.HmfbException;

/**

 * @FileName : CmmnRestController.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : CmmnRestController 클레스 

 * @변경이력 :

 */

@RestController
public class CmmnRestController {
	
	@Autowired
	private CmmnServiceImpl cmmnService;
	
	/**
    
     * @Method Name : saveCmmn
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 공통코드 저장
   
     * @변경이력 : 
   
     */
	@PostMapping("/cmmn/saveCmmn")
	public ResponseEntity<?> saveCmmn(@ModelAttribute("cmmnDto") CmmnDto cmmnDto, Model model) throws HmfbException  {
		cmmnService.saveCmmn(cmmnDto);
		return ResponseEntity.ok().body("");
	}
	
	/**
    
     * @Method Name : delCmmns
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 공통코드 삭제
   
     * @변경이력 : 
   
     */
	@PostMapping("/cmmn/delCmmns")
	public ResponseEntity<?> delCmmns(@RequestBody List<CmmnDto> cmmnDtoList, Model model) throws HmfbException  {
		cmmnService.delCmmns(cmmnDtoList);
		return ResponseEntity.ok().body("");
	}
	
	
}
