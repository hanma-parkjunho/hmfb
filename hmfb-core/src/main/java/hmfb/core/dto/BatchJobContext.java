package hmfb.core.dto;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import hmfb.core.exception.ErrorCode;
import hmfb.core.exception.HmfbException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 업무 배치 프로그램에 제공되는 모델 객체 
 * @author KDK
 *
 */
@NoArgsConstructor
@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
/*
 * @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS) 의 사용 :
 * Implementation that uses Jackson2 to provide (de)serialization. 
 * By default, this implementation trusts a limited set of classes to be deserialized from the execution context.
 *  If a class is not trusted by default and is safe to deserialize, you can add it to the base set of trusted classes at construction time 
 *  or provide an explicit mapping using Jackson annotations,
 */
public class BatchJobContext extends AbstractDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7938782887280392763L;
	/*
	 *******************
	 * Batch 기본 정보  
	 *******************
	 */
	/** 배치 식별자 */
	private String jobCode;
	/** 배치 설명 */
	private String jobDesc;
	/** 배치 유형 */
	private String jobType;
	/** 배치 프로그램 명 */
	private String programName;	
	/** 커밋 단위 건수 */
	private int	commitCount;	 
	/** 작업입력데이터 지시자 */
	private String inputDataSelector;
	/** 작업출력데이터 지시자 */
	private String outputDataSelector;
	
	/*
	 *******************
	 * Batch 실행 파라미터 
	 *******************
	 */
	/** 배치 실행 식별자 */
	private String jobUuid;
	/** 배치 작업년월일 */
	private String jobYmd;
	/** 결과 반환 여부 */
	private String returnYn;
	/** 실행 파라미터. 복수 개일 경우 쌍따옴표로 전체 묶어야 함  */
	private String runParam;	// 실행파라미터 
	
	/*
	 *******************
	 * Batch 실행 파라미터 
	 *******************
	 */
	private int totalCount		= 0;
	private int completed		= 0;
	private int currentIndex	= 0;
	
	/** File io */
	private FileIOMetaDTO ioMeta;
	/** DB Condition */
	private Map<String,Object> conditions = new HashMap<String, Object>();
	
	/**
	 * 입력 파일명 변경 시 호출 
	 * @param search
	 * @param replacement
	 */
	public void replaceInputFileName(String search, String replacement) {
		
		if (jobType.charAt(0) != 'F') {			
			throw new RuntimeException(new HmfbException(ErrorCode.E810, String.format("배치기본정보를 확인하세요. 배치유형[%s]",jobType)));
		}
		
		inputDataSelector = StringUtils.replace(inputDataSelector, search, replacement); 
	}
	/**
	 * 출력 파일명 변경 시 호출 
	 * @param search
	 * @param replacement
	 */
	public void replaceOutputFileName(String search, String replacement) {
		if (jobType.charAt(2) != 'F') {			
			throw new RuntimeException(new HmfbException(ErrorCode.E811, String.format("배치기본정보를 확인하세요. 배치유형[%s]",jobType)));
		}
		outputDataSelector = StringUtils.replace(outputDataSelector, search, replacement); 
	}
	/** DB 조회 조건 등록 */
	public void putCondition(String key, Object value) {
		this.conditions.put(key, value);
	}
}
