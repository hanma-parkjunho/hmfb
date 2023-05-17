package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import hmfb.core.service.BaseMessage;
import lombok.Data;

@Data
public class F0400100Dto implements BaseMessage{
	@FixedString(order = 1, type = MessageFieldType.ALPHABET, value = 8)
	private String oridelngDe;
	@FixedString(order = 2, type = MessageFieldType.NUMERIC, value = 6)
	private String oritelemsgNo;
	@FixedString(order = 3, type = MessageFieldType.ALPHABET, value = 1)
	private String processResultInqireSe;
	@FixedString(order = 4, type = MessageFieldType.ALPHABET, value = 4)
	private String oridelngRspnsCode;
	@FixedString(order = 5, type = MessageFieldType.ALPHABET, value = 6)
	private String oridelngProcessTime;
	@FixedString(order = 6, type = MessageFieldType.ALPHABET, value = 15)
	private String defrayAcnutNo;
	@FixedString(order = 7, type = MessageFieldType.ALPHABET, value = 15)
	private String virtlAcnutNo;
	@FixedString(order = 8, type = MessageFieldType.NUMERIC, value = 13)
	private String delngAmount;
	@FixedString(order = 9, type = MessageFieldType.ALPHABET, value = 1)
	private String blceSmbol;
	@FixedString(order = 10, type = MessageFieldType.NUMERIC, value = 13)
	private String blce;
	@FixedString(order = 11, type = MessageFieldType.ALPHABET, value = 15)
	private String thrghAcnutNo1;
	@FixedString(order = 12, type = MessageFieldType.ALPHABET, value = 15)
	private String thrghAcnutNo2;
	@FixedString(order = 13, type = MessageFieldType.NUMERIC, value = 3)
	private String bankCode;
	@FixedString(order = 14, type = MessageFieldType.ALPHABET, value = 15)
	private String acnutNo;
	@FixedString(order = 15, type = MessageFieldType.ALPHABET, value = 3)
	private String irSafSe;
	@FixedString(order = 16, type = MessageFieldType.NUMERIC, value = 9)
	private String fee;
	@FixedString(order = 17, type = MessageFieldType.ALPHABET, value = 20)
	private String cmsCode;
	@FixedString(order = 18, type = MessageFieldType.ALPHABET, value = 14)
	private String defrayAcnutPrntxt;
	@FixedString(order = 19, type = MessageFieldType.ALPHABET, value = 14)
	private String thrghAcnut1RcpmnyPrntxt;
	@FixedString(order = 20, type = MessageFieldType.ALPHABET, value = 14)
	private String thrghAcnut1DefrayPrntxt;
	@FixedString(order = 21, type = MessageFieldType.ALPHABET, value = 14)
	private String thrghAcnut2RcpmnyPrntxt;
	@FixedString(order = 22, type = MessageFieldType.ALPHABET, value = 14)
	private String thrghAcnut2DefrayPrntxt;
	@FixedString(order = 23, type = MessageFieldType.ALPHABET, value = 14)
	private String rcpmnyAcnutPrntxt;
	@FixedString(order = 24, type = MessageFieldType.ALPHABET, value = 13)
	private String cinetDelngInnb;
	@FixedString(order = 25, type = MessageFieldType.ALPHABET, value = 41)
	private String filler;
}
