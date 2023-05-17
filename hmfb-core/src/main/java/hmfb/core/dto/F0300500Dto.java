package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import hmfb.core.service.BaseMessage;
import lombok.Data;

@Data
public class F0300500Dto implements BaseMessage{
	@FixedString(order = 1, type = MessageFieldType.ALPHABET, value = 10)
	private String orgCode;
	@FixedString(order = 2, type = MessageFieldType.ALPHABET, value = 10)
	private String agencyCode;
	@FixedString(order = 3, type = MessageFieldType.ALPHABET, value = 10)
	private String requstAcnutCnt;
	@FixedString(order = 4, type = MessageFieldType.ALPHABET, value = 15)
	private String beginVirtlAcnutno;
	@FixedString(order = 5, type = MessageFieldType.ALPHABET, value = 15)
	private String endVirtlAcnutno;
	@FixedString(order = 6, type = MessageFieldType.ALPHABET, value = 200)
	private String filler;
}
