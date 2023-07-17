package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import hmfb.core.service.BaseMessage;
import lombok.Data;

/**
 * @author USER
 * 업무개시
 */
@Data
public class F2000101Dto implements BaseMessage{
	@FixedString(order = 1, type = MessageFieldType.ALPHABET, value = 6)
	private String telemsgNo;			// 식별번호
	@FixedString(order = 1, type = MessageFieldType.ALPHABET, value = 15)
	private String pymentAcunt; 		// 지급계좌
	@FixedString(order = 2, type = MessageFieldType.ALPHABET, value = 8)
	private String password; 			// 비밀번호
	@FixedString(order = 3, type = MessageFieldType.NUMERIC, value = 6)
	private String revwSmbol; 			// 복기부호
	@FixedString(order = 4, type = MessageFieldType.ALPHABET, value = 13)
	private String pymntMount; 			// 지급금액
	@FixedString(order = 5, type = MessageFieldType.ALPHABET, value = 1)
	private String smbol; 				// 부호
	@FixedString(order = 6, type = MessageFieldType.NUMERIC, value = 13)
	private String blce; 				// 잔액
	@FixedString(order = 7, type = MessageFieldType.NUMERIC, value = 3)
	private String bankCode; 			// 은행코드
	@FixedString(order = 8, type = MessageFieldType.NUMERIC, value = 14)
	private String rcpmnyAcnut; 		// 입금계좌
	@FixedString(order = 9, type = MessageFieldType.NUMERIC, value = 9)
	private String fee; 				// 수수료
	@FixedString(order = 10, type = MessageFieldType.ALPHABET, value = 16)
	private String cmsCode; 			// CMS코드
	@FixedString(order = 11, type = MessageFieldType.ALPHABET, value = 14)
	private String rcpmnyAcnutPrntxt;	// 입금계좌인자내용
	@FixedString(order = 12, type = MessageFieldType.ALPHABET, value = 13)
	private String rlnmNo; 				// 실명번호
	@FixedString(order = 13, type = MessageFieldType.ALPHABET, value = 12)
	private String dpstrNm; 			// 예금주명
	@FixedString(order = 14, type = MessageFieldType.ALPHABET, value = 2)
	private String cptalCharct; 		// 자금성격
	
	@FixedString(order = 15, type = MessageFieldType.ALPHABET, value = 5)
	private String filler1; 			// 예비1
	@FixedString(order = 16, type = MessageFieldType.ALPHABET, value = 5)
	private String filler2; 			// 예비2
	@FixedString(order = 17, type = MessageFieldType.ALPHABET, value = 5)
	private String filler3; 			// 예비3
	@FixedString(order = 18, type = MessageFieldType.ALPHABET, value = 5)
	private String filler4; 			// 예비4
	@FixedString(order = 19, type = MessageFieldType.ALPHABET, value = 5)
	private String filler5; 			// 예비5
	
	@FixedString(order = 20, type = MessageFieldType.ALPHABET, value = 4)
	private String rspnsCode; 			// 응답코드
	@FixedString(order = 21, type = MessageFieldType.ALPHABET, value = 500)
	private String rspnsMssage;			// 응답메세지
	@FixedString(order = 22, type = MessageFieldType.ALPHABET, value = 2)
	private String sendCode;			// 이체전송여부
		
}