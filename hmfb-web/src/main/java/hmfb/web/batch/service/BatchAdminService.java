package hmfb.web.batch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmfb.core.dto.BatchJobDTO;
import hmfb.core.dto.BatchJobPageDTO;
import hmfb.core.dto.BatchMonitorPageDTO;
import hmfb.web.batch.manage.BatchAdminDAO;

@Service
public class BatchAdminService {
	
	@Autowired
	private BatchAdminDAO dao;
	
	public int getBatchJobTotCnt(BatchJobPageDTO batchJobPageDto) {
		
		return dao.selectOne("batchadmin.getBatchJobTotCnt", batchJobPageDto);
	}
	
	public List<BatchJobPageDTO> getBatchJobList(BatchJobPageDTO batchJobPageDto) {
		
		return dao.selectList("batchadmin.getListBatchJobPage", batchJobPageDto);
	}
	
	public int getBatchExecutionTotCnt(BatchMonitorPageDTO batchMonitorPageDto) {
		Object result = dao.selectOne("batchadmin.getBatchExecutionTotCnt", batchMonitorPageDto);
		if (result == null) 
			return 0;
		else 
			return (Integer)result;
	}
	
	public List<BatchMonitorPageDTO> getBatchMonitorList(BatchMonitorPageDTO batchMonitorPageDto) {
		
		return dao.selectList("batchadmin.getBatchMonitorPage", batchMonitorPageDto);
	}
	
	public int insertBatchJob(BatchJobDTO input) {
		return dao.insert("batchadmin.insertBatchJob", input);
	}
	
	public BatchJobDTO selectBatchJob(String input) {
		return dao.selectOne("batchadmin.selectBatchJob", input);
	}
	
	public int updateBatchJob(BatchJobDTO input) {
		return dao.update("batchadmin.updateBatchJob", input);
	}
	
	public int deleteBatchJob(BatchJobDTO input) {
		return dao.delete("batchadmin.deleteBatchJob", input);
	}
}
