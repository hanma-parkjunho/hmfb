package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
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
public class T2100101Dto implements BaseMessage{

	private String telemsgNo;			// 전문번호
	private String pymentAcunt; 		// 지급계좌
	private String password; 			// 비밀번호
	private Integer revwSmbol; 			// 복기부호
	private String pymntMount; 			// 지급금액
	private String smbol; 				// 부호
	private Integer blce; 				// 잔액
	private Integer bankCode; 			// 은행코드
	private Integer rcpmnyAcnut; 		// 입금계좌
	private Integer fee; 				// 수수료
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
	private String rspnsCode; 			// 응답코드
	private String rspnsMssage; 		// 응답메세지
	private String sendCode;			// 이체전송여부
	
}
