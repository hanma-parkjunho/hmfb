package hmfb.core.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class T0500200Dto extends AbstractDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String regDate;
	private String telemsgNo;
	private String receptInfo;
	private String bankCode;
	private String acnutNo;
	private BigDecimal delngAmount = BigDecimal.ZERO;
	private String dpstrNm;
	private String rspnsCode;
	private String rspnsMssage;
	private String sendYn;
}
