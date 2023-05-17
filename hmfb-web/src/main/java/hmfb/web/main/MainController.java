package hmfb.web.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import hmfb.core.exception.HmfbException;

/**

 * @FileName : MainController.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : Main Controller 클레스 

 * @변경이력 :

 */

@Controller
public class MainController {
	
	/**
    
     * @Method Name : main
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : main 페이지 호출 
   
     * @변경이력 : 
   
     */
	@RequestMapping("/")
	public String main(Model model) throws HmfbException  {
		return "main";
	}
	
}
