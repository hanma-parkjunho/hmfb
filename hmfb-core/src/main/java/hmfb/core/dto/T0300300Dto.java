package hmfb.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class T0300300Dto extends AbstractDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String regDate;
	private String telemsgNo;
	private String prtsacnutNo;
	private String virtlAcnutNo;
	private String taxtTy;
	private String bsnmInfo;
	private String insttNm;
	private String insttRprsntvNm;
	private String rspnsCode;
	private String rspnsMssage;
	private String sendYn;
}
