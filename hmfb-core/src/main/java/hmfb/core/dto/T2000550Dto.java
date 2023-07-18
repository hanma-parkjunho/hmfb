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
public class T2000550Dto implements BaseMessage{
	
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
	private String filler; 			// 예비영역
	private String rspnsCode; 		// 응답코드
	private String rspnsMssage; 	// 응답메시지
	private String sendCode; 		// 전송여부
	
}