package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import hmfb.core.service.BaseMessage;
import lombok.Data;

/**
 * @author USER
 * 자동이체 신청
 * 
 * KJY
 * 
 */
@Data
public class F2000550Dto implements BaseMessage{

	@FixedString(order = 1, type = MessageFieldType.ALPHABET, value = 1)
	private String idntfcCode; 			// 식별코드
	@FixedString(order = 2, type = MessageFieldType.NUMERIC, value = 7)
	private String processSn; 			// 처리순번
	@FixedString(order = 3, type = MessageFieldType.ALPHABET, value = 3)
	private String bankCode; 			// 은행코드
	@FixedString(order = 4, type = MessageFieldType.ALPHABET, value = 16)
	private String acnutNo; 			// 계좌번호
	@FixedString(order = 5, type = MessageFieldType.ALPHABET, value = 1)
	private String reqstSe; 			// 신청구분
	@FixedString(order = 6, type = MessageFieldType.ALPHABET, value = 2)
	private String atmcpayDe; 			// 자동납부일자
	@FixedString(order = 7, type = MessageFieldType.ALPHABET, value = 7)
	private String dsrbtr; 				// 은행코드 + 취급점
	@FixedString(order = 8, type = MessageFieldType.ALPHABET, value = 8)
	private String reqstDe; 			// 신청일자
	@FixedString(order = 9, type = MessageFieldType.ALPHABET, value = 1)
	private String processAt; 			// 처리여부
	@FixedString(order = 10, type = MessageFieldType.ALPHABET, value = 4)
	private String incpctyCode; 		// 불능코드
	@FixedString(order = 11, type = MessageFieldType.ALPHABET, value = 1)
	private String rlnmNoCeckEnnc; 		// 실명번호 체크유무
	@FixedString(order = 12, type = MessageFieldType.ALPHABET, value = 13)
	private String rlnmNo; 				// 실명번호
	@FixedString(order = 13, type = MessageFieldType.ALPHABET, value = 20)
	private String payerNo; 			// 납부자번호
	@FixedString(order = 14, type = MessageFieldType.ALPHABET, value = 16)
	private String cstmrRelm; 			// 고객영역
	@FixedString(order = 15, type = MessageFieldType.ALPHABET, value = 10)
	private String insttCode; 			// 기관코드
	@FixedString(order = 16, type = MessageFieldType.ALPHABET, value = 90)
	private String filler; 				// 예비영역
	
}
