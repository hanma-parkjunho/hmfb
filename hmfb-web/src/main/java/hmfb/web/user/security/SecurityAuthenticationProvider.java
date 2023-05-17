package hmfb.web.user.security;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.ObjectUtils;

import hmfb.core.dto.UserDto;
import hmfb.core.exception.ErrorCode;
import hmfb.core.exception.HmfbSecurityException;
import hmfb.web.user.service.UserServiceImpl;

/**

 * @FileName : SecurityAuthenticationProvider.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : SecurityAuthenticationProvider 클레스

 * @변경이력 :

 */

public class SecurityAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
    private MessageSource messageSource;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
    @Autowired
    private UserServiceImpl userService;
    
    /**
	
	  * @Method Name : authenticate
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 :  로그인처리 
	
	  * @변경이력 : 
	
	  */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException  {
        
    	String username = authentication.getName();
        String password = (String)authentication.getCredentials();
        
        UserDetail user = null;
        
        try {
        	
        	UserDto	userDto = userService.getUser(username);
        	
        	if(userDto == null) {
    			throw new HmfbSecurityException(messageSource.getMessage(ErrorCode.E102.getMessage(), new Object[] {}, Locale.getDefault()));
    		}
        	
            if(!bCryptPasswordEncoder.matches(password,userDto.getPswd())){
                throw new HmfbSecurityException(messageSource.getMessage(ErrorCode.E103.getMessage(), new Object[] {}, Locale.getDefault()));
            }
            
            if(ObjectUtils.isEmpty(userDto.getAuthDvcd())){
                throw new HmfbSecurityException(messageSource.getMessage(ErrorCode.E104.getMessage(), new Object[] {}, Locale.getDefault()));
            }
            
            user = UserDetail.builder()
            	.seqNo(userDto.getSeqNo())
                .userId(userDto.getUserId())
                .flnm(userDto.getFlnm())
                .phnNo(userDto.getPhnNo())
                .depart(userDto.getDepart())
                .authDvcd(userDto.getAuthDvcd())
                .authDvNm(userDto.getAuthDvNm())
                .build();
        	
            userService.getUserMenu(user);
            
        } catch (Exception ex) {
        	if(ex instanceof HmfbSecurityException)
        		throw new SecurityException(ex.getMessage());
        	else
        		throw new SecurityException(messageSource.getMessage(ErrorCode.E101.getMessage(), new Object[] {}, Locale.getDefault()));
		}
        
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
