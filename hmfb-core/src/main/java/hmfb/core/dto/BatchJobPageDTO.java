package hmfb.core.dto;

import lombok.Data;

@Data
public class BatchJobPageDTO extends AbstractPageDTO {
	
	private static final long serialVersionUID = -4572140524174013671L;
	
	/** 배치 식별자 */
	private String jobCode;
	/** 배치 설명 */
	private String jobDesc;
	/** 배치 유형 : F2D, D2F, D2D, CTM(custom batch) */
	private String jobType;		
	/** 입력데이터 지사자(DB: sql-Id / file : 상대경로)  */
	private String inputDataSelector;
	/** cron expression */
	private String cronExpression; 
	/** 사용여부 (Y:사용, N:사용안함) */
	private String useYn;
}
