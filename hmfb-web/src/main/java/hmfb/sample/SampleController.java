package hmfb.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import hmfb.core.exception.HmfbException;

/**

 * @FileName : SampleController.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : Controller 테스트 클레스 

 * @변경이력 :

 */

@Controller
public class SampleController {
	
	@Autowired
	SampleServiceImpl sampleServiceimpl;
	
	/**
    
     * @Method Name : sample
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 페이지 테스트
   
     * @변경이력 : 
   
     */
	@GetMapping("/sample/sample")
	public String sample() {
		return "sample/sample";
	}
	
	/**
    
     * @Method Name : errorPage
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 페이지 오류 발생 테스트
   
     * @변경이력 : 
   
     */
	@GetMapping("/sample/errorPage")
	public String errorPage() throws HmfbException {
		sampleServiceimpl.error();
		return "";
	}
	
}
