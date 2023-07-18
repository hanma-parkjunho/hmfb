package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import hmfb.core.service.BaseMessage;
import lombok.Data;

/**
 * @author USER
 * 예금거래(교환어음)명세통지
 */
@Data
public class F4000101Dto implements BaseMessage{
	
	@FixedString(order = 1, type = MessageFieldType.ALPHABET, value = 15)
	private String acnutNo; 		// 계좌번호
	@FixedString(order = 2, type = MessageFieldType.ALPHABET, value = 7)
	private String rcpmnyBhf; 		// 입금점
	@FixedString(order = 3, type = MessageFieldType.ALPHABET, value = 2)
	private String rcpmnyPymntSe; 	// 입지구분
	@FixedString(order = 4, type = MessageFieldType.ALPHABET, value = 2)
	private String delngSe; 		// 거래구분
	@FixedString(order = 5, type = MessageFieldType.NUMERIC, value = 13)
	private String amount; 			// 금액
	@FixedString(order = 6, type = MessageFieldType.NUMERIC, value = 13)
	private String altrtvAmount; 	// 대체
	@FixedString(order = 7, type = MessageFieldType.NUMERIC, value = 13)
	private String etc; 			// 기타
	@FixedString(order = 8, type = MessageFieldType.ALPHABET, value = 1)
	private String smbol; 			// SIGN부호
	@FixedString(order = 9, type = MessageFieldType.NUMERIC, value = 13)
	private String blce; 			// 잔액
	@FixedString(order = 10, type = MessageFieldType.ALPHABET, value = 12)
	private String nm; 				// 성명
	@FixedString(order = 11, type = MessageFieldType.ALPHABET, value = 10)
	private String checkBilNo; 		// 수표/어음번호
	@FixedString(order = 12, type = MessageFieldType.NUMERIC, value = 8)
	private String elngDe; 			// 거래일자
	@FixedString(order = 13, type = MessageFieldType.NUMERIC, value = 6)
	private String elngTime; 		// 거래시간
	@FixedString(order = 14, type = MessageFieldType.NUMERIC, value = 6)
	private String sn; 				// 일련번호
	@FixedString(order = 15, type = MessageFieldType.NUMERIC, value = 6)
	private String oridelngNo; 		// 원거래번호
	@FixedString(order = 16, type = MessageFieldType.NUMERIC, value = 8)
	private String oridelngDe; 		// 원거래일자
	@FixedString(order = 17, type = MessageFieldType.ALPHABET, value = 16)
	private String rcpmnyerCode; 	// 입금인코드
	@FixedString(order = 18, type = MessageFieldType.NUMERIC, value = 13)
	private String csrcc; 			// 자기앞
	@FixedString(order = 19, type = MessageFieldType.NUMERIC, value = 13)
	private String prsnlchk; 		// 가계
	@FixedString(order = 20, type = MessageFieldType.ALPHABET, value = 23)
	private String filler; 			// 예비
	
}
