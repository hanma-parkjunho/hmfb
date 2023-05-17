package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import hmfb.core.service.BaseMessage;
import lombok.Data;

/**
 * @author USER
 * 거래명세통지
 */
@Data
public class F0600100Dto implements BaseMessage{
//    @FixedString(order = 1, type = MessageFieldType.ALPHABET, value = 100, grpYn = "Y", grpName = "hmfb.core.dto.FirmCommonDto")
//    private FirmCommonDto commonDto;
	@FixedString(order = 1, type = MessageFieldType.ALPHABET, value = 8)
	private String delngDe;
	@FixedString(order = 2, type = MessageFieldType.NUMERIC, value = 6)
	private String delngSn;
	@FixedString(order = 3, type = MessageFieldType.ALPHABET, value = 15)
	private String acnutNo;
	@FixedString(order = 4, type = MessageFieldType.ALPHABET, value = 2)
	private String seCode;
	@FixedString(order = 5, type = MessageFieldType.NUMERIC, value = 13)
	private String delngAmount;
	@FixedString(order = 6, type = MessageFieldType.ALPHABET, value = 1)
	private String blceSmbol;
	@FixedString(order = 7, type = MessageFieldType.NUMERIC, value = 13)
	private String blce;
	@FixedString(order = 8, type = MessageFieldType.ALPHABET, value = 20)
	private String delngSumry;
	@FixedString(order = 9, type = MessageFieldType.ALPHABET, value = 6)
	private String delngTime;
	@FixedString(order = 10, type = MessageFieldType.NUMERIC, value = 3)
	private String bankCode;
	@FixedString(order = 11, type = MessageFieldType.ALPHABET, value = 7)
	private String delngBhfCode;
	@FixedString(order = 12, type = MessageFieldType.ALPHABET, value = 20)
	private String cmsCode;
	@FixedString(order = 13, type = MessageFieldType.ALPHABET, value = 10)
	private String checkBilNo;
	@FixedString(order = 14, type = MessageFieldType.NUMERIC, value = 13)
	private String csrccAmount;
	@FixedString(order = 15, type = MessageFieldType.NUMERIC, value = 13)
	private String prsnlchkAmount;
	@FixedString(order = 16, type = MessageFieldType.NUMERIC, value = 13)
	private String genchkAmount;
	@FixedString(order = 17, type = MessageFieldType.ALPHABET, value = 2)
	private String delngMediaTy;
	@FixedString(order = 18, type = MessageFieldType.ALPHABET, value = 135)
	private String filler;
}
