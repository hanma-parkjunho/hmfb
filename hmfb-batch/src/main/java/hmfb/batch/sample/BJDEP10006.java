package hmfb.batch.sample;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hmfb.core.dto.AccountDDTO;
import hmfb.core.dto.BatchJobContext;
import hmfb.core.exception.HmfbException;
import hmfb.framework.batch.biz.ICustomBatchJob;
import hmfb.framework.batch.constant.BatchConstants;
import hmfb.framework.batch.db.BatchDao;
import lombok.extern.log4j.Log4j2;

/**
 * 샘플코드에서 사용한 데이터 예시
 * ------------------------------------
 * 배치 유형      : Custom Batch 
 * 
 * @author KDK
 *
 */
@Log4j2
public class BJDEP10006 implements ICustomBatchJob {

	@Autowired
	private BatchDao batchDao;
	
	@Override
	public boolean isExecutable(BatchJobContext ctx) throws HmfbException {
		
		if(log.isDebugEnabled()) {
			log.debug("[업무로그]"+getClass().getSimpleName()+".isExecutable 실행");
		}
		
		DayOfWeek day = LocalDate.now().getDayOfWeek();
//		일요일엔 정상 종료 리턴. (배치를 수행시키지 않는다. 상태는 FAILED 가 아닌 COMPLETED)
		return (day.equals(DayOfWeek.SATURDAY)) ? false : true;
	}

	@Override
	public void preProcess(BatchJobContext ctx) throws HmfbException {		
		if(log.isDebugEnabled()) {
			log.debug("[업무로그]"+getClass().getSimpleName()+".preProcess 실행");
		}		
	}

	@Override
	public void process(BatchJobContext ctx) throws HmfbException {
		if(log.isDebugEnabled()) {
			log.debug("[업무로그]"+getClass().getSimpleName()+".process 실행");
		}
//		파일 읽기
//		String fileName = "C:/Users/kmk/git/unchart/springbank/src/test/resources/input/files/fwtest_20221220.csv";
		String fileName = "C:/Develop/java/git/unchart/springbank/src/test/resources/input/files/fwtest_20221220.csv";
		LineIterator iter;
		List<AccountDDTO> allList = new ArrayList<AccountDDTO>();
		try {
			iter = FileUtils.lineIterator(new File(fileName), "UTF-8");
			while (iter.hasNext()) {
				String line = iter.nextLine();
				AccountDDTO account = buildDTO(line);
				allList.add(account);
			}	
		} catch (IOException e) {
			throw new HmfbException("파일 없음", e);
		}
		
		int chunkSize = 3;
		
//		DB insert
		List<AccountDDTO> chunkList = new ArrayList<AccountDDTO>();
		for (int i=0; i< allList.size(); i++) {
			
			AccountDDTO item  = allList.get(i);
			chunkList.add(item);
			
			if (chunkList.size() /chunkSize > 0 && chunkList.size() % chunkSize == 0) {
//				chunk 단위 처리 
				processChunk(chunkList);
//				처리 후 초기화
				chunkList.clear();
			}
		}
		
	}
	
	private AccountDDTO buildDTO(String line) {
		
		String[] values = StringUtils.split(line, BatchConstants.DELIM_COMMA);
		
		AccountDDTO accountDdto = new AccountDDTO();
		
		accountDdto.setAccountNo(values[0]);
		accountDdto.setProductCode(values[1]);
		accountDdto.setCifNo(values[2]);
		accountDdto.setBalance(new BigDecimal(values[3]));
		accountDdto.setOpenDate(values[4]);
		accountDdto.setStatus(values[5]);
		
		return accountDdto;
	}
	
	/*
	 * 
	 * @param transaction
	 * @param accountList
	 */
	@Transactional(rollbackFor = HmfbException.class)
	private void processChunk(List<AccountDDTO> accountList) throws HmfbException {

		try {
			int index = 1;
			for (AccountDDTO accountDdto : accountList) {
//				tx 분리 사례: 채번기 
//				int seq = getSeqNo(accountDdto);
//				accountDdto.setSeq(seq);
				batchDao.insert("TDEP10001.openAccount", accountDdto);

				if (log.isDebugEnabled()) log.debug(index +"번째 생성");
				index++;
			}
			
		} catch (Throwable t) {
			throw new HmfbException("예제라서 생략", t);
		}
	}
	
	/*
	 * REQUIRED 와 REQURIES_NEW 로만 트랜잭션을 제어하도록 한다. TX API 를 직접 코딩하지 않는다.  
	 */
	@Transactional(rollbackFor = HmfbException.class, propagation = Propagation.REQUIRES_NEW)
	private int getSeqNo(AccountDDTO accountDdto) {
//		여기에 채번 로직을 구현 
		return 0;
	}
	
	/*
	 * programmatic TX 제어는 사용 안함. @Transactional 을 이용
	 * @param transaction
	 * @param accountList
	 *
	private void processChunk(List<AccountDDTO> accountList) {
		
		BatchTransaction transaction = new BatchTransaction();
		transaction.begin();
		try {
			int index = 1;
			for (AccountDDTO accountDdto : accountList) {
				
				batchDao.insert("TDEP10001.openAccount", accountDdto);

				if (log.isDebugEnabled()) log.debug(index +"번째 생성");
				index++;
			}
//			트랜잭션 당 커밋은 한번만 수행.
//			Transaction is already completed - do not call commit or rollback more than once per transaction
			transaction.commit();
			
		} catch (Throwable t) {
			transaction.rollback();
			throw new HmfbException("", t);
		}
	}
	*/
	@Override
	public void postProcess(BatchJobContext ctx, Throwable t) throws HmfbException {	
		if(log.isDebugEnabled()) {
			log.debug("[업무로그]"+getClass().getSimpleName()+".postProcess 실행");
		}
		
	}
}
