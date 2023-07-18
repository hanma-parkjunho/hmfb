package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import hmfb.core.service.BaseMessage;
import lombok.Data;

/**
 * @author USER
 * 지급(자금)이체처리결과
 */
@Data
public class F7000101Dto implements BaseMessage{
	
	@FixedString(order = 1, type = MessageFieldType.ALPHABET, value = 6)
	private String inqireNo; 		// 조회번호
	@FixedString(order = 2, type = MessageFieldType.ALPHABET, value = 15)
	private String pymntAcnut; 		// 지급계좌
	@FixedString(order = 3, type = MessageFieldType.NUMERIC, value = 3)
	private String bankCode; 		// 은행코드
	@FixedString(order = 4, type = MessageFieldType.ALPHABET, value = 15)
	private String rcpmnyAcnut; 	// 입금계좌
	@FixedString(order = 5, type = MessageFieldType.NUMERIC, value = 13)
	private String amount; 			// 금액
	@FixedString(order = 6, type = MessageFieldType.NUMERIC, value = 13)
	private String nrmltAmount; 	// 정상금액
	@FixedString(order = 7, type = MessageFieldType.NUMERIC, value = 13)
	private String incpctyAmount; 	// 불능금액
	@FixedString(order = 8, type = MessageFieldType.NUMERIC, value = 9)
	private String fee; 			// 수수료
	@FixedString(order = 9, type = MessageFieldType.NUMERIC, value = 6)
	private String transfrTime; 	// 이체시간
	@FixedString(order = 10, type = MessageFieldType.ALPHABET, value = 4)
	private String processResult; 	// 처리결과
	@FixedString(order = 11, type = MessageFieldType.ALPHABET, value = 103)
	private String filler; 			// 예비
	
}
