package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import hmfb.core.service.BaseMessage;
import lombok.Data;

@Data
public class F0500300Dto implements BaseMessage{
	@FixedString(order = 1, type = MessageFieldType.ALPHABET, value = 13)
	private String bizrno;
	@FixedString(order = 2, type = MessageFieldType.ALPHABET, value = 12)
	private String virtlAcnutno;
	@FixedString(order = 3, type = MessageFieldType.ALPHABET, value = 8)
	private String stdrDe;
	@FixedString(order = 4, type = MessageFieldType.ALPHABET, value = 8)
	private String newDe;
	@FixedString(order = 5, type = MessageFieldType.ALPHABET, value = 9)
	private String goodsCode;
	@FixedString(order = 6, type = MessageFieldType.NUMERIC, value = 18)
	private String acnutBlce;
	@FixedString(order = 7, type = MessageFieldType.NUMERIC, value = 18)
	private String trmnatBlce;
	@FixedString(order = 8, type = MessageFieldType.NUMERIC, value = 14)
	private String bfrxIntr;
	@FixedString(order = 9, type = MessageFieldType.NUMERIC, value = 14)
	private String afttxIntr;
	@FixedString(order = 10, type = MessageFieldType.NUMERIC, value = 14)
	private String crrx;
	@FixedString(order = 11, type = MessageFieldType.NUMERIC, value = 14)
	private String incmtax;
	@FixedString(order = 12, type = MessageFieldType.NUMERIC, value = 14)
	private String ihnts;
	@FixedString(order = 13, type = MessageFieldType.NUMERIC, value = 14)
	private String agspt;
	@FixedString(order = 14, type = MessageFieldType.NUMERIC, value = 10)
	private String fee;
	@FixedString(order = 15, type = MessageFieldType.ALPHABET, value = 8)
	private String delngStdrDe;
	@FixedString(order = 16, type = MessageFieldType.ALPHABET, value = 24)
	private String delngSeCn;
	@FixedString(order = 17, type = MessageFieldType.ALPHABET, value = 24)
	private String goodsNm;
	@FixedString(order = 18, type = MessageFieldType.ALPHABET, value = 24)
	private String applcTaskNm;
	@FixedString(order = 19, type = MessageFieldType.ALPHABET, value = 8)
	private String incomeBeginDe;
	@FixedString(order = 20, type = MessageFieldType.ALPHABET, value = 8)
	private String incomeEndDe;
	@FixedString(order = 21, type = MessageFieldType.ALPHABET, value = 24)
	private String filler;
}
