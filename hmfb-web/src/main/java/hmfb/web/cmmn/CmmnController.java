package hmfb.web.cmmn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import hmfb.core.cmmn.service.CmmnServiceImpl;
import hmfb.core.dto.CmmnDto;
import hmfb.core.exception.HmfbException;
import hmfb.core.pagination.PaginationInfo;

/**

 * @FileName : LogController.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : LogController 클레스 

 * @변경이력 :

 */

@Controller
public class CmmnController {
	
	@Value("${pagination.pageUnit}")
	private int pageUnit;
	
	@Value("${pagination.pageSize}")
	private int pageSize;
	
	@Autowired
	private CmmnServiceImpl cmmnService;
	
	/**
    
     * @Method Name : cmmnInfo
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 연계환경관리 - 공통코드정보 페이지 호출
   
     * @변경이력 : 
   
     */
	@RequestMapping("/cmmn/cmmnInfo")
    public String cmmnInfo(@ModelAttribute("cmmnDto") CmmnDto cmmnDto, Model model) throws HmfbException {	
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(ObjectUtils.isEmpty(cmmnDto.getPageIndex()) ? 1 : cmmnDto.getPageIndex());
		paginationInfo.setRecordCountPerPage(ObjectUtils.isEmpty(cmmnDto.getPageUnit()) ? pageUnit : cmmnDto.getPageUnit());
		paginationInfo.setPageSize(ObjectUtils.isEmpty(cmmnDto.getPageSize()) ? pageSize : cmmnDto.getPageSize());
		paginationInfo.setTotalRecordCount(cmmnService.getCmmnTotCnt(cmmnDto));
		
		cmmnDto.setFirstIndex(paginationInfo.getCurrentRecordIndex());
		cmmnDto.setLastIndex(paginationInfo.getNextRecordIndex());
		cmmnDto.setPageIndex(paginationInfo.getCurrentPageNo());
		cmmnDto.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		model.addAttribute("searchCmmnDto", cmmnDto);
		model.addAttribute("cmmnList", cmmnService.getCmmnList(cmmnDto));
		model.addAttribute("paginationInfo", paginationInfo);
		
        return "cmmn/cmmnInfo";
    }
	
}
