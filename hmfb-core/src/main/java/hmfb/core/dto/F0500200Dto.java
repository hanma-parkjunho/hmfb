package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import hmfb.core.service.BaseMessage;
import lombok.Data;

@Data
public class F0500200Dto implements BaseMessage{
	@FixedString(order = 1, type = MessageFieldType.ALPHABET, value = 13)
	private String receptInfo;
	@FixedString(order = 2, type = MessageFieldType.NUMERIC, value = 3)
	private String bankCode;
	@FixedString(order = 3, type = MessageFieldType.ALPHABET, value = 15)
	private String acnutNo;
	@FixedString(order = 4, type = MessageFieldType.NUMERIC, value = 13)
	private String delngAmount;
	@FixedString(order = 5, type = MessageFieldType.ALPHABET, value = 20)
	private String dpstrNm;
	@FixedString(order = 6, type = MessageFieldType.ALPHABET, value = 236)
	private String filler;
}
