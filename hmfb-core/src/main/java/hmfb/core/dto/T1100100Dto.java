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
public class T1100100Dto implements BaseMessage{

	private String jobDe;            // 업무일자
	private String jobSe;            // 업무구분 01:개시, 02:종료
	private String telemsgNo;        // 전문번호
	private String telemsgSendTm;    // 전문전송일자
	private String telemsgSendSe;    // 전송구분 01:전송대기, 02:전송완료, 03:응답완료
	private String rspnsCode;        // 전문응답코드
	private String rspnsMssage;      // 응답메시지
	
}
