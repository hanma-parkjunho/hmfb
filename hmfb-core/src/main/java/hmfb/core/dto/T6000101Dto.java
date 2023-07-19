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
	private String telemsgNo;      // 전문번호   
	private String inqireAcnutNo;  // 조회계좌번호
	private String rlnmNo;	       // 실명번호   
	private String dpstrNm;	       // 예금주명   
	private String bankCode;       // 은행코드   
	private String rlnmCmpr;       // 실명비교   
	
	/* 테이블에 추가안함 [s] */
	private String sendCode;			// 전송여부
	private String sendDt;				// 전송날짜
	private String sendTm;				// 전송시간
	/* 테이블에 추가안함  [e] */
	
	private String rspnsCode;			// 응답코드
	private String rspnsMssage;			// 응답메세지
	
	/* 테이블에 추가안함 [s] */
	private String recvDt;				// 수신날짜
	private String recvTm;				// 수신시간
	/* 테이블에 추가안함 [e] */
	
}
