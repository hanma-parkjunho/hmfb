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
public class T4100201Dto implements BaseMessage{
	
	// AbstractDTO 
	private static final long serialVersionUID = 1L;
	
	// serialVersionUID 
	private String telemsgNo;
	private String acnutNo;
	private String rcpmnyBhf;
	private String rcpmnyPymntSe;
	private String delngSe;
	private String amount;
	private String altrtvAmount;
	private String etc;
	private String smbol;
	private String blce;
	private String nm;
	private String checkBilNo;
	private String delngDe;
	private String delngTime;
	private String sn;
	private String oridelngNo;
	private String oridelngDe;
	private String rcpmnyerCode;
	private String csrcc;
	private String prsnlchk;
	
	/* 테이블에 추가안함 [s] */
	private String sendCode;			// 전송여부
	private String sendDt;				// 전송날짜
	private String sendTm;				// 전송시간
	/* 테이블에 추가안함  [e] */
	
	private String rspnsCode;			// 응답코드
	private String rspnsMssage;			// 응답메세지
	
	/* 테이블에 추가안함 [s] */
	private String recvDt;				// 수신날짜
	private String recvTm;				// 수신시간
	/* 테이블에 추가안함 [e] */
	
}
