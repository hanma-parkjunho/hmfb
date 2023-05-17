package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import hmfb.core.service.BaseMessage;
import lombok.Data;

@Data
public class TcpHeader implements BaseMessage {
    @FixedString(order = 1, type = MessageFieldType.NUMERIC, value = 4)
    private String totLength;
    @FixedString(order = 2, type = MessageFieldType.ALPHABET, value = 6)
    private String bizDvcd;
    @FixedString(order = 3, type = MessageFieldType.ALPHABET, value = 2)
    private String legthDvcd;
    @FixedString(order = 4, type = MessageFieldType.ALPHABET, value = 8)
    private String blank;
}
