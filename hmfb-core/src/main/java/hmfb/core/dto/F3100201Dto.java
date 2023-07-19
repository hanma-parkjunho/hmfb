package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import hmfb.core.service.BaseMessage;
import lombok.Data;

/**
 * @author USER
 * 타행이체결과 불능분 통지 결번요구
 */
@Data
public class F3100201Dto implements BaseMessage{
	
	@FixedString(order = 1, type = MessageFieldType.NUMERIC, value = 6)
	private String oritelemsgNo;		// 원전문번호
	@FixedString(order = 2, type = MessageFieldType.ALPHABET, value = 15)
	private String pymntAcnut; 			// 지급계좌
	@FixedString(order = 3, type = MessageFieldType.ALPHABET, value = 3)
	private String bankCode; 			// 은행코드
	@FixedString(order = 4, type = MessageFieldType.ALPHABET, value = 15)
	private String rcpmnyAcnut; 		// 입금계좌
	@FixedString(order = 5, type = MessageFieldType.NUMERIC, value = 13)
	private String amount; 				// 금액
	@FixedString(order = 6, type = MessageFieldType.NUMERIC, value = 13)
	private String nrmltAmount; 		// 정상금액
	@FixedString(order = 7, type = MessageFieldType.NUMERIC, value = 13)
	private String incpctyAmount; 		// 불능금액
	@FixedString(order = 8, type = MessageFieldType.NUMERIC, value = 2)
	private String subcnt; 				// SUB-CNT
	@FixedString(order = 9, type = MessageFieldType.NUMERIC, value = 2)
	private String subseq; 				// SUB-SEQ
	@FixedString(order = 10, type = MessageFieldType.NUMERIC, value = 6)
	private String diffbankProcessNo; 	// 타행처리번호
	@FixedString(order = 11, type = MessageFieldType.NUMERIC, value = 13)
	private String rcpmnyIncpctyAm; 	// 입금불능액
	@FixedString(order = 12, type = MessageFieldType.ALPHABET, value = 4)
	private String diffbankProcessCode; // 타행처리코드
	@FixedString(order = 13, type = MessageFieldType.ALPHABET, value = 95)
	private String fillter; 			// 예비
	
}
