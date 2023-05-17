package hmfb.core.dto;

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
public class UserDto {
	
	private Integer pageIndex;
	private Integer pageUnit;
	private Integer pageSize;
    private Integer firstIndex;
    private Integer lastIndex;
    private Integer recordCountPerPage;
    
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
    
    private String oldPswd;
    private String newPswd;
    
}
