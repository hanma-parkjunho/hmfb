package hmfb.core.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**

 * @FileName : CmmnDto.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : 공통코드 DTO 클레스

 * @변경이력 :

 */

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Data
public class CmmnDto {
	
	private Integer pageIndex;
	private Integer pageUnit;
	private Integer pageSize;
    private Integer firstIndex;
    private Integer lastIndex;
    private Integer recordCountPerPage;
    
	private String codeGrp;
	private String code;
	private String name;
	private String code2;
	private String name2;
	private String code3;
	private String name3;
	private String code4;
	private String name4;
	private String code5;
	private String name5;
	private String seq;
	private String uzYn;
	private String regDt;
	private String regTm;
	private String updDt;
	private String updTm;
	
}
