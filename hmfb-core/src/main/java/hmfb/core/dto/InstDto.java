package hmfb.core.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**

 * @FileName : InstDto.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : 기관기본 DTO 클래스

 * @변경이력 :

 */

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Setter
public class InstDto  extends AbstractPageDTO {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -582292223545646476L;
	private String seqNo;
    private String instCd;
    private String instNm;
    private String bizrNo;
    private String regDt;
    private String regTm;
    private String updDt;
    private String updTm;
  
}
