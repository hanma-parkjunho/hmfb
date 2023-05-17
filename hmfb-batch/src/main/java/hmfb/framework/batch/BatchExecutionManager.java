package hmfb.framework.batch;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hmfb.core.dto.BatchExeParamDTO;
import hmfb.core.dto.BatchExecutionDTO;
import hmfb.core.management.ApplicationContextProvider;
import hmfb.framework.batch.constant.BatchConstants;
import hmfb.framework.batch.db.BatchDao;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class BatchExecutionManager {
	
	@Autowired
	private BatchDao batchDao;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public BatchExecutionDTO insertExecution(BatchExeParamDTO param) {
		
		BatchExecutionDTO pExecution = new BatchExecutionDTO();
		pExecution.setJobUuid(UUID.randomUUID().toString());
		pExecution.setJobExecutionId(0);
		
		pExecution.setJobCode(param.getJobCode());
		pExecution.setJobYmd(param.getJobYmd());
		pExecution.setRunParam(param.getRunParam());
		
		String ndtm = LocalDateTime.now().format(DateTimeFormatter.ofPattern(BatchConstants.FMT_YMD_MLS));
		pExecution.setStartDtm(ndtm);
//		pExecution.setEndDtm(BatchConstants.SPACE);
		pExecution.setProcStatus(BatchConstants.PROC_STATUS_INIT);
//		pExecution.setExitMessage(BatchConstants.SPACE);
		String hostName = "";
		try {
			hostName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			hostName = "";
		}
		pExecution.setProcessServerName(hostName);
		String wasId = System.getProperty("was.id");
		pExecution.setProcessWasId(StringUtils.defaultString(wasId));
		
//		pExecution.setTotalCount(0);
//		pExecution.setCompletedCount(0);

//		pExecution.setInputDataSelector(BatchConstants.SPACE);
//		pExecution.setOutputDataSelector(BatchConstants.SPACE);
//		pExecution.setExceptionPolicy(BatchConstants.SPACE);
		
		batchDao.insert("framework.insertBatchExecution", pExecution);
		
		return pExecution;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void updateExecution(BatchExecutionDTO param) {
		
		String ndtm = LocalDateTime.now().format(DateTimeFormatter.ofPattern(BatchConstants.FMT_YMD_MLS));
		param.setModifyDtm(ndtm);
		
		batchDao.update("framework.updateBatchExecution", param);
	}
	
	public BatchExecutionDTO selectExecution(String jobUuid) {
		return batchDao.selectOne("framework.selectBatchExecution", jobUuid);
	}
	
	public List<BatchExecutionDTO> selectListExecution(BatchExecutionDTO condition) {
		return batchDao.selectList("framework.selectListBatchExecution", condition);
	}
	
	public static BatchExecutionManager getService() {
		ApplicationContext context = ApplicationContextProvider.getApplicationContext();
		return context.getBean(BatchExecutionManager.class);
	}
}
