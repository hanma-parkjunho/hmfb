package hmfb.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class T0300400Dto extends AbstractDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String regDate;
	private String telemsgNo;
	private String rcpmnyPrtsacnut;
	private String defrayPrtsacnut;
	private String feePrtsacnut;
	private String agencyCode;
	private String agencyNm;
	private String rspnsCode;
	private String rspnsMssage;
	private String sendYn;
}
