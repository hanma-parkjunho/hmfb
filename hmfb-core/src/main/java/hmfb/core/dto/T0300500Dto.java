package hmfb.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class T0300500Dto extends AbstractDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String regDate;
	private String telemsgNo;
	private String agencyCode;
	private String requstAcnutCnt;
	private String beginVirtlAcnutno;
	private String endVirtlAcnutno;
	private String rspnsCode;
	private String rspnsMssage;
	private String sendYn;
}
