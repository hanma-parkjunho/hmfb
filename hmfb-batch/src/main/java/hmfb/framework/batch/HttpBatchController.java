package hmfb.framework.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hmfb.core.dto.BatchExeParamDTO;
import hmfb.core.dto.BatchExeResultDTO;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/batch")
public class HttpBatchController {

	@Autowired
	BatchJobLauncher uncJobLauncher;
	
	@Autowired
	BatchRepositoryExplorer explorer;
	
	@RequestMapping("execute")
	public BatchExeResultDTO executeBatch(@RequestBody BatchExeParamDTO param) {
		
		if (log.isInfoEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append("\n=============== 입력된 배치 파라미터 ===============\n");
			sb.append("%-10s:[%s]\n");
			sb.append("%-10s:[%s]\n");
			sb.append("%-10s:[%s]\n");
			sb.append("%-10s:[%s]\n");
			
			log.info(String.format(sb.toString(), 
					"jobYmd",param.getJobYmd(), 
					"jobCode",param.getJobCode(), 
					"returnYn",param.getReturnYn(), 
					"runParam",param.getRunParam()));
		}
		
		BatchExeResultDTO result = uncJobLauncher.execute(param);

		return result;		// 상태에 따라 응답 success or fail
	}
	
	@RequestMapping("summary")
	public BatchExeResultDTO summaryBatch(@RequestBody BatchExeParamDTO param) {
		return explorer.summary(param);
	}
}
