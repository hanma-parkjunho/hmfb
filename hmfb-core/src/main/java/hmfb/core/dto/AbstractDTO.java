package hmfb.core.dto;

import java.io.Serializable;

import hmfb.core.utils.DateUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AbstractDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6668135321375430606L;
	
	public AbstractDTO() {

		String dttm = DateUtil.getCurrentDttm();

		this.regDt = dttm.substring(0, 8);
		this.regTm = dttm.substring(8);
		this.updDt = dttm.substring(0, 8);
		this.updTm = dttm.substring(8);
	}
	/** 등록일자 */
	private String regDt;
	/** 등록시각 */
	private String regTm;
	/** 등록사용자 ID */
	private String regUserId;	
	/** 변경일자 */
	private String updDt;
	/** 변경시각 */
	private String updTm;
	/** 변경사용자 ID */
	private String updUserId;
	
}
