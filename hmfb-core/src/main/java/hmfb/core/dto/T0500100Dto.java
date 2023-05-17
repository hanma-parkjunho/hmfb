package hmfb.core.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class T0500100Dto extends AbstractDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String regDate;
	private String telemsgNo;
	private String prtsacnutNo;
	private String virtlAcnutNo;
	private String blceSmbol;
	private BigDecimal blce = BigDecimal.ZERO;
	private BigDecimal defrayPosblAmount = BigDecimal.ZERO;
	private String rspnsCode;
	private String rspnsMssage;
	private String sendYn;
}
