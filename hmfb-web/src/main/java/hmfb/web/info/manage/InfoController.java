package hmfb.web.info.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import hmfb.core.dto.InstDto;
import hmfb.core.dto.LinkDto;
import hmfb.core.exception.HmfbException;
import hmfb.core.pagination.PaginationInfo;
import hmfb.web.info.service.InfoAdminService;

@Controller
public class InfoController {
	
	@Value("${pagination.pageUnit}")
	private int pageUnit;
	
	@Value("${pagination.pageSize}")
	private int pageSize;
	
	@Autowired 
	InfoAdminService infoService;
	
	@RequestMapping("instInfo")
    public String instInfo(@ModelAttribute("instDto") InstDto instDto, Model model) throws HmfbException {	
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(ObjectUtils.isEmpty(instDto.getPageIndex()) ? 1 : instDto.getPageIndex());
		paginationInfo.setRecordCountPerPage(ObjectUtils.isEmpty(instDto.getPageUnit()) ? pageUnit : instDto.getPageUnit());
		paginationInfo.setPageSize(ObjectUtils.isEmpty(instDto.getPageSize()) ? pageSize : instDto.getPageSize());
		paginationInfo.setTotalRecordCount(infoService.getInstInfoTotCnt(instDto));
		
		instDto.setFirstIndex(paginationInfo.getCurrentRecordIndex());
		instDto.setLastIndex(paginationInfo.getNextRecordIndex());
		instDto.setPageIndex(paginationInfo.getCurrentPageNo());
		instDto.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		model.addAttribute("instInfoList", infoService.getInstInfoList(instDto));
		model.addAttribute("paginationInfo", paginationInfo);

        return "info/instInfo";
    }
	
	@RequestMapping("linkInfo")
    public String linkInfo(@ModelAttribute("linkDto") LinkDto linkDto, Model model) throws HmfbException {	
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(ObjectUtils.isEmpty(linkDto.getPageIndex()) ? 1 : linkDto.getPageIndex());
		paginationInfo.setRecordCountPerPage(ObjectUtils.isEmpty(linkDto.getPageUnit()) ? pageUnit : linkDto.getPageUnit());
		paginationInfo.setPageSize(ObjectUtils.isEmpty(linkDto.getPageSize()) ? pageSize : linkDto.getPageSize());
		paginationInfo.setTotalRecordCount(infoService.getLinkInfoTotCnt(linkDto));
		
		linkDto.setFirstIndex(paginationInfo.getCurrentRecordIndex());
		linkDto.setLastIndex(paginationInfo.getNextRecordIndex());
		linkDto.setPageIndex(paginationInfo.getCurrentPageNo());
		linkDto.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		model.addAttribute("linkInfoList", infoService.getLinkInfoList(linkDto));
		model.addAttribute("paginationInfo", paginationInfo);

        return "info/linkInfo";
    }
	
	@RequestMapping("firmInfo")
    public String firmInfo(Model model) throws HmfbException {
		
		 return "info/firmInfo";
	}
	
	@RequestMapping("virtInfo")
    public String virtInfo(Model model) throws HmfbException {
		
		 return "info/virtInfo";
	}
}
