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
public class T7000101Dto implements BaseMessage{
	
	// AbstractDTO 
	private static final long serialVersionUID = 1L;
	
	// serialVersionUID 
    private String telemsgNo;
	private String inqireNo;
	private String pymntAcnut;
	private String bankCode;
	private String rcpmnyAcnut;
	private String amount;
	private String nrmltAmount;
	private String incpctyAmount;
	private String fee;
	private String transfrTime;
	private String processResult;
	
	private String sendCode; 		// 전송여부
	
}
