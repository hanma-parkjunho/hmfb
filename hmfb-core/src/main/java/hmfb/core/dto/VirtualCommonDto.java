package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import hmfb.core.service.BaseMessage;
import lombok.Data;

@Data
public class VirtualCommonDto implements BaseMessage{
    @FixedString(order = 1, type = MessageFieldType.NUMERIC, value = 4)
	private String length;             // LENGTH
    @FixedString(order = 2, type = MessageFieldType.ALPHABET, value = 3)
	private String sysId;              // 시스템ID
    @FixedString(order = 3, type = MessageFieldType.ALPHABET, value = 8)
	private String sendOrgCd;          // 전문송수신기관코드
    @FixedString(order = 4, type = MessageFieldType.ALPHABET, value = 1)
	private String sndRcvFlag;         // 송수신FLAG 1: 기관->코어, 2: 코어->기관, 3: 채널->코어, 4: 코어->채널
    @FixedString(order = 5, type = MessageFieldType.NUMERIC, value = 4)
	private String tlgDivCd;           // 전문구분코드(MSG TYPE)
    @FixedString(order = 6, type = MessageFieldType.NUMERIC, value = 4)
	private String tranDivCd;          // 거래구분코드
    @FixedString(order = 7, type = MessageFieldType.NUMERIC, value = 8)
	private String handleOrgCd;        // 취급기관코드 (입금되는 기관의 코드)
    @FixedString(order = 8, type = MessageFieldType.NUMERIC, value = 7)
	private String tranSeqNo;          // 거래일련번호
    @FixedString(order = 9, type = MessageFieldType.NUMERIC, value = 14)
	private String sendDttm;           // 전문전송일시
    @FixedString(order = 10, type = MessageFieldType.NUMERIC, value = 14)
	private String tranDttm;           // 거래개시일시
    @FixedString(order = 11, type = MessageFieldType.NUMERIC, value = 4)
	private String recvCd;             // 응답코드
    @FixedString(order = 12, type = MessageFieldType.ALPHABET, value = 15)
	private String userWorkArea1;
    @FixedString(order = 13, type = MessageFieldType.ALPHABET, value = 15)
	private String userWorkArea2;
    @FixedString(order = 14, type = MessageFieldType.ALPHABET, value = 1)
	private String closeBefAftDvcd;    // 마감전후구분
    @FixedString(order = 15, type = MessageFieldType.ALPHABET, value = 1)
	private String occurDivCd;         // 발생구분
    @FixedString(order = 16, type = MessageFieldType.ALPHABET, value = 1)
	private String mediumDivCd;        // 매체구분
    @FixedString(order = 17, type = MessageFieldType.NUMERIC, value = 8)
	private String orgCd;              // 개설기관코드
    @FixedString(order = 18, type = MessageFieldType.ALPHABET, value = 25)
	private String handleTermCd;       // 취급단말정보
    @FixedString(order = 19, type = MessageFieldType.ALPHABET, value = 50)
	private String retrunMessage;      // 응답Message
    @FixedString(order = 20, type = MessageFieldType.ALPHABET, value = 13)
	private String serialNo;           // 공동망 고유번호
	
}
