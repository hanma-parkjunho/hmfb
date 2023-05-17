package hmfb.web.batch.manage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import hmfb.core.dto.BatchJobPageDTO;
import hmfb.core.dto.BatchMonitorPageDTO;
import hmfb.core.exception.HmfbException;
import hmfb.core.pagination.PaginationInfo;
import hmfb.web.batch.service.BatchAdminService;

@Controller
public class BatchController {
	
	@Value("${pagination.pageUnit}")
	private int pageUnit;
	
	@Value("${pagination.pageSize}")
	private int pageSize;
	
	@Autowired
	BatchAdminService batchAdminService;
	
	@RequestMapping("batchJobsInfo")
    public String batchJobsInfo(@ModelAttribute("batchJobPageDto") BatchJobPageDTO batchJobPageDto, Model model) throws HmfbException {	
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(ObjectUtils.isEmpty(batchJobPageDto.getPageIndex()) ? 1 : batchJobPageDto.getPageIndex());
		paginationInfo.setRecordCountPerPage(ObjectUtils.isEmpty(batchJobPageDto.getPageUnit()) ? pageUnit : batchJobPageDto.getPageUnit());
		paginationInfo.setPageSize(ObjectUtils.isEmpty(batchJobPageDto.getPageSize()) ? pageSize : batchJobPageDto.getPageSize());
		paginationInfo.setTotalRecordCount(batchAdminService.getBatchJobTotCnt(batchJobPageDto));
		
		batchJobPageDto.setFirstIndex(paginationInfo.getCurrentRecordIndex());
		batchJobPageDto.setLastIndex(paginationInfo.getNextRecordIndex());
		batchJobPageDto.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		model.addAttribute("batchJobList", batchAdminService.getBatchJobList(batchJobPageDto));
		model.addAttribute("paginationInfo", paginationInfo);

        return "batch/batchJobsInfo";
    }
		
	@RequestMapping("monitorBatchJobs")
    public String monitorBatchJobs(@ModelAttribute("batchMonitorPageDto") BatchMonitorPageDTO batchMonitorPageDto, Model model) throws HmfbException {	
//		날짜 검색 조건이 비어있으면 오늘 날자로 설정 
		if (!StringUtils.hasText(batchMonitorPageDto.getSearchTrxDt())) {
			batchMonitorPageDto.setSearchTrxDt(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		}
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(ObjectUtils.isEmpty(batchMonitorPageDto.getPageIndex()) ? 1 : batchMonitorPageDto.getPageIndex());
		paginationInfo.setRecordCountPerPage(ObjectUtils.isEmpty(batchMonitorPageDto.getPageUnit()) ? pageUnit : batchMonitorPageDto.getPageUnit());
		paginationInfo.setPageSize(ObjectUtils.isEmpty(batchMonitorPageDto.getPageSize()) ? pageSize : batchMonitorPageDto.getPageSize());
		int totalRecordCount = batchAdminService.getBatchExecutionTotCnt(batchMonitorPageDto);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		batchMonitorPageDto.setFirstIndex(paginationInfo.getCurrentRecordIndex());
		batchMonitorPageDto.setLastIndex(paginationInfo.getNextRecordIndex());
		batchMonitorPageDto.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		model.addAttribute("condition", batchMonitorPageDto);
		if (totalRecordCount == 0) {
			model.addAttribute("executionList", new ArrayList<BatchMonitorPageDTO>());
		} else {
			model.addAttribute("executionList", batchAdminService.getBatchMonitorList(batchMonitorPageDto));
		}
        return "batch/monitorBatchJobs";
    }
}
