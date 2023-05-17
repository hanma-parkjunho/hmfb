package hmfb.core.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 디폴트 값 <br/>
 * fileEncoding : UTF-8
 * fileType       : delimeter 방식
 * delimeter     : comma(CSV)
 * 업무 배치 preProcess() 에서 필수로 설정할 값
 * inputClassName
 * inputAttributeNames
 * outputAttributeNames
 * 
 * @author KDK
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class FileIOMetaDTO extends AbstractDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 336604928220404729L;

	private String fileEncoding = "UTF-8";
	/**  파일 유형 : FIXED, DELIM */
	private String fileType = "DELIM";	// recursive ref. 회피를 위해 하드코딩. BatchConstants.FILE_TYPE_DELIM;
	
	/*********************************************
	 * 			ItemReader 에서 사용할 정보 
	 *********************************************/
	/** 파일에서 읽은 값을 넣을 DTO 명을 full name(package 명 포함) 으로 설정 */
	private String inputClassName	= "";
	/** inputClass 의 인스턴스 변수 명 */
	private String[] inputAttributeNames	= null;
	
	private int headerRows = 0;

	/*********************************************
	 * 			ItemWriter 에서 사용할 정보 
	 *********************************************/
	/** 출력될 인스턴스 변수 명을 출력 순서대로 나열  */
	private String[] outputAttributeNames  = null;

	/*********************************************
	 * 기타  
	 *********************************************/
	/** delimeter 를 사용한 파일의 입력/출력 시 사용 (CSV 는 comma, ). default is comma */
	private String delimeter = ",";	// BatchConstants.DELIM_COMMA;
	/** 
	 * FixedLength 파일의 입력/출력 시 사용.
	 * 
	 * startIndex: 각 item 의 위치 시작 인덱스. base 1. 
	 * endIndex : 각 item 의 위치 종료 인덱스
	 */
	private int[][] inputFixedLenInfo = null;
	
	/** flat file 에 라인을 출력 시 포맷 */
	private String outputLineFormat = ""; // "%-5s%-09d%20s"
	
	/** 출력 시 padding : default is space */
	private String padding	= " ";	// BatchConstants.SPACE;
}
