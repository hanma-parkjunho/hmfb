package hmfb.framework.batch.init;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import hmfb.core.exception.ErrorCode;
import hmfb.core.exception.HmfbException;
import hmfb.framework.batch.prop.BatchProps;
import lombok.extern.log4j.Log4j2;
/*
 * ApplicationReadyEvent : An ApplicationReadyEvent is fired to indicate that the application is ready to service requests.
 * 							It is advised not to modify the internal state at this point since all initialization steps will be completed.
 * 
 * @author KDK
 *
 */
@Log4j2
@Component
public class BatchReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {
	
	@Autowired
	BatchProps batchProps;
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		
		File inputDir	= new File(batchProps.getInputRootDir());
		File outputDir	= new File(batchProps.getOutputRootDir());
//		없으면 생성
		try {
//	        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxrwxrwx");
//	        FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(perms);
			
			if (!FileUtils.isDirectory(inputDir)) {
				FileUtils.forceMkdir(inputDir);
			}
			if (!FileUtils.isDirectory(outputDir)) {
				FileUtils.forceMkdir(outputDir);
			}
		} catch (IOException e) {
//			배치 데이터 파일을 위한 입출력 디렉토리를 생성할 때 오류가 발생했습니다
			throw new RuntimeException(new HmfbException(ErrorCode.E809, e));
		}
		
		if (log.isInfoEnabled()) {
			log.info("배치 프레임워크가 시작되었습니다.");
		}
	}

}
