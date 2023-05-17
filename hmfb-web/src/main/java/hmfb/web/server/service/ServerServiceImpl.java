package hmfb.web.server.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import hmfb.core.exception.ErrorCode;
import hmfb.core.exception.HmfbException;
import hmfb.web.server.prop.ServerInfo;
import hmfb.web.server.prop.ServerInfoProperties;
import lombok.extern.log4j.Log4j2;

/**

 * @FileName : ServerServiceImpl.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : ServerServiceImpl 클래스

 * @변경이력 :

 */
@Log4j2
@Service
@Transactional
public class ServerServiceImpl {
	
	final static String SERVER_STATUS_SUCCESS = "0";
	final static String SERVER_STATUS_FAIL = "-1";
	
	@Autowired
	private ServerInfoProperties serverProps;
	
	public ServerInfo getServerInfo(String systemId) throws HmfbException {
		List<ServerInfo> list = serverProps.getServerInfoList();
		for (ServerInfo serverInfo : list) {
			if (systemId.contentEquals(serverInfo.getSys())) {
				return serverInfo;
			}
		}
		throw new HmfbException(ErrorCode.E112, "존재하지 않는 시스템 ID 입니다["+systemId+"]. serverinfo.yml 를 확인해주세요");
	}
    
	public String doHealthCheck(ServerInfo info) {
		
		String resourceUrl = info.getIp()+":"+info.getPort()+"/node/healthcheck";
		
		RestTemplate restTemplate = new RestTemplate();
		SimpleClientHttpRequestFactory factory = (SimpleClientHttpRequestFactory)restTemplate.getRequestFactory();
		factory.setConnectTimeout(3000);
		
		try {
			
			ResponseEntity<String> response = restTemplate.exchange(resourceUrl, HttpMethod.HEAD, null, String.class);		
			HttpStatus status = response.getStatusCode();
			if (log.isDebugEnabled()) log.debug(status.toString());
		} catch (Throwable t) {
			return SERVER_STATUS_FAIL;
		}
		
		return SERVER_STATUS_SUCCESS;
	}
	
	public String executeStartScriptFile(String installPath, String systemId, String scriptFile) throws InterruptedException, IOException, HmfbException {
		
		String rtnMsg = SERVER_STATUS_FAIL;
		String filePath = installPath+File.separator+"hmfb-zbin"+File.separator+scriptFile+ this.getFileExt();
		ProcessBuilder pb = null;
		List<String> commandList = new ArrayList<String>();
		
		ServerInfo serverInfo = getServerInfo(systemId);
		commandList.add(filePath);
		commandList.add(serverInfo.getBootJarPath());
		commandList.add(serverInfo.getPort());
		
		pb = new ProcessBuilder(commandList);
		
		pb.redirectErrorStream(true);
		
		if (log.isInfoEnabled()) log.info("script경로:"+filePath);
		if (log.isInfoEnabled()) log.info("bootJar명:"+serverInfo.getBootJarPath());
		if (log.isInfoEnabled()) log.info("port 번호:"+serverInfo.getPort());
		if (log.isInfoEnabled()) log.info("script 시작..");
		Process process = pb.start();
		Scanner scan =  new Scanner(process.getInputStream(), getFileEncoding());
		while (scan.hasNext()) {
			String msg = scan.nextLine();
			log.info(msg);
			if (msg.contains("Started Hmfb")) {
				rtnMsg = SERVER_STATUS_SUCCESS; break;
			} else rtnMsg = msg;
		}
		if (scan != null) {
			scan.close();
		}
		return rtnMsg;
	}
	
	public String executeStopScriptFile(String installPath, String systemId, String scriptFile) throws InterruptedException, IOException, HmfbException {
		
		String filePath = installPath+File.separator+"hmfb-zbin"+File.separator+scriptFile+ this.getFileExt();
		ProcessBuilder pb = null;
		List<String> commandList = new ArrayList<String>();
		
		ServerInfo serverInfo = getServerInfo(systemId);
		commandList.add(filePath);
		commandList.add(serverInfo.getBootJarPath());
		commandList.add(serverInfo.getPort());
		
		pb = new ProcessBuilder(commandList);
		
		pb.redirectErrorStream(true);
		
		if (log.isInfoEnabled()) log.info("script경로:"+filePath);
		if (log.isInfoEnabled()) log.info("bootJar명:"+serverInfo.getBootJarPath());
		if (log.isInfoEnabled()) log.info("port 번호:"+serverInfo.getPort());
		if (log.isInfoEnabled()) log.info("script 시작..");
		Process process = pb.start();
		Scanner scan =  new Scanner(process.getInputStream(), getFileEncoding());
		while (scan.hasNext()) {
			String msg = scan.nextLine();
			log.info(msg);
		}
		if (scan != null) {
			scan.close();
		}
		return "0";
	}
	
	private String getFileExt() {
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.contains("windows")) {			
			return ".bat";	
		} else {
			return ".sh";
		}
	}
	
	private String getFileEncoding() {
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.contains("windows")) {
			return "ms949";
		} else {
			return "utf-8";
		}
	}
	
}
