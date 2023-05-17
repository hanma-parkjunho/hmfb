package hmfb.web.monitor.manage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import hmfb.core.dto.MonitorPageDTO;
import hmfb.core.exception.HmfbException;
import hmfb.core.pagination.PaginationInfo;

@Controller
public class RealMonitorController {
	
	@Value("${pagination.pageUnit}")
	private int pageUnit;
	
	@Value("${pagination.pageSize}")
	private int pageSize; 
	
//	@Autowired
//	MonitorAdminService monitorAdminService;
	
	@RequestMapping("monitorReal")
	public String monitorReal(@ModelAttribute("MonitorPageDTO") MonitorPageDTO batchMonitorPageDto, Model model) throws HmfbException {		
		//	날짜 검색 조건이 비어있으면 오늘 날짜로 설정 
		if (!StringUtils.hasText(batchMonitorPageDto.getSearchTrxDt())) {
			batchMonitorPageDto.setSearchTrxDt(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		}
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setCurrentPageNo(ObjectUtils.isEmpty(batchMonitorPageDto.getPageIndex()) ? 1 : batchMonitorPageDto.getPageIndex());
		paginationInfo.setRecordCountPerPage(ObjectUtils.isEmpty(batchMonitorPageDto.getPageUnit()) ? pageUnit : batchMonitorPageDto.getPageUnit());
		paginationInfo.setPageSize(ObjectUtils.isEmpty(batchMonitorPageDto.getPageSize()) ? pageSize : batchMonitorPageDto.getPageSize());
		
		model.addAttribute("condition", batchMonitorPageDto);
		
        return "monitor/monitorReal";
    }	
}
