package hmfb.core.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**

 * @FileName : PrgmDto.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : 프로그램 로그 DTO

 * @변경이력 :

 */

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Data
public class PrgmDto {
	
	private Integer pageIndex;
	private Integer pageUnit;
	private Integer pageSize;
    private Integer firstIndex;
    private Integer lastIndex;
    private Integer recordCountPerPage;
    
	private String sys;
	private String uuid;
	private String timestamp;
	private String prgmId;
	private String prgmLog;
	private String regDt;
	private String regTm;
	private String regDtTm;
	
	private String searchSys;
	private String searchKeyword;
	private String searchStDt;
	private String searchEdDt;
	private String searchPrgmId;
	
}