package hmfb.core.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AbstractPageDTO implements Serializable {

	private static final long serialVersionUID = -4421359409501981733L;
	
	private Integer pageIndex;
	private Integer pageUnit;
	private Integer pageSize;
    private Integer firstIndex;
    private Integer lastIndex;
    private Integer recordCountPerPage;
    
}
