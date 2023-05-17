package hmfb.core.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**

 * @FileName : LinkDto.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : 연계기본 DTO 클래스

 * @변경이력 :

 */

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Setter
public class LinkDto extends AbstractPageDTO {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -6685115085952438341L;
	private String seqNo;
    private String instDvcd;
    private String instSerNm;
    private String instHost;
    private String instPort;
    private String regDt;
    private String regTm;
    private String updDt;
    private String updTm;
  
}
