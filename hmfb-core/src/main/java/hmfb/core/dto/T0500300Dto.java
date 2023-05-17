package hmfb.core.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class T0500300Dto extends AbstractDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String regDate;
	private String telemsgNo;
	private String requstDe;
	private String bizrno;
	private String virtlAcnutno;
	private String stdrDe;
	private String newDe;
	private String goodsCode;
	private BigDecimal acnutBlce = BigDecimal.ZERO;
	private BigDecimal trmnatBlce = BigDecimal.ZERO;
	private BigDecimal bfrxIntr = BigDecimal.ZERO;
	private BigDecimal afttxIntr = BigDecimal.ZERO;
	private BigDecimal crrx = BigDecimal.ZERO;
	private BigDecimal incmtax = BigDecimal.ZERO;
	private BigDecimal ihnts = BigDecimal.ZERO;
	private BigDecimal agspt = BigDecimal.ZERO;
	private BigDecimal fee = BigDecimal.ZERO;
	private String delngStdrDe;
	private String delngSeCn;
	private String goodsNm;
	private String applcTaskNm;
	private String incomeBeginDe;
	private String incomeEndDe;
	private String rspnsCode;
	private String rspnsMssage;
	private String sendYn;
}
