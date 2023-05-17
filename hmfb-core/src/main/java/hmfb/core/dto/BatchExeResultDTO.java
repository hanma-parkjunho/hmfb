package hmfb.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BatchExeResultDTO extends AbstractDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2714021238749061027L;
	
	private long jobExecutionId;
	private long instanceId;
	private String jobCode;
	private String exitCode;
	private String exitDescription;
	/*
	 * org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: 
	 * Cannot construct instance of `com.unchart.springbank.framework.model.BatchExeResultDTO` 
	 * (although at least one Creator exists): cannot deserialize from Object value 
	 * (no delegate- or property-based Creator);
	 * 디폴트 생성자 필요. 
	 
	public BatchExeResultDTO() {
	}
	*/
	public BatchExeResultDTO(String jobCode) {
		this.jobCode = jobCode;
	}
}
