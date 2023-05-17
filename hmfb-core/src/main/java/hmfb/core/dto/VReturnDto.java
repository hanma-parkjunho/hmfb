package hmfb.core.dto;

import hmfb.core.service.BaseMessage;
import lombok.Data;

@Data
public class VReturnDto {

	private VirtualCommonDto commonDto;
	private BaseMessage rtnObj;
}
