package hmfb.web.utils;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;

import hmfb.web.user.security.UserDetail;

/**

 * @FileName : SecurityUtils.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : SecurityUtils 클래스

 * @변경이력 :

 */

public class SecurityUtils {
	
	/**
	
	  * @Method Name : getClientIP
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 클라이언트 IP 가져오기
	
	  * @변경이력 : 
	
	  */
   public static String getClientIP(HttpServletRequest request) {
       String ip = request.getHeader("X-Forwarded-For");
       if (ip == null)
           ip = request.getHeader("Proxy-Client-IP");
       if (ip == null)
           ip = request.getHeader("WL-Proxy-Client-IP");
       if (ip == null)
           ip = request.getHeader("HTTP_CLIENT_IP");
       if (ip == null)
           ip = request.getHeader("HTTP_X_FORWARDED_FOR");
       if (ip == null)
           ip = request.getRemoteAddr();
       return ip;
   }
   
   /**
	
	  * @Method Name : isAuthenticated
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : Security 인증 여부 
	
	  * @변경이력 : 
	
	  */
   public static boolean isAuthenticated() {
	   return !"anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
   }
   
   /**
	
	  * @Method Name : getPrincipal
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : Security 인증 객체 얻기 
	
	  * @변경이력 : 
	
	  */
   public static UserDetail getPrincipal() {
	   return SecurityUtils.isAuthenticated() ? (UserDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal() : null;
   }
   
}
