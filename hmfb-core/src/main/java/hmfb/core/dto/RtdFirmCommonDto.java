package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import hmfb.core.service.BaseMessage;
import lombok.Data;

@Data
public class RtdFirmCommonDto implements BaseMessage{
    @FixedString(order = 1, type = MessageFieldType.ALPHABET, value = 9)
    private String distinCode; 		// 식별코드
    @FixedString(order = 2, type = MessageFieldType.ALPHABET, value = 12)
    private String firmId; 			// 업체코드
    @FixedString(order = 3, type = MessageFieldType.NUMERIC, value = 2)
    private String bnkCode; 		// 은행코드
    @FixedString(order = 4, type = MessageFieldType.ALPHABET, value = 4)
    private String tlgmCode; 		// 전문코드
    @FixedString(order = 5, type = MessageFieldType.ALPHABET, value = 3)
    private String sndRcvDvcd; 		// 업무구분
    @FixedString(order = 6, type = MessageFieldType.NUMERIC, value = 1)
    private String sendCnt; 		// 송신횟수
    @FixedString(order = 7, type = MessageFieldType.NUMERIC, value = 6)
    private String tlgmSeqNo; 		// 전문번호
    @FixedString(order = 8, type = MessageFieldType.NUMERIC, value = 8)
    private String tranTm; 			// 전송일자
    @FixedString(order = 9, type = MessageFieldType.NUMERIC, value = 6)
    private String tranDt; 			// 전송시간
    @FixedString(order = 10, type = MessageFieldType.ALPHABET, value = 4)
    private String bizCode; 		// 응답코드
    @FixedString(order = 11, type = MessageFieldType.ALPHABET, value = 9)
    private String recvCode; 		// 식별코드2
    @FixedString(order = 12, type = MessageFieldType.ALPHABET, value = 15)
    private String dummyArea; 		// SDS영역
    @FixedString(order = 13, type = MessageFieldType.ALPHABET, value = 11)
    private String clientArea; 		// 고객영역
    @FixedString(order = 14, type = MessageFieldType.ALPHABET, value = 1)
    private String y2kArea; 		// y2k구분
    @FixedString(order = 15, type = MessageFieldType.ALPHABET, value = 9)
    private String bnkArea; 		// 은행영역
}
