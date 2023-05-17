package hmfb.core.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AccountDDTO extends AbstractDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String accountNo;
	
	private String productCode;

	private String cifNo;

	private BigDecimal balance	= BigDecimal.ZERO;

	private String openDate;
	
	private String status;
}
