package hmfb.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class T0300200Dto extends AbstractDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String regDate;
	private String telemsgNo;
	private String agremSn;
	private String pmsId;
	private String agremInsttId;
	private String trmnatSe;
	private String sendYn;
	private String rspnsCode;
	private String rspnsMssage;
}
