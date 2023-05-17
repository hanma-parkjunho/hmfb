package hmfb.web.info.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hmfb.core.dto.InstDto;
import hmfb.core.dto.LinkDto;
import hmfb.core.exception.ErrorCode;
import hmfb.core.exception.HmfbException;
import hmfb.web.info.service.InfoAdminService;

@RestController
public class InfoRestController {
	
	@Autowired
	InfoAdminService infoAdminService;
	
	/* 기관정보 */
	@PostMapping("/inst/registerInstInfo")
	public ResponseEntity<?> registerInstInfo(@ModelAttribute InstDto input, Model model) throws HmfbException  {
		
		if (input.getInstCd().length() > 8) {
			throw new HmfbException(ErrorCode.E116, "기관코드의 길이는 8자리 이하여야 합니다.");
		}
		
		if (!StringUtils.hasText(input.getInstNm())) {
			throw new HmfbException(ErrorCode.E114, "기관명은 필수 항목입니다.");
		}
		
		if (input.getBizrNo().length() != 10) {
			throw new HmfbException(ErrorCode.E116, "사업자등록번호의 길이는 10자리의 숫자여야 합니다");
		}
		infoAdminService.insertInstInfo(input);
		return ResponseEntity.ok().body(model);
	}
	
	@PostMapping("/inst/getInstInfo")
	public ResponseEntity<?> getInstInfo(@Nullable @RequestParam(required = false) String instCd, Model model) throws HmfbException {

		InstDto output = infoAdminService.selectInstInfo(instCd);
		model.addAttribute("instInfo", output);
		return ResponseEntity.ok().body(model);
	}
	
	@PostMapping("/inst/modifyInstInfo")
	public ResponseEntity<?> modifyInstInfo(@ModelAttribute InstDto input, Model model) throws HmfbException  {
		
		if (input.getInstCd().length() > 8) {
			throw new HmfbException(ErrorCode.E116, "기관코드의 길이는 8자리 이하여야 합니다.");
		}
		
		if (!StringUtils.hasText(input.getInstNm())) {
			throw new HmfbException(ErrorCode.E114, "기관명은 필수 항목입니다.");
		}
		
		if (input.getBizrNo().length() != 10) {
			throw new HmfbException(ErrorCode.E116, "사업자등록번호의 길이는 10자리의 숫자여야 합니다");
		}
		
		infoAdminService.updateInstInfo(input);
		return ResponseEntity.ok().body(model);
	}
	
	@PostMapping("/inst/removeInstInfo")
	public ResponseEntity<?> removeInstInfo(@ModelAttribute InstDto input, Model model) throws HmfbException  {
		
		infoAdminService.deleteInstInfo(input);
		return ResponseEntity.ok().body(model);
	}
	
	/* 연계정보 */
	@PostMapping("/link/registerLinkInfo")
	public ResponseEntity<?> registerLinkInfo(@ModelAttribute LinkDto input, Model model) throws HmfbException  {
		
		if (!StringUtils.hasText(input.getInstDvcd())) {
			throw new HmfbException(ErrorCode.E114, "기관구분명은 필수 항목입니다.");
		}
		if (!StringUtils.hasText(input.getInstSerNm())) {
			throw new HmfbException(ErrorCode.E114, "기관서버명은 필수 항목입니다.");
		}
		if (!StringUtils.hasText(input.getInstHost())) {
			throw new HmfbException(ErrorCode.E114, "기관호스트는 필수 항목입니다.");
		}
		if (!StringUtils.hasText(input.getInstPort())) {
			throw new HmfbException(ErrorCode.E114, "기관포트는 필수 항목입니다.");
		}
		
		infoAdminService.insertLinkInfo(input);
		return ResponseEntity.ok().body(model);
	}
	
	@PostMapping("/link/getLinkInfo")
	public ResponseEntity<?> getLinkInfo(@Nullable @RequestParam(required = false) String seqNo, Model model) throws HmfbException {

		LinkDto output = infoAdminService.selectLinkInfo(seqNo);
		model.addAttribute("linkInfo", output);
		return ResponseEntity.ok().body(model);
	}
	
	@PostMapping("/link/modifyLinkInfo")
	public ResponseEntity<?> modifyLinkInfo(@ModelAttribute LinkDto input, Model model) throws HmfbException  {
		
		if (!StringUtils.hasText(input.getInstDvcd())) {
			throw new HmfbException(ErrorCode.E114, "기관구분명은 필수 항목입니다.");
		}
		if (!StringUtils.hasText(input.getInstSerNm())) {
			throw new HmfbException(ErrorCode.E114, "기관서버명은 필수 항목입니다.");
		}
		if (!StringUtils.hasText(input.getInstHost())) {
			throw new HmfbException(ErrorCode.E114, "기관호스트는 필수 항목입니다.");
		}
		if (!StringUtils.hasText(input.getInstPort())) {
			throw new HmfbException(ErrorCode.E114, "기관포트는 필수 항목입니다.");
		}
		
		infoAdminService.updateLinkInfo(input);
		return ResponseEntity.ok().body(model);
	}
	
	@PostMapping("/link/removeLinkInfo")
	public ResponseEntity<?> removeLinkInfo(@ModelAttribute LinkDto input, Model model) throws HmfbException  {
		
		infoAdminService.deleteLinkInfo(input);
		return ResponseEntity.ok().body(model);
	}
	
}
