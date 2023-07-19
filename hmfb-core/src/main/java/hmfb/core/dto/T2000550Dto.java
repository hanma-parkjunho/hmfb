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
	
	private String telemsgNo;	    // 전문번호       
	private String idntfcCode;	    // 식별코드       
	private String processSn; 	    // 처리순번       
	private String bankCode; 	    // 은행코드       
	private String acnutNo; 	    // 계좌번호       
	private String reqstSe; 	    // 신청구분       
	private String atmcpayDe; 	    // 자동납부일자    
	private String dsrbtr; 		    // 은행코드+취급점 
	private String reqstDe; 	    // 신청일자       
	private String processAt; 	    // 처리여부       
	private String incpctyCode; 	// 불능코드       
	private String rlnmNoCeckEnnc; 	// 실명번호 체크유무
	private String rlnmNo; 		    // 실명번호       
	private String payerNo; 	    // 납부자번호     
	private String cstmrRelm; 	    // 고객영역       
	private String insttCode; 	    // 기관코드  
	private String filler; 			// 예비영역
	private String rspnsCode; 		// 응답코드
	private String rspnsMssage; 	// 응답메시지
	private String sendCode; 		// 전송여부
	
}