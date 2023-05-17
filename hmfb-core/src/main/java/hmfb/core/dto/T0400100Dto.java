package hmfb.core.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class T0400100Dto extends AbstractDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String regDate;
	private String telemsgNo;
	private String oridelngDe;
	private String oritelemsgNo;
	private String processResultInqireSe;
	private String oridelngRspnsCode;
	private String oridelngProcessTime;
	private String defrayAcnutNo;
	private String virtlAcnutNo;
	private BigDecimal delngAmount = BigDecimal.ZERO;
	private String blceSmbol;
	private BigDecimal blce = BigDecimal.ZERO;
	private String thrghAcnutNo1;
	private String thrghAcnutNo2;
	private String bankCode;
	private String acnutNo;
	private String irSafSe;
	private BigDecimal fee = BigDecimal.ZERO;
	private String cmsCode;
	private String defrayAcnutPrntxt;
	private String thrghAcnut1RcpmnyPrntxt;
	private String thrghAcnut1DefrayPrntxt;
	private String thrghAcnut2RcpmnyPrntxt;
	private String thrghAcnut2DefrayPrntxt;
	private String rcpmnyAcnutPrntxt;
	private String cinetDelngInnb;
	private String rspnsCode;
	private String rspnsMssage;
	private String sendYn;
}
