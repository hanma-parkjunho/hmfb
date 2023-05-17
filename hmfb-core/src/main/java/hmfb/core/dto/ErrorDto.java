package hmfb.core.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**

 * @FileName : ErrorDto.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : 오류 데이터 처리 클레스

 * @변경이력 :

 */

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Data
public class ErrorDto {
	
	private int status;
	
	private String path;
	
	private String error;
	
	private String message;
	
	private String errorDt;
	
	private String errorTm;
	
}
