package hmfb.core.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class T0600100Dto extends AbstractDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String regDate;
	private String telemsgNo;
	private String delngDe;
	private String delngSn;
	private String acnutNo;
	private String seCode;
	private BigDecimal delngAmount = BigDecimal.ZERO;
	private String blceSmbol;
	private BigDecimal blce = BigDecimal.ZERO;
	private String delngSumry;
	private String delngTime;
	private String bankCode;
	private String delngBhfCode;
	private String cmsCode;
	private String checkBilNo;
	private BigDecimal csrccAmount = BigDecimal.ZERO;
	private BigDecimal prsnlchkAmount = BigDecimal.ZERO;
	private BigDecimal genchkAmount = BigDecimal.ZERO;
	private String delngMediaTy;
	private String rspnsCode;
	private String rspnsMssage;
	private String sendYn;
}
