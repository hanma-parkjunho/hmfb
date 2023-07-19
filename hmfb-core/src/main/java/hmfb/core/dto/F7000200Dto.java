package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import hmfb.core.service.BaseMessage;
import lombok.Data;

/**
 * @author USER
 * 모계좌 잔액조회
 */
@Data
public class F7000200Dto implements BaseMessage{
	
	@FixedString(order = 1, type = MessageFieldType.ALPHABET, value = 15)
	private String inqireAcnutNo; 	// 조회계좌번호
	@FixedString(order = 2, type = MessageFieldType.NUMERIC, value = 13)
	private String ovrdftLmt; 		// 대월한도
	@FixedString(order = 3, type = MessageFieldType.ALPHABET, value = 1)
	private String blceSmbol; 		// 잔액부호
	@FixedString(order = 4, type = MessageFieldType.NUMERIC, value = 13)
	private String nowBlce; 		// 현재잔액	
	@FixedString(order = 5, type = MessageFieldType.NUMERIC, value = 13)
	private String blceCasg; 		// 잔액-현금,대체
	@FixedString(order = 6, type = MessageFieldType.NUMERIC, value = 13)
	private String blcePrsnlchk;	// 잔액-일반,가계
	@FixedString(order = 7, type = MessageFieldType.NUMERIC, value = 13)
	private String etcBhfAm; 		// 기타타점액
	@FixedString(order = 8, type = MessageFieldType.ALPHABET, value = 1)
	private String drtPosblSmbol; 	// 인출가능부호
	@FixedString(order = 9, type = MessageFieldType.NUMERIC, value = 13)
	private String drtPosblAmount; 	// 인출가능금액
	@FixedString(order = 10, type = MessageFieldType.ALPHABET, value = 105)
	private String filler; 			// 예비영역
	
}
