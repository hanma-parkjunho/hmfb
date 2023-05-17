package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import hmfb.core.service.BaseMessage;
import lombok.Data;

@Data
public class F0500100Dto implements BaseMessage{
	@FixedString(order = 1, type = MessageFieldType.ALPHABET, value = 15)
	private String prtsacnutNo;
	@FixedString(order = 2, type = MessageFieldType.ALPHABET, value = 15)
	private String virtlAcnutNo;
	@FixedString(order = 3, type = MessageFieldType.ALPHABET, value = 1)
	private String blceSmbol;
	@FixedString(order = 4, type = MessageFieldType.NUMERIC, value = 13)
	private String blce;
	@FixedString(order = 5, type = MessageFieldType.NUMERIC, value = 13)
	private String defrayPosblAmount;
	@FixedString(order = 6, type = MessageFieldType.ALPHABET, value = 243)
	private String filler;

}
