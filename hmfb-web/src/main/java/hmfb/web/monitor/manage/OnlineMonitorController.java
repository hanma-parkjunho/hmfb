package hmfb.web.monitor.manage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import hmfb.core.dto.BatchMonitorPageDTO;
import hmfb.core.exception.HmfbException;
import hmfb.core.pagination.PaginationInfo;

@Controller
public class OnlineMonitorController {
	
	@Value("${pagination.pageUnit}")
	private int pageUnit; // paging관련 
	
	@Value("${pagination.pageSize}")
	private int pageSize; // paging관련 
	
//	@Autowired
//	BatchAdminService batchAdminService;
	
	@RequestMapping("monitorOnline")
	public String monitorOnline(@ModelAttribute("batchMonitorPageDto") BatchMonitorPageDTO batchMonitorPageDto, Model model) throws HmfbException {	
//		날짜 검색 조건이 비어있으면 오늘 날자로 설정 
		if (!StringUtils.hasText(batchMonitorPageDto.getSearchTrxDt())) {
			batchMonitorPageDto.setSearchTrxDt(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		}
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(ObjectUtils.isEmpty(batchMonitorPageDto.getPageIndex()) ? 1 : batchMonitorPageDto.getPageIndex());
		paginationInfo.setRecordCountPerPage(ObjectUtils.isEmpty(batchMonitorPageDto.getPageUnit()) ? pageUnit : batchMonitorPageDto.getPageUnit());
		paginationInfo.setPageSize(ObjectUtils.isEmpty(batchMonitorPageDto.getPageSize()) ? pageSize : batchMonitorPageDto.getPageSize());
		
		
		batchMonitorPageDto.setFirstIndex(paginationInfo.getCurrentRecordIndex());
		batchMonitorPageDto.setLastIndex(paginationInfo.getNextRecordIndex());
		batchMonitorPageDto.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		model.addAttribute("condition", batchMonitorPageDto);
		
        return "monitor/monitorOnline";
    }
	
}
