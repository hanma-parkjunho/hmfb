package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import hmfb.core.service.BaseMessage;
import lombok.Data;

@Data
public class F0200200Dto implements BaseMessage{
	@FixedString(order = 1, type = MessageFieldType.ALPHABET, value = 15)
	private String defrayAcnutNo;
	@FixedString(order = 2, type = MessageFieldType.ALPHABET, value = 15)
	private String virtlAcnutNo;
	@FixedString(order = 3, type = MessageFieldType.NUMERIC, value = 6)
	private String revwSmbol;
	@FixedString(order = 4, type = MessageFieldType.NUMERIC, value = 13)
	private String delngAmount;
	@FixedString(order = 5, type = MessageFieldType.ALPHABET, value = 1)
	private String blceSmbol;
	@FixedString(order = 6, type = MessageFieldType.NUMERIC, value = 13)
	private String blce;
	@FixedString(order = 7, type = MessageFieldType.NUMERIC, value = 3)
	private String bankCode;
	@FixedString(order = 8, type = MessageFieldType.ALPHABET, value = 15)
	private String acnutNo;
	@FixedString(order = 9, type = MessageFieldType.ALPHABET, value = 3)
	private String irSafSe;
	@FixedString(order = 10, type = MessageFieldType.ALPHABET, value = 1)
	private String salaryTransfrSe;
	@FixedString(order = 11, type = MessageFieldType.NUMERIC, value = 9)
	private String fee;
	@FixedString(order = 12, type = MessageFieldType.ALPHABET, value = 20)
	private String cmsCode;
	@FixedString(order = 13, type = MessageFieldType.ALPHABET, value = 14)
	private String defrayAcnutPrntxt;
	@FixedString(order = 14, type = MessageFieldType.ALPHABET, value = 14)
	private String rcpmnyAcnutPrntxt;
	@FixedString(order = 15, type = MessageFieldType.ALPHABET, value = 13)
	private String cinetDelngInnb;
	@FixedString(order = 16, type = MessageFieldType.ALPHABET, value = 145)
	private String filler;

}
