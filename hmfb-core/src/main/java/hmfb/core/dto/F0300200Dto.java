package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import hmfb.core.service.BaseMessage;
import lombok.Data;

@Data
public class F0300200Dto implements BaseMessage{
	
	@FixedString(order = 1, type = MessageFieldType.ALPHABET, value = 15)
	private String agremSn;
	@FixedString(order = 2, type = MessageFieldType.ALPHABET, value = 3)
	private String pmsId;
	@FixedString(order = 3, type = MessageFieldType.ALPHABET, value = 20)
	private String agremInsttId;
	@FixedString(order = 4, type = MessageFieldType.ALPHABET, value = 1)
	private String trmnatSe;
	@FixedString(order = 5, type = MessageFieldType.ALPHABET, value = 261)
	private String filler;
}
