package hmfb.core.log.service;

import java.util.Locale;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import hmfb.core.dto.ErrorDto;
import hmfb.core.dto.PrgmDto;
import hmfb.core.exception.ErrorCode;
import hmfb.core.idgnr.service.IdGnrService;
import hmfb.core.prop.LogProperties;
import hmfb.core.utils.DateUtil;
import lombok.extern.log4j.Log4j2;

/**

 * @FileName : LogService.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : 로그 처리 서비스 클레스

 * @변경이력 :

 */

@Log4j2
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
public class LogService {
	
	private static final Logger prgm_file_log = LoggerFactory.getLogger("PRGM_LOGGER");
	
	@Value("${project.sys}")
	private String sys;
	
	@Autowired
    private IdGnrService uuidGnrService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private LogProperties logProperties;
	
	@Autowired
    @Qualifier("defaulSqlSessionTemplate")
    private SqlSession sqlSession;
	
	@Autowired
    private MessageSource messageSource;
	
	/**
	
	  * @Method Name : log
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 로그 처리
	
	  * @변경이력 : 
	
	  */
	public void log(String prgmId, Object prgmLog) {
		try {
			this.log(prgmId, objectMapper.writeValueAsString(prgmLog));
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex.getCause());
		}
	}
	
	/**
	
	  * @Method Name : log
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 로그 처리
	
	  * @변경이력 : 
	
	  */
	public void log(String prgmId, String prgmLog) {
		
		try {
			
			String dttm = DateUtil.getCurrentDttm();
			
			PrgmDto prgmDto = PrgmDto.builder()
					.sys(sys)
					.uuid(uuidGnrService.getNextStringId())
					.timestamp(DateUtil.getCurrentTime())
					.prgmId(prgmId)
					.prgmLog(prgmLog)
					.regDt(dttm.substring(0, 8))
					.regTm(dttm.substring(8))
					.build();
			
			String logStr = objectMapper.writeValueAsString(prgmDto);
			//log.info(logStr);
			if("Y".equals(logProperties.getFileLog())) prgm_file_log.error(logStr);
			if("Y".equals(logProperties.getDbLog()))
				sqlSession.insert("hmfb.core.log.LogMapper.insertLog", prgmDto);
			
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex.getCause());
		}
	}
	
	/**
	
	  * @Method Name : log
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 로그 처리
	
	  * @변경이력 : 
	
	  */
	public void log(String path, ErrorCode errorCode, Exception ex) {
		
		String dttm = DateUtil.getCurrentTime();
		
		ErrorDto errorDto = ErrorDto.builder()
				.status(errorCode.getStatus())
				.path(path)
				.error(errorCode.getError())
				.message(ObjectUtils.isEmpty(ex.getMessage()) ? messageSource.getMessage(errorCode.getMessage(), new Object[] {}, Locale.getDefault()) : ex.getMessage())
				.errorDt(dttm.substring(0, 8))
				.errorTm(dttm.substring(8))
				.build();
		
		this.log("ERROR", errorDto);
	}
	
	/**
	
	  * @Method Name : log
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 오류 로그 처리
	
	  * @변경이력 : 
	
	  */
	public void log(ErrorCode errorCode, Exception ex) {
		this.log("", errorCode, ex);
	}

}
