package hmfb.web.user.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import hmfb.core.dto.UserDto;
import hmfb.core.exception.ErrorCode;
import hmfb.core.log.service.LogService;
import hmfb.core.utils.DateUtil;
import hmfb.web.utils.SecurityUtils;

/**

 * @FileName : SecurityConfig.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : SecurityConfig 클래스

 * @변경이력 :

 */

@EnableWebSecurity        //spring security 를 적용한다는 Annotation
public class SecurityConfig {
    
	@Autowired
    @Qualifier("defaulSqlSessionTemplate")
    private SqlSession sqlSession;
	
	@Autowired
	LogService logService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	SecurityProperties securityProperties;
	
	@Autowired
    private MessageSource messageSource;
	
	/**
    
     * @Method Name : filterChain
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : HttpSecurity 규칙 설정
   
     * @변경이력 : 
   
     */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
	    	.authorizeRequests()
	    	.antMatchers(securityProperties.getAntMatchers()).permitAll()
	    	.anyRequest().authenticated()
	    	.and()
	    	.formLogin()
	    	.loginPage(securityProperties.getLoginPage())
	    	.loginProcessingUrl(securityProperties.getLoginProcessingUrl())
	    	.successHandler( 
	    		new AuthenticationSuccessHandler() {
	    			
	    			@Override
				    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
	    				String dttm = DateUtil.getCurrentDttm();
	    				UserDetail userDetail = (UserDetail)authentication.getPrincipal();
	    				userDetail.setLstLgnDttm(dttm);
	    				sqlSession.update("hmfb.web.user.UserMapper.updateLstLgnDttm", UserDto.builder().userId(userDetail.getUserId()).lstLgnDttm(dttm).updDt(dttm.substring(0, 8)).updTm(dttm.substring(8)).build());
		    			Map<String, Object> authRslt = SecurityConfig.getAuthRslt(userDetail.getUserId(), SecurityUtils.getClientIP(request), true, "");
		    			response.setCharacterEncoding("utf-8");
	    				response.setHeader("content-type", "application/json");
	    				response.getWriter().println(objectMapper.writeValueAsString(authRslt));
				    }
	    	})
	    	.failureHandler( // 로그인 실패 후 핸들러
	    		new AuthenticationFailureHandler() {
	    			
	    			private Logger log = LogManager.getLogger("Login Fail");
	    			
	    			@Override
	    		    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
	    				Map<String, Object> authRslt = SecurityConfig.getAuthRslt(request.getParameter("username"), SecurityUtils.getClientIP(request), false, exception.getMessage().toString());
	    				log.error(authRslt.toString(), exception.getCause());
	    				response.setCharacterEncoding("utf-8");
	    				response.setHeader("content-type", "application/json");
	    				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
	    				response.getWriter().println(objectMapper.writeValueAsString(authRslt));
	    		    }
	    	})
	    	.and()
	    	.logout().logoutSuccessUrl(securityProperties.getLoginPage())
	    	.and()
	    	.exceptionHandling()
	    	.authenticationEntryPoint(
				new LoginUrlAuthenticationEntryPoint(securityProperties.getLoginPage()) {
					private Logger log = LogManager.getLogger("Authentication Entry Point");
					private boolean forceHttps = false;
					private boolean useForward = false;
					private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
					
					@Override
					public void commence(HttpServletRequest request, HttpServletResponse response,
							AuthenticationException authException) throws IOException, ServletException {
						// TODO Auto-generated method stub
						if(SecurityConfig.isAjax(request)) {
							Map<String, Object> authRslt = SecurityConfig.getAuthRslt("", SecurityUtils.getClientIP(request), false, messageSource.getMessage(ErrorCode.E401.getMessage(), new Object[] {}, Locale.getDefault()));
		    				log.error(authRslt.toString(), authException.getCause());
		    				response.setCharacterEncoding("utf-8");
		    				response.setHeader("content-type", "application/json");
		    				response.setStatus(HttpStatus.UNAUTHORIZED.value());
		    				response.getWriter().println(objectMapper.writeValueAsString(authRslt));
						} else {
							if (!this.useForward) {
								// redirect to login page. Use https if forceHttps true
								String redirectUrl = buildRedirectUrlToLoginPage(request, response, authException);
								this.redirectStrategy.sendRedirect(request, response, redirectUrl);
								return;
							}
							String redirectUrl = null;
							if (this.forceHttps && "http".equals(request.getScheme())) {
								// First redirect the current request to HTTPS. When that request is received,
								// the forward to the login page will be used.
								redirectUrl = buildHttpsRedirectUrlForRequest(request);
							}
							if (redirectUrl != null) {
								this.redirectStrategy.sendRedirect(request, response, redirectUrl);
								return;
							}
							String loginForm = determineUrlToUseForThisRequest(request, response, authException);
							log.debug(LogMessage.format("Server side forward to: %s", loginForm));
							RequestDispatcher dispatcher = request.getRequestDispatcher(loginForm);
							dispatcher.forward(request, response);
							return;
						}
							
					}	
	    	})
	    	.and()
	    	.headers().frameOptions().sameOrigin()
	    	.and()
	    	.csrf().disable();
		return http.build();
	
    }
	
	/**
	
	  * @Method Name : getAuthRslt
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 :  로그인 결과 로그 처리 
	
	  * @변경이력 : 
	
	  */
	private static Map<String, Object> getAuthRslt(String username, String clientIP, boolean loginSuccess, String message) {
		Map<String, Object> authRslt = new HashMap<>();
		authRslt.put("loginID", username);
		authRslt.put("clientIP", clientIP);
		authRslt.put("loginSuccess", loginSuccess);
		authRslt.put("message", message);
		authRslt.put("dttm", DateUtil.getCurrentDttm());
		return authRslt;
	}
	
	/**
	
	  * @Method Name : getAuthRslt
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 :  SecurityAuthenticationProvider 설정
	
	  * @변경이력 : 
	
	  */
	@Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider())
                .build();
    }
	
	/**
	
	  * @Method Name : authenticationProvider
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 :  SecurityAuthenticationProvider 생성
	
	  * @변경이력 : 
	
	  */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new SecurityAuthenticationProvider();
    }

    /**
	
	  * @Method Name : bCryptPasswordEncoder
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 :  암호화 Encoder 생성
	
	  * @변경이력 : 
	
	  */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    private static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}
