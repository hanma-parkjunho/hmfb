package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import hmfb.core.service.BaseMessage;
import lombok.Data;

@Data
public class FirmCommonDto implements BaseMessage{
    @FixedString(order = 1, type = MessageFieldType.ALPHABET, value = 9)
    private String distinCode;
    @FixedString(order = 2, type = MessageFieldType.ALPHABET, value = 8)
    private String firmId;
    @FixedString(order = 3, type = MessageFieldType.ALPHABET, value = 1)
    private String sndRcvDvcd;
    @FixedString(order = 4, type = MessageFieldType.NUMERIC, value = 3)
    private String bnkCode;
    @FixedString(order = 5, type = MessageFieldType.NUMERIC, value = 4)
    private String tlgmCode;
    @FixedString(order = 6, type = MessageFieldType.NUMERIC, value = 3)
    private String bizCode;
    @FixedString(order = 7, type = MessageFieldType.NUMERIC, value = 6)
    private String tlgmSeqNo;
    @FixedString(order = 8, type = MessageFieldType.ALPHABET, value = 8)
    private String tranDt;
    @FixedString(order = 9, type = MessageFieldType.ALPHABET, value = 6)
    private String tranTm;
    @FixedString(order = 10, type = MessageFieldType.ALPHABET, value = 4)
    private String recvCode;
    @FixedString(order = 11, type = MessageFieldType.ALPHABET, value = 48)
    private String dummyArea;
}
