package hmfb.core.dto;

import hmfb.core.service.BaseMessage;
import lombok.Data;

@Data
public class FirmReturnDto {

	private FirmCommonDto commonDto;
	private BaseMessage rtnObj;
	
}
