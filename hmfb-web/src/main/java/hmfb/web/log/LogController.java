package hmfb.web.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import hmfb.core.dto.PrgmDto;
import hmfb.core.exception.HmfbException;
import hmfb.core.pagination.PaginationInfo;
import hmfb.core.prop.LogProperties;
import hmfb.core.utils.DateUtil;
import hmfb.core.utils.StringUtil;
import hmfb.web.log.service.LogServiceImpl;
import hmfb.web.server.prop.ServerInfoProperties;

/**

 * @FileName : LogController.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : LogController 클레스 

 * @변경이력 :

 */

@Controller
public class LogController {
	
	@Value("${pagination.pageUnit}")
	private int pageUnit;
	
	@Value("${pagination.pageSize}")
	private int pageSize;
	
	@Autowired
	private LogServiceImpl logService;
	
	@Autowired
	private LogProperties logProperties;  
	
	@Autowired
	private ServerInfoProperties serverProps;
	
	/**
     
	 * @Method Name : userInfo
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 연계환경관리 - 로그정보 페이지 호출
   
     * @변경이력 : 
   
     */
	@RequestMapping("/log/logInfo")
    public String userInfo(@ModelAttribute("prgmDto") PrgmDto prgmDto, Model model) throws HmfbException {	
		
		String currentDt = DateUtil.getCurrentDttm().substring(0, 8);
		
		if(StringUtil.isEmpty(prgmDto.getSearchStDt()))
			prgmDto.setSearchStDt(DateUtil.formatDate(DateUtil.addDay(currentDt, -7), "."));
		
		if(StringUtil.isEmpty(prgmDto.getSearchEdDt()))
			prgmDto.setSearchEdDt(DateUtil.formatDate(currentDt, "."));
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(ObjectUtils.isEmpty(prgmDto.getPageIndex()) ? 1 : prgmDto.getPageIndex());
		paginationInfo.setRecordCountPerPage(ObjectUtils.isEmpty(prgmDto.getPageUnit()) ? pageUnit : prgmDto.getPageUnit());
		paginationInfo.setPageSize(ObjectUtils.isEmpty(prgmDto.getPageSize()) ? pageSize : prgmDto.getPageSize());
		
		if("Y".equals(logProperties.getDbLog())) {
			
			paginationInfo.setTotalRecordCount(logService.getlogTotCnt(prgmDto));
			prgmDto.setFirstIndex(paginationInfo.getCurrentRecordIndex());
			prgmDto.setLastIndex(paginationInfo.getNextRecordIndex());
			prgmDto.setPageIndex(paginationInfo.getCurrentPageNo());
			prgmDto.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
			model.addAttribute("logList", logService.getlogList(prgmDto));
			model.addAttribute("prgmIdList", logService.getPrgmIdList(prgmDto));
		} else if(!"Y".equals(logProperties.getDbLog()) && "Y".equals(logProperties.getFileLog())) {
			model.addAttribute("logList", logService.getlogFileList(prgmDto, paginationInfo).getContent());
		}
		
		model.addAttribute("searchPrgmDto", prgmDto);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("sysList", serverProps.getServerInfoList());
		model.addAttribute("dbLog", logProperties.getDbLog());
		model.addAttribute("fileLog", logProperties.getFileLog());
		
        return "log/logInfo";
    }
	
}
