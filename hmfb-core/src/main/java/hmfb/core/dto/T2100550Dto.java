package hmfb.core.dto;

import hmfb.core.service.BaseMessage;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author USER
 * 자동이체신청
 */
@Data
@Getter
@Setter
public class T2100550Dto implements BaseMessage{
	
	private static final long serialVersionUID = 1L;
	
	private String telemsgNo;		// 전문번호
	private String idntfcCode;		// 식별코드
	private String processSn; 		// 처리순번
	private String bankCode; 		// 은행코드
	private String acnutNo; 		// 계좌번호
	private String reqstSe; 		// 신청구분
	private String atmcpayDe; 		// 자동납부일자
	private String dsrbtr; 			// 은행코드+취급점
	private String reqstDe; 		// 신청일자
	private String processAt; 		// 실명번호
	private String incpctyCode; 	// 납부자번호
	private String rlnmNoCeckEnnc; 	// 고객영역
	private String rlnmNo; 			// 기관코드
	private String payerNo; 		// sds영역
	private String cstmrRelm; 		// 고객영역
	private String insttCode; 		// y2k구분

	private String filler1; 			// 예비1
	private String filler2; 			// 예비2
	private String filler3; 			// 예비3
	private String filler4; 			// 예비4
	private String filler5; 			// 예비5	
	
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