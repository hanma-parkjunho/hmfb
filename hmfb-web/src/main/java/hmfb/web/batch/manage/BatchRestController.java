package hmfb.web.batch.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hmfb.core.dto.BatchJobDTO;
import hmfb.core.exception.ErrorCode;
import hmfb.core.exception.HmfbException;
import hmfb.web.batch.service.BatchAdminService;

@RestController
public class BatchRestController {
	
	@Autowired
	BatchAdminService batchAdminService;
	
	@PostMapping("/batch/registerBatchJob")
	public ResponseEntity<?> registerBatchJob(@ModelAttribute BatchJobDTO input, Model model) throws HmfbException  {
		
		if (input.getJobCode().length() != 9) {
			throw new HmfbException(ErrorCode.E116, "작업식별자의 길이는 9자리여야 합니다.");
		}
		
		if (!StringUtils.hasText(input.getProgramName())) {
			throw new HmfbException(ErrorCode.E114, "자바 프로그램명은 필수 항목입니다.");
		}
		
		if (input.getCommitCount() <= 0 || input.getCommitCount() > 10000) {
			throw new HmfbException(ErrorCode.E116, "커밋건수는 1~10000 범위의 숫자여야 합니다");
		}
		if (StringUtils.hasText(input.getCronExpression())
				&& !CronExpression.isValidExpression(input.getCronExpression())) {
			throw new HmfbException(ErrorCode.E116, "유효한 cron expression 이 아닙니다.");
		}
		batchAdminService.insertBatchJob(input);
		return ResponseEntity.ok().body(model);
	}
	
	@PostMapping("/batch/getBatchJob")
	public ResponseEntity<?> getBatchJob(@Nullable @RequestParam(required = false) String jobCode, Model model) throws HmfbException {

		BatchJobDTO output = batchAdminService.selectBatchJob(jobCode);
		model.addAttribute("batchJob", output);
		return ResponseEntity.ok().body(model);
	}
	
	@PostMapping("/batch/modifyBatchJob")
	public ResponseEntity<?> modifyBatchJob(@ModelAttribute BatchJobDTO input, Model model) throws HmfbException  {
		
		if (input.getCommitCount() <= 0 || input.getCommitCount() > 10000) {
			throw new HmfbException(ErrorCode.E116, "커밋건수는 1~10000 범위의 숫자여야 합니다");
		}
		if (!StringUtils.hasText(input.getProgramName())) {
			throw new HmfbException(ErrorCode.E114, "자바 프로그램명은 필수 항목입니다.");
		}
		if (StringUtils.hasText(input.getCronExpression())
				&& !CronExpression.isValidExpression(input.getCronExpression())) {
			throw new HmfbException(ErrorCode.E116, "유효한 cron expression 이 아닙니다.");
		}
		
		batchAdminService.updateBatchJob(input);
		return ResponseEntity.ok().body(model);
	}
	
	@PostMapping("/batch/removeBatchJob")
	public ResponseEntity<?> removeBatchJob(@ModelAttribute BatchJobDTO input, Model model) throws HmfbException  {
		
		batchAdminService.deleteBatchJob(input);
		return ResponseEntity.ok().body(model);
	}
}
