package hmfb.core.dto;

import hmfb.core.utils.DateUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatchExecutionDTO {	
	
	private static final long serialVersionUID = -804689695154238222L;
	
	private String jobUuid;
	/** 배치 식별자 */
	private String jobCode;
	/** 배치 실행 ID */
	private long jobExecutionId;

	/** 배치 작업년월일 */
	private String jobYmd;
	/** 실행 파라미터 */
	private String runParam;
	/** 시작일시 */
	private String startDtm;
	/** 종료일시 */
	private String endDtm;
	/** 상태 코드 */
	private String procStatus;
	/** 에러 메시지 */
	private String exitMessage;
	/** 실행 서버명 */
	private String processServerName;
	/** 실행 WAS ID */
	private String processWasId;
	/** 전체 건수 */
	private int totalCount;
	/** 완료건수 */
	private int completedCount;
	/** 커밋 단위 건수 */
	private int	commitCount;	 
	/** 입력데이터 지사자(DB: sql-Id / file : 상대경로)  */
	private String inputDataSelector;
	/** 출력데이터 지사자(DB: sql-Id / file : 상대경로)  */
	private String outputDataSelector;	
	/** N, S(skipException) */
	private String exceptionPolicy;   
	
	private String registerDtm;
	private String registerUserId;
	private String modifyDtm;
	private String modifyUserId;
	
	public BatchExecutionDTO() {
		
		this.registerDtm = DateUtil.getCurrentDttm();
		this.modifyDtm = DateUtil.getCurrentDttm();
	}
}
