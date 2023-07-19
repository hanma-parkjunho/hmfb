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
public class T3100101Dto implements BaseMessage{
	
	// AbstractDTO 
	private static final long serialVersionUID = 1L;
	
	// serialVersionUID 
	private String telemsgNo;             // 전문번호   
	private String oritelemsgNo;	      // 원전문번호 
	private String pymntAcnut;	      // 지급계좌   
	private String bankCode;	      // 은행코드   
	private String rcpmnyAcnut;	      // 입금계좌   
	private String amount;		      // 금    액  
	private String nrmltAmount;	      // 정상금액   
	private String incpctyAmount;	      // 불능금액   
	private String subcnt;		      // SUB-CNT  
	private String subseq;		      // SUB-SEQ  
	private String diffbankProcessNo;     // 타행처리번호
	private String rcpmnyIncpctyAm;	      // 입금불능액 
	private String diffbankProcessCode;   // 타행처리코드
	
	/* 테이블에 추가안함 [s] */
	private String sendCode;			// 전송여부
	private String sendDt;				// 전송날짜
	private String sendTm;				// 전송시간
	/* 테이블에 추가안함  [e] */
	
	private String rspnsCode;			// 응답코드
	private String rspnsMssage;			// 응답메세지
	
	/* 테이블에 추가안함 [s] */
	private String recvCode;            //
	private String recvDt;				// 수신날짜
	private String recvTm;				// 수신시간
	/* 테이블에 추가안함 [e] */
	
}
