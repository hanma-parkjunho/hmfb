package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import hmfb.core.service.BaseMessage;
import lombok.Data;

/**
 * @author USER
 * 당,타행예금주성명조회
 */
@Data
public class F6000101Dto implements BaseMessage{
	// 공통부
	@FixedString(order = 1, type = MessageFieldType.ALPHABET, value = 9)
	private String orgCode; // 식별코드1
	@FixedString(order = 2, type = MessageFieldType.ALPHABET, value = 12)
	private String companyCode; // 업체코드
	@FixedString(order = 3, type = MessageFieldType.NUMERIC, value = 2)
	private String bankCode; // 은행코드
	@FixedString(order = 4, type = MessageFieldType.ALPHABET, value = 4)
	private String professCode; // 전문코드
	@FixedString(order = 5, type = MessageFieldType.ALPHABET, value = 3)
	private String BusinessSort; // 업무구분
	@FixedString(order = 6, type = MessageFieldType.NUMERIC, value = 1)
	private String responseCnt; // 송신횟수
	@FixedString(order = 7, type = MessageFieldType.NUMERIC, value = 6)
	private String professNum; // 전문번호
	@FixedString(order = 8, type = MessageFieldType.NUMERIC, value = 8)
	private String requestDate; // 전송일자
	@FixedString(order = 9, type = MessageFieldType.NUMERIC, value = 6)
	private String requestTime; // 전송시간
	@FixedString(order = 10, type = MessageFieldType.ALPHABET, value = 4)
	private String replyCode; // 응답코드
	@FixedString(order = 11, type = MessageFieldType.ALPHABET, value = 9)
	private String discernCode; // 식별코드2
	@FixedString(order = 12, type = MessageFieldType.ALPHABET, value = 15)
	private String sdsArea; // SDS영역
	@FixedString(order = 13, type = MessageFieldType.ALPHABET, value = 11)
	private String customerArea; // 고객영역
	@FixedString(order = 14, type = MessageFieldType.ALPHABET, value = 1)
	private String Y2KSort; // Y2K구분
	@FixedString(order = 15, type = MessageFieldType.ALPHABET, value = 9)
	private String bankArea; // 은행영역
	
//	개별부?	
//	@FixedString(order = 16, type = MessageFieldType.ALPHABET, value = 15)
//	private String pamBanking;
}
