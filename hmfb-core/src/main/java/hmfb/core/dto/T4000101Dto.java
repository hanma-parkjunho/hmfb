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
public class T4000101Dto implements BaseMessage{
	
	// AbstractDTO 
	private static final long serialVersionUID = 1L;
	
	// serialVersionUID 
	private String telemsgNo;        // 전문번호    
	private String acnutNo;		 // 계좌번호    
	private String rcpmnyBhf;	 // 입 금 점   
	private String rcpmnyPymntSe;	 // 입지구분    
	private String delngSe;		 // 거래구분    
	private String amount;		 // 금    액   
	private String altrtvAmount;	 // 대    체   
	private String etc;		 // 기    타   
	private String smbol;		 // SIGN부호   
	private String blce;		 // 잔    액   
	private String nm;		 // 성    명   
	private String checkBilNo;	 // 수표/어음번호
	private String delngDe;		 // 거래일자    
	private String delngTime;	 // 거래시간    
	private String sn;		 // 일련번호    
	private String oridelngNo;	 // 원거래번호  
	private String oridelngDe;	 // 원거래일자  
	private String rcpmnyerCode;	 // 입금인코드  
	private String csrcc;		 // 자 기 앞   
	private String prsnlchk;	 // 가    계
	
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
