package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import hmfb.core.service.BaseMessage;
import lombok.Data;

/**
 * @author USER
 * 당,타행예금주성명조회
 */
@Data
public class F6000101Dto implements BaseMessage{

	@FixedString(order = 1, type = MessageFieldType.ALPHABET, value = 15)
	private String inqireAcnutNo; // 조회계좌번호
	@FixedString(order = 2, type = MessageFieldType.ALPHABET, value = 13)
	private String rlnmNo; // 실명번호
	@FixedString(order = 3, type = MessageFieldType.ALPHABET, value = 12)
	private String dpstrNm; // 예금주명
	@FixedString(order = 4, type = MessageFieldType.ALPHABET, value = 3)
	private String bankCode; // 은행코드
	@FixedString(order = 5, type = MessageFieldType.NUMERIC, value = 1)
	private String rlnmCmpr; // 은행코드
	@FixedString(order = 6, type = MessageFieldType.ALPHABET, value = 156)
	private String filler; // 은행코드
	
}
