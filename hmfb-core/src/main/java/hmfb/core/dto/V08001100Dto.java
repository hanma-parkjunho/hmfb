package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import hmfb.core.service.BaseMessage;
import lombok.Data;

@Data
public class V08001100Dto implements BaseMessage{
    @FixedString(order = 1, type = MessageFieldType.ALPHABET, value = 8)
    private String tranDt;                       // 거래일자
    @FixedString(order = 2, type = MessageFieldType.ALPHABET, value = 3)
    private String mngInfo;                      // 관리정보 (001:개시   002:재 개시   003:종료예고 004:종료   005:장애      006:장애회복 007:TEST-CALL) 
    @FixedString(order = 3, type = MessageFieldType.NUMERIC, value = 1)
    private String holyDiv;                      // 휴일구분
    @FixedString(order = 4, type = MessageFieldType.NUMERIC, value = 7)
    private String safCnt;                       // SAF건수
    @FixedString(order = 5, type = MessageFieldType.ALPHABET, value = 81)
    private String filler;
}
