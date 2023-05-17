package hmfb.sample;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.JsonObject;
import hmfb.core.exception.ErrorCode;
import hmfb.core.exception.HmfbException;
import hmfb.core.exception.HmfbResponseEntity;
import hmfb.core.idgnr.service.IdGnrService;
import hmfb.core.log.service.LogService;
import hmfb.web.user.security.UserDetail;
import hmfb.web.user.service.UserServiceImpl;

/**

 * @FileName : SampleRestController.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : RestController 테스트 클레스 

 * @변경이력 :

 */

@RestController
public class SampleRestController {
	
	@Value("${project.sys}")
	private String sys;
	
	@Autowired
	LogService logService;
	
	@Autowired
	SampleService sampleService;
	
	@Autowired
	SampleServiceImpl sampleServiceImpl;
	
	@Autowired
    private MessageSource messageSource;
	
	/**
    
     * @Method Name : api
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : api 테스트
   
     * @변경이력 : 
   
     */
	@GetMapping("/sample/api")
	public ResponseEntity<?> api(HttpServletRequest request)  {
		JsonObject json = new JsonObject();
		json.addProperty("aaaa", "bbb");
		return ResponseEntity.ok().body(json.toString());
	}
	
	/**
    
     * @Method Name : Service
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : Service 테스트
   
     * @변경이력 : 
   
     */
	@GetMapping("/sample/Service")
	public ResponseEntity<?> Service() {
		sampleService.getCurrentDataTimeByDao();
		sampleService.getColunm1ByMapper();
		return ResponseEntity.ok().body("");
	}
	
	/**
    
     * @Method Name : ServiceImpl
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : ServiceImpl 테스트
   
     * @변경이력 : 
   
     */
	@GetMapping("/sample/ServiceImpl")
	public ResponseEntity<?> ServiceImpl() {
		sampleServiceImpl.getCurrentDataTimeByDao();
		return ResponseEntity.ok().body("");
	}
	
	@Autowired
    private IdGnrService userSeqNoGnrService;
	
	@Autowired
    private IdGnrService menuSeqNoGnrService;
	
	/**
	   
	 * @Method Name : idGnrTable
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : idGnr 테스트
   
     * @변경이력 : 
   
     */
	@GetMapping("/sample/idGnrTable")
	public ResponseEntity<?> idGnrTable()  {
		JsonObject json = new JsonObject();
		try {
			json.addProperty("userSeqNo getNextStringId", userSeqNoGnrService.getNextStringId());
			json.addProperty("userSeqNo getNextLongId", userSeqNoGnrService.getNextLongId());
			json.addProperty("menuSeqNo getNextLongId", menuSeqNoGnrService.getNextStringId());
		} catch (HmfbException ex) {
			/*
    		 * Service 오류 발생 시 AfterThrowing에서 오류 로그 처리됨
    		 * 오류 로그 이중 처리 방지를 위해 Controller에서 try catch 처리 
    		 * Controller에서는 ServiceImpl 사용 추천 
    		 * 사용시 Controller HmfbResponseEntity 리턴 처리
    		 */ 
			return HmfbResponseEntity.error();
		}
		return ResponseEntity.ok().body(json);
	}
	
	@Autowired
    private IdGnrService uuidGnrService;
	
	/**
	   
	 * @Method Name : idGnrUUId
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : idGnr UUId 테스트
   
     * @변경이력 : 
   
     */
	@GetMapping("/sample/idGnrUUId")
	public ResponseEntity<?> idGnrUUId()  {
		JsonObject json = new JsonObject();
		try {
			json.addProperty("getNextStringId", uuidGnrService.getNextStringId());
		} catch (HmfbException ex) {
			/*
    		 * Service 오류 발생 시 AfterThrowing에서 오류 로그 처리됨
    		 * 오류 로그 이중 처리 방지를 위해 Controller에서 try catch 처리 
    		 * Controller에서는 ServiceImpl 사용 추천 
    		 * 사용시 Controller HmfbResponseEntity 리턴 처리
    		 */ 
			return HmfbResponseEntity.error();
		}
		return ResponseEntity.ok().body(json);
		
	}
	
	/**
    
     * @Method Name : errorLogService
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 :  error Log 테스트 
   
     * @변경이력 : 
   
     */
	@GetMapping("/sample/errorLogService")
	public ResponseEntity<?> errorLogService(HttpServletRequest request) {
		try {
			sampleService.error();
    	} catch (HmfbException ex) {
    		/*
    		 * Service 오류 발생 시 AfterThrowing에서 오류 로그 처리됨
    		 * 오류 로그 이중 처리 방지를 위해 Controller에서 try catch 처리 
    		 * Controller에서는 ServiceImpl 사용 추천 
    		 * 사용시 Controller HmfbResponseEntity 리턴 처리
    		 */ 
			return HmfbResponseEntity.error("errorLogService 처리");
		}
		
		return ResponseEntity.ok().body("");
	}
	
	/**
    
     * @Method Name : runtimeErrorLogService
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : runtime error Log 테스트 
   
     * @변경이력 : 
   
     */
	@GetMapping("/sample/runtimeErrorLogService")
	public ResponseEntity<?> runtimeErrorLogService(HttpServletRequest request) {
		try {
			sampleService.runtimeError("dwdwdw");
		} catch (RuntimeException ex) {
			/*
    		 * Service 오류 발생 시 AfterThrowing에서 오류 로그 처리됨
    		 * 오류 로그 이중 처리 방지를 위해 Controller에서 try catch 처리 
    		 * Controller에서는 ServiceImpl 사용 추천 
    		 * 사용시 Controller HmfbResponseEntity 리턴 처리
    		 */ 
			return HmfbResponseEntity.error(messageSource, ErrorCode.E500);
		}
		return ResponseEntity.ok().body("");
	}
	
	/**
    
     * @Method Name : errorLogServiceImplThrows
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : error Log ServiceImpl 테스트 
   
     * @변경이력 : 
   
     */
	@GetMapping("/sample/errorLogServiceImpl")
	public ResponseEntity<?> errorLogServiceImpl() throws HmfbException {
		sampleServiceImpl.error();
		return ResponseEntity.ok().body("");
	}
	
	/**
    
     * @Method Name : rollbackService
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : rollback No Service 테스트  
   
     * @변경이력 : 
   
     */
	@GetMapping("/sample/rollbackNoService")
	public ResponseEntity<?> rollbackNoService(HttpServletRequest request) {
		try {
			sampleService.rollbackNo();
		} catch (HmfbException ex) {
			/*
    		 * Service 오류 발생 시 AfterThrowing에서 오류 로그 처리됨
    		 * 오류 로그 이중 처리 방지를 위해 Controller에서 try catch 처리 
    		 * Controller에서는 ServiceImpl 사용 추천 
    		 * 사용시 Controller HmfbResponseEntity 리턴 처리
    		 */ 
			return HmfbResponseEntity.error(messageSource, ErrorCode.E500);
		}
		return ResponseEntity.ok().body("");
	}
	
	/**
    
     * @Method Name : rollbackYes
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : rollback Yes Service 테스트  
   
     * @변경이력 : 
   
     */
	@GetMapping("/sample/rollbackYesService")
	public ResponseEntity<?> rollbackYesService(HttpServletRequest request) {
		try {
			sampleService.rollbackYes();
		} catch (HmfbException ex) {
			/*
    		 * Service 오류 발생 시 AfterThrowing에서 오류 로그 처리됨
    		 * 오류 로그 이중 처리 방지를 위해 Controller에서 try catch 처리 
    		 * Controller에서는 ServiceImpl 사용 추천 
    		 * 사용시 Controller HmfbResponseEntity 리턴 처리
    		 */ 
			return HmfbResponseEntity.error(messageSource, ErrorCode.E500);
		}
		return ResponseEntity.ok().body("");
	}
	
	/**
    
     * @Method Name : rollbackYes
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : modify Sqlseesion Service 테스트  
   
     * @변경이력 : 
   
     */
	@GetMapping("/sample/modifySqlseesionService")
	public ResponseEntity<?> modifySqlseesionService(HttpServletRequest request) {
		sampleService.modifySqlseesion();
		return ResponseEntity.ok().body("");
	}
	
	/**
    
     * @Method Name : rollbackServiceImpl
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : rollback ServiceImpl 테스트  
   
     * @변경이력 : 
   
     */
	@GetMapping("/sample/rollbackServiceImpl")
	public ResponseEntity<?> rollbackServiceImpl(HttpServletRequest request) throws HmfbException {
		sampleServiceImpl.transactionAspect();
		return ResponseEntity.ok().body("");
	}
	
	@Autowired
    private UserServiceImpl userService;
	
	/**
    
     * @Method Name : userAuthTreeMenu
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : user Auth Tree Menu 테스트   
   
     * @변경이력 : 
   
     */
	@GetMapping("/sample/userAuthTreeMenu")
	public ResponseEntity<?> userAuthTreeMenu(HttpServletRequest request) throws HmfbException {
		UserDetail user = UserDetail.builder().authDvcd("ADMIN").build();
        userService.getUserMenu(user);
		return ResponseEntity.ok().body(user.getUserAuthTreeMenu());
	}
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	/**
    
     * @Method Name : bCryptPasswordEncoder
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : bCrypt Password Encoder 테스트  
   
     * @변경이력 : 
   
     */
	@GetMapping("/sample/bCryptPasswordEncoder")
	public ResponseEntity<?> bCryptPasswordEncoder(HttpServletRequest request) throws HmfbException {
		String password = request.getParameter("password") == null ? "" : request.getParameter("password").toString();
		String encPassword = bCryptPasswordEncoder.encode(password);
		JsonObject json = new JsonObject();
		json.addProperty("encPassword", encPassword);
		json.addProperty("length", encPassword.length());
		return ResponseEntity.ok().body(json);
	}
	
}
