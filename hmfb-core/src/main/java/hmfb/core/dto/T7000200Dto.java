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
public class T7000200Dto implements BaseMessage{
	
	// AbstractDTO 
	private static final long serialVersionUID = 1L;
	
	// serialVersionUID 
	private String telemsgNo;       // 전문번호     
	private String inqireAcnutNo;	// 조회계좌번호  
	private String ovrdftLmt;	// 대월한도     
	private String blceSmbol;	// 잔액부호     
	private String nowBlce;		// 현재잔액     
	private String blceCash;	// 잔액-현금,대체
	private String blcePrsnlchk;	// 잔액-일반,가계
	private String etcBhfAm;	// 기타타점액   
	private String drtPosblSmbol;	// 인출가능부호  
	private String drtPosblAmount;	// 인출가능금액  
	
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
