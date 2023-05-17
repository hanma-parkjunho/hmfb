package hmfb.core.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class T0300100Dto extends AbstractDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String regDate;
	private String telemsgNo;
	private String agremSn;
	private String pmsId;
	private String agremInsttId;
	private String wctAcnutno;
	private String wctAcnut1BankCode;
	private String wctAcnut1No;
	private String wctAcnut2BankCode;
	private String wctAcnut2No;
	private String spcltyInsttBizrno;
	private String spcltyInsttNm;
	private String agremInstt1Bizrno;
	private String agremInstt1Nm;
	private String agremInstt2Bizrno;
	private String agremInstt2Nm;
	private BigDecimal gvrnDntAmount = BigDecimal.ZERO;
	private BigDecimal locgovBndCashAmount = BigDecimal.ZERO;
	private BigDecimal prvateBndCashAmount = BigDecimal.ZERO;
	private BigDecimal agremLmtAmount = BigDecimal.ZERO;
	private String gvrnDntRcpmnyDe;
	private String locgovBndRcpmnyDe;
	private String prvateBndRcpmnyDe;
	private String taskId;
	private String taskNm;
	private String bsnsClCode;
	private String bsnsClNm;
	private String sendYn;
	private String rspnsCode;
	private String rspnsMssage;
}
