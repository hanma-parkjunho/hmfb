package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import hmfb.core.service.BaseMessage;
import lombok.Data;

@Data
public class F0300300Dto implements BaseMessage{
	@FixedString(order = 1, type = MessageFieldType.ALPHABET, value = 15)
	private String prtsacnutNo;
	@FixedString(order = 2, type = MessageFieldType.ALPHABET, value = 15)
	private String virtlAcnutNo;
	@FixedString(order = 3, type = MessageFieldType.ALPHABET, value = 2)
	private String taxtTy;
	@FixedString(order = 4, type = MessageFieldType.ALPHABET, value = 13)
	private String bsnmInfo;
	@FixedString(order = 5, type = MessageFieldType.ALPHABET, value = 100)
	private String insttNm;
	@FixedString(order = 6, type = MessageFieldType.ALPHABET, value = 20)
	private String insttRprsntvNm;
	@FixedString(order = 7, type = MessageFieldType.ALPHABET, value = 135)
	private String filler;
}
