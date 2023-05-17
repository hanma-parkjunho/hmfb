package hmfb.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BatchExeParamDTO extends AbstractDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3530551996331492420L;
	/** 배치 실행 식별자 */
	private String guid;
	/** 배치 작업년월일 */
	private String jobYmd;
	/** 배치 식별자 */
	private String jobCode;
	/** 결과 반환 여부 */
	private String returnYn;
	/** 실행 파라미터. 복수 개일 경우 쌍따옴표로 전체 묶어야 함  */
	private String runParam;	// 실행파라미터 
	
	/*******************************************************
	 * 		배치 실행 제어 
	 *******************************************************/
	/** execution id : 배치 실행 이력에 대한 key */
	private long executionId;
}
