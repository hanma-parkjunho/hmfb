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
public class T2100850Dto implements BaseMessage{
	
	// AbstractDTO 
	private static final long serialVersionUID = 1L;
	
	// serialVersionUID 
	private String telemsgNo;            // 전문번호      
	private String pymntAcnut;	         // 지급계좌      
	private String password;	         // 비밀번호      
	private String revwSmbol;	         // 복기부호      
	private String pymntAmount;	         // 지급금액      
	private String smbol;		         // 부    호     
	private String blce;		         // 잔    액     
	private String bankCode;		     // 은행코드      
	private String rcpmnyAcnut;	         // 입금계좌      
	private String fee;		             // 수 수 료     
	private String defrayAcnutPrntxt;    // 출금계좌인자내용
	private String rlnmNo;		         // 실명번호      
	private String unused;		         // 미사용       
	private String insttCode;	         // 기관코드      
	private String payerNo;	             // 납부자번호    
	private String chrgeKnd;		     // 요금종류      
	private String payHopeDe;	         // 납부희망일    
	private String rcpmnyerNo;	         // 입금인번호    
	private String csrccAmount;	         // 자기앞수표금액 
	private String bndeEntrpsNo;	     // 일괄용업체번호 
	private String partDefrayAt;	     // 부분출금여부
	
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
