package hmfb.core.dto;

import hmfb.core.service.BaseMessage;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author USER
 * 업무개시
 */
@Data
@Getter
@Setter
public class T2000850Dto implements BaseMessage{
	
	// AbstractDTO 
	private static final long serialVersionUID = 1L;
	
	// serialVersionUID 
	private String telemsgNo;
	private String pymntAcnut;
	private String password;
	private String revwSmbol;
	private String pymntAmount;
	private String smbol;
	private String blce;
	private String bankCode;
	private String rcpmnyAcnut;
	private String fee;
	private String defrayAcnutPrntxt;
	private String rlnmNo;
	private String unused;
	private String insttCode;
	private String payerNo;
	private String chrgeKnd;
	private String payHopeDe;
	private String rcpmnyerNo;
	private String csrccAmount;
	private String bndeEntrpsNo;
	private String partDefrayAt;
	
	private String sendCode; 		// 전송여부
	
}
