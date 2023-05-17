package hmfb.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class T0100100Dto extends AbstractDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String jobDt;
	private String jobTm;
	private String jobSe;
	private String rspnsCode;
	private String rspnsMssage;
}
