package hmfb.core.dto;

import hmfb.core.utils.DateUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatchJobDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -804689695154238222L;
	
	/** 배치 식별자 */
	private String jobCode;
	/** 배치 설명 */
	private String jobDesc;
	/** 실행 주기 : D(일별), M(월별), Q(분기별), Y(년별), A(수시) */
	private String jobCycle;	
	/** 배치 유형 : F2D, D2F, D2D, CTM(custom batch) */
	private String jobType;		
	/** 재처리 여부 */
	private String rejobYn;			
	/** 배치 프로그램 명 */
	private String programName;		
	/** 분배. RR, Dedicated  */
	private String distMethod;	
	/** dedicated 방식일 경우 */
	private String processWasId;	
	/** 커밋 단위 건수 */
	private int	commitCount;	 
	/** 입력데이터 지사자(DB: sql-Id / file : 상대경로)  */
	private String inputDataSelector;
	/** 출력데이터 지사자(DB: sql-Id / file : 상대경로)  */
	private String outputDataSelector;
	
	/** N, S(skipException) */
	private String exceptionPolicy;   
	/** 사용여부 (Y:사용, N:사용안함) */
	private String useYn;
	
	private String runParam;
	/** cron expression */
	private String cronExpression; 
	
	private String registerDtm;
	private String registerUserId;
	private String modifyDtm;
	private String modifyUserId;
	
	public BatchJobDTO() {
		
		this.registerDtm = DateUtil.getCurrentDttm();
		this.modifyDtm = DateUtil.getCurrentDttm();
	}
}
