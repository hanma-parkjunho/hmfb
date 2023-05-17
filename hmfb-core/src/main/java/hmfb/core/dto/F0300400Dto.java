package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import hmfb.core.service.BaseMessage;
import lombok.Data;

@Data
public class F0300400Dto implements BaseMessage{
	@FixedString(order = 1, type = MessageFieldType.ALPHABET, value = 8)
	private String orgCode;
	@FixedString(order = 2, type = MessageFieldType.ALPHABET, value = 15)
	private String rcpmnyPrtsacnut;
	@FixedString(order = 3, type = MessageFieldType.ALPHABET, value = 15)
	private String defrayPrtsacnut;
	@FixedString(order = 4, type = MessageFieldType.ALPHABET, value = 15)
	private String feePrtsacnut;
	@FixedString(order = 5, type = MessageFieldType.ALPHABET, value = 5)
	private String agencyCode;
	@FixedString(order = 6, type = MessageFieldType.ALPHABET, value = 30)
	private String agencyNm;
	@FixedString(order = 7, type = MessageFieldType.ALPHABET, value = 112)
	private String filler;
}
