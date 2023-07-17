package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import hmfb.core.service.BaseMessage;
import lombok.Data;

@Data
public class StdFirmCommonDto implements BaseMessage{
	@FixedString(order = 1, type = MessageFieldType.ALPHABET, value = 9)
    private String distinCode;
    @FixedString(order = 2, type = MessageFieldType.ALPHABET, value = 12)
    private String firmId;
    @FixedString(order = 3, type = MessageFieldType.NUMERIC, value = 2)
    private String bnkCode;
    @FixedString(order = 4, type = MessageFieldType.ALPHABET, value = 4)
    private String tlgmCode;
    @FixedString(order = 5, type = MessageFieldType.ALPHABET, value = 3)
    private String bizCode;	
    @FixedString(order = 6, type = MessageFieldType.NUMERIC, value = 1)
    private String sndRcvDvcd;
    @FixedString(order = 7, type = MessageFieldType.NUMERIC, value = 6)
    private String tlgmSeqNo;	
    @FixedString(order = 8, type = MessageFieldType.NUMERIC, value = 8)
    private String tranDt;
    @FixedString(order = 9, type = MessageFieldType.NUMERIC, value = 6)
    private String tranTm;
    @FixedString(order = 10, type = MessageFieldType.ALPHABET, value = 4)
    private String recvCode;
    @FixedString(order = 11, type = MessageFieldType.ALPHABET, value = 9)
    private String idntfcCode;
    @FixedString(order = 12, type = MessageFieldType.ALPHABET, value = 15)
    private String sdsRelm;
    @FixedString(order = 13, type = MessageFieldType.ALPHABET, value = 11)
    private String cstmrRelm;
    @FixedString(order = 14, type = MessageFieldType.ALPHABET, value = 1)
    private String y2kRelm;
    @FixedString(order = 15, type = MessageFieldType.ALPHABET, value = 9)
    private String bnkRelm;
}
