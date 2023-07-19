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
public class F1100100Dto implements BaseMessage{
		
	@FixedString(order = 1, type = MessageFieldType.ALPHABET, value = 200)
	private String filler;
	
}
