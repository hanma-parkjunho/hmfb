package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import hmfb.core.service.BaseMessage;
import lombok.Data;

/**
 * @author USER
 * 자동이체 청구
 */
@Data
public class F2000850Dto implements BaseMessage{
	
	@FixedString(order = 1, type = MessageFieldType.ALPHABET, value = 9)
	private String pymntAcnut; 			// 지급계좌
	@FixedString(order = 2, type = MessageFieldType.ALPHABET, value = 12)
	private String password; 			// 비밀번호
	@FixedString(order = 3, type = MessageFieldType.ALPHABET, value = 2)
	private String revwSmbolL; 			// 복기부호
	@FixedString(order = 4, type = MessageFieldType.NUMERIC, value = 4)
	private String pymntAmount; 		// 지급금액
	@FixedString(order = 5, type = MessageFieldType.ALPHABET, value = 3)
	private String smbol; 				// 부호
	@FixedString(order = 6, type = MessageFieldType.NUMERIC, value = 1)
	private String blce; 				// 잔액
	@FixedString(order = 7, type = MessageFieldType.ALPHABET, value = 6)
	private String bankCode; 			// 은행코드
	@FixedString(order = 8, type = MessageFieldType.ALPHABET, value = 8)
	private String rcpmnyAcnut; 		// 입금계좌
	@FixedString(order = 9, type = MessageFieldType.NUMERIC, value = 6)
	private String fee; 				// 수수료
	@FixedString(order = 10, type = MessageFieldType.ALPHABET, value = 4)
	private String defrayAcnutPrntxt; 	// 출금계좌인자내용
	@FixedString(order = 11, type = MessageFieldType.ALPHABET, value = 9)
	private String rlnmNo; 				// 실명번호
	@FixedString(order = 12, type = MessageFieldType.ALPHABET, value = 15)
	private String unused; 				// 미사용	
	@FixedString(order = 13, type = MessageFieldType.ALPHABET, value = 11)
	private String insttCode; 			// 기관코드
	@FixedString(order = 14, type = MessageFieldType.ALPHABET, value = 1)
	private String payerNo; 			// 납부자번호
	@FixedString(order = 15, type = MessageFieldType.ALPHABET, value = 9)
	private String chrgeKnd; 			// 요금종류
	@FixedString(order = 16, type = MessageFieldType.ALPHABET, value = 9)
	private String payHopeDe; 			// 납부희망일
	@FixedString(order = 15, type = MessageFieldType.ALPHABET, value = 9)
	private String rcpmnyerNo; 			// 입금인번호
	@FixedString(order = 15, type = MessageFieldType.NUMERIC, value = 9)
	private String csrccAmount; 		// 자기앞수표금액
	@FixedString(order = 15, type = MessageFieldType.ALPHABET, value = 9)
	private String bndeEntrpsNo; 		// 일괄용업체번호
	@FixedString(order = 15, type = MessageFieldType.ALPHABET, value = 9)
	private String partDefrayAt; 		// 부분출금여부
	@FixedString(order = 15, type = MessageFieldType.ALPHABET, value = 9)
	private String filler; 				// 예비
	
}