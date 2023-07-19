package hmfb.core.dto;

import hmfb.core.service.BaseMessage;
import lombok.Data;

@Data
public class StdFirmReturnDto {

	private StdFirmCommonDto commonDto;
	private BaseMessage rtnObj;
	
}
