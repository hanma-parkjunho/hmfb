package pub.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**

 * @FileName : PubController.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : pub 클레스 

 * @변경이력 :

 */

@Controller
public class PubController {
	
	
	/**
    
     * @Method Name : index
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 페블 가이드 index
   
     * @변경이력 : 
   
     */
	@GetMapping("/")
	public String index() {
		return "guide/index";
	}
	
	/**
    
     * @Method Name : layout
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 페블 가이드 layout
   
     * @변경이력 : 
   
     */
	@GetMapping("/layout")
	public String layout() {
		return "guide/layout";
	}
	
	/**
    
     * @Method Name : layout2
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 페블 가이드 layout
   
     * @변경이력 : 
   
     */
	@GetMapping("/layout2")
	public String layout2() {
		return "guide/layout2";
	}
	
	/**
    
     * @Method Name : page
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 페블 가이드 index
   
     * @변경이력 : 
   
     */
	@GetMapping("/views/{page}")
	public String page(@PathVariable String page) {
		return "views/"+page;
	}
	
}
