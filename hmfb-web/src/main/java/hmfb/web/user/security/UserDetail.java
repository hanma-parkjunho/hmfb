package hmfb.web.user.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import hmfb.core.dto.MenuDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**

 * @FileName : UserDto.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : 사용자기본 DTO 클래스

 * @변경이력 :

 */

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Setter
public class UserDetail implements UserDetails {

    private static final long serialVersionUID = 8459595353646247308L;
	
    private Long seqNo;
    private String userId;
    private String pswd;
    private String flnm;
    private String phnNo;
    private String depart;
    private String authDvcd;
    private String authDvNm;
    private String lstLgnDttm;
    private String delYn;
    private String regDt;
    private String regTm;
    private String updDt;
    private String updTm;
    private MenuDto userAuthTreeMenu;
    private List<String> allMenuUrls;
    private List<String> authMenuUrls;
    
    @Override
	public String getUsername() {
		return this.flnm;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.authDvcd));
    }

}
