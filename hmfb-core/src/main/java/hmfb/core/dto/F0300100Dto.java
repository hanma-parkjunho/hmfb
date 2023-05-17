package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import hmfb.core.service.BaseMessage;
import lombok.Data;

@Data
public class F0300100Dto implements BaseMessage{
	@FixedString(order = 1, type = MessageFieldType.ALPHABET, value = 15)
	private String agremSn;
	@FixedString(order = 2, type = MessageFieldType.ALPHABET, value = 3)
	private String pmsId;
	@FixedString(order = 3, type = MessageFieldType.ALPHABET, value = 20)
	private String agremInsttId;
	@FixedString(order = 4, type = MessageFieldType.ALPHABET, value = 15)
	private String wctAcnutno;
	@FixedString(order = 5, type = MessageFieldType.NUMERIC, value = 3)
	private String wctAcnut1BankCode;
	@FixedString(order = 6, type = MessageFieldType.ALPHABET, value = 15)
	private String wctAcnut1No;
	@FixedString(order = 7, type = MessageFieldType.NUMERIC, value = 3)
	private String wctAcnut2BankCode;
	@FixedString(order = 8, type = MessageFieldType.ALPHABET, value = 15)
	private String wctAcnut2No;
	@FixedString(order = 9, type = MessageFieldType.ALPHABET, value = 10)
	private String spcltyInsttBizrno;
	@FixedString(order = 10, type = MessageFieldType.ALPHABET, value = 100)
	private String spcltyInsttNm;
	@FixedString(order = 11, type = MessageFieldType.ALPHABET, value = 10)
	private String agremInstt1Bizrno;
	@FixedString(order = 12, type = MessageFieldType.ALPHABET, value = 100)
	private String agremInstt1Nm;
	@FixedString(order = 13, type = MessageFieldType.ALPHABET, value = 10)
	private String agremInstt2Bizrno;
	@FixedString(order = 14, type = MessageFieldType.ALPHABET, value = 100)
	private String agremInstt2Nm;
	@FixedString(order = 15, type = MessageFieldType.NUMERIC, value = 15)
	private String gvrnDntAmount;
	@FixedString(order = 16, type = MessageFieldType.NUMERIC, value = 15)
	private String locgovBndCashAmount;
	@FixedString(order = 17, type = MessageFieldType.NUMERIC, value = 15)
	private String prvateBndCashAmount;
	@FixedString(order = 18, type = MessageFieldType.NUMERIC, value = 15)
	private String agremLmtAmount;
	@FixedString(order = 19, type = MessageFieldType.ALPHABET, value = 8)
	private String gvrnDntRcpmnyDe;
	@FixedString(order = 20, type = MessageFieldType.ALPHABET, value = 8)
	private String locgovBndRcpmnyDe;
	@FixedString(order = 21, type = MessageFieldType.ALPHABET, value = 8)
	private String prvateBndRcpmnyDe;
	@FixedString(order = 22, type = MessageFieldType.ALPHABET, value = 20)
	private String taskId;
	@FixedString(order = 23, type = MessageFieldType.ALPHABET, value = 200)
	private String taskNm;
	@FixedString(order = 24, type = MessageFieldType.ALPHABET, value = 10)
	private String bsnsClCode;
	@FixedString(order = 25, type = MessageFieldType.ALPHABET, value = 100)
	private String bsnsClNm;
	@FixedString(order = 26, type = MessageFieldType.ALPHABET, value = 67)
	private String filler;
}
