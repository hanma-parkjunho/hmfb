package hmfb.core.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class T0200300Dto extends AbstractDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String regDate;
	private String telemsgNo;
	private String defrayAcnutNo;
	private String virtlAcnutNo;
	private String revwSmbol;
	private BigDecimal delngAmount = BigDecimal.ZERO;
	private String blceSmbol;
	private BigDecimal blce = BigDecimal.ZERO;
	private String thrghAcnutNo;
	private String bankCode;
	private String acnutNo;
	private String irSafSe;
	private BigDecimal fee = BigDecimal.ZERO;
	private String cmsCode;
	private String defrayAcnutPrntxt;
	private String thrghAcnutRcpmnyPrntxt;
	private String thrghAcnutDefrayPrntxt;
	private String rcpmnyAcnutPrntxt;
	private String cinetDelngInnb;
	private String agremSn;
	private String pmsId;
	private String agremInsttId;
	private String rspnsCode;
	private String rspnsMssage;
	private String sendYn;
}
