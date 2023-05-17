package hmfb.core.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**

 * @FileName : MenuAuthDto.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : 메뉴기본 DTO 클래스

 * @변경이력 :

 */

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Setter
public class MenuDto {
	
	private Integer pageIndex;
	private Integer pageUnit;
	private Integer pageSize;
    private Integer firstIndex;
    private Integer lastIndex;
    private Integer recordCountPerPage;
    
	private String authDvcd;
    private String seqNo;
    private String uprnSeqNo;
    private String flnm;
    private String url;
    private String lvl;
    private String odr;
    private String uzYn;
    private String regDt;
    private String regTm;
    private String updDt;
    private String updTm;
    private List<MenuDto> childern;
    
}
