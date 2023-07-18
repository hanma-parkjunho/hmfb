package hmfb.core.dto;

import hmfb.core.service.BaseMessage;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author USER
 * 업무개시
 */
@Data
@Getter
@Setter
public class T6000101Dto implements BaseMessage{
	
	// AbstractDTO 
	private static final long serialVersionUID = 1L;
	
	// serialVersionUID 
	private String regDate;
	private String telemsgNo;
	private String orgCode; 		// 식별코드
	private String companyCode; 	// 업체코드
	private String bankCode; 		// 은행코드
	private String professCode; 	// 전문코드
	private String BusinessSort; 	// 업무구분
	private String responseCnt; 	// 송신횟수
	private String professNum; 		// 전문번호
	private String requestDate; 	// 전송일자
	private String requestTime; 	// 전송시간
	private String replyCode; 		// 응답코드
	private String discernCode; 	// 식별코드2
	private String sdsArea; 		// SDS영역
	private String customerArea; 	// 고객영역
	private String Y2KSort; 		// Y2K구분
	private String bankArea; 		// 은행영역
	private String rspnsMssage; 	// 에러
	
}
