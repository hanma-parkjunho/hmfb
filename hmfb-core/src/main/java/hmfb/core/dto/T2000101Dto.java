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
public class T2000101Dto implements BaseMessage{
	
	private static final long serialVersionUID = 1L;
	
	private String telemsgNo;			// 전문번호
	private String pymentAcunt; 		// 지급계좌
	private String password; 			// 비밀번호
	private String revwSmbol; 			// 복기부호
	private String pymntMount; 			// 지급금액
	private String smbol; 				// 부호
	
	private String blce; 				// 잔액
	private String bankCode; 			// 은행코드
	private String rcpmnyAcnut; 		// 입금계좌
	private String fee; 				// 수수료
	private String cmsCode; 			// CMS코드
	private String rcpmnyAcnutPrntxt;	// 입금계좌인자내용
	private String rlnmNo; 				// 실명번호
	private String dpstrNm; 			// 예금주명
	private String cptalCharct; 		// 자금성격
	
	private String filler1; 			// 예비1
	private String filler2; 			// 예비2
	private String filler3; 			// 예비3
	private String filler4; 			// 예비4
	private String filler5; 			// 예비5	
	
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
