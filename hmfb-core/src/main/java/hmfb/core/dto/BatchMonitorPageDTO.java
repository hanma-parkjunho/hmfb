package hmfb.core.dto;

import lombok.Data;

@Data
public class BatchMonitorPageDTO extends AbstractPageDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3410480582534667446L;
	/** 검색 거래일자 : 필수 */
	String searchTrxDt;
	/** 검색 거래명 : 거래명 조건이 입력안될 경우 empty string 이어야 함. */
	String searchTrxName	= "";
	
	/** 거래명 */
	private String trxName;
	/** 총건수 */
	private int totalCount;
	/** 성공건수 */
	private int successCount;
	/** 실패건수 */
	private int failCount;
	
}
