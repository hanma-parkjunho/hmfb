package hmfb.web.log.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hmfb.core.dto.PrgmDto;
import hmfb.core.exception.HmfbException;
import hmfb.core.pagination.PaginationInfo;
import hmfb.core.prop.LogProperties;
import hmfb.core.utils.StringUtil;
import hmfb.web.server.prop.ServerInfo;
import hmfb.web.server.prop.ServerInfoProperties;

/**

 * @FileName : LogServiceImpl.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : Log ServiceImpl 클레스 

 * @변경이력 :

 */

@Service
@Transactional
public class LogServiceImpl {
	
	@Autowired
    @Qualifier("defaulSqlSessionTemplate")
    private SqlSession sqlSession;
	
	@Autowired
	private LogProperties logProperties;  
	
	@Autowired
	private ServerInfoProperties serverProps;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	/**
    
     * @Method Name : getPrgmId
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 로그 PRGMID 내역 조회
   
     * @변경이력 : 
   
     */
	public List<PrgmDto> getPrgmIdList(PrgmDto prgmDto) throws HmfbException {
		return sqlSession.selectList("hmfb.web.log.LogMapper.getPrgmIdList", prgmDto);
	}
	
	/**
    
     * @Method Name : getlogList
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 로그정보 내역조회
   
     * @변경이력 : 
   
     */
    public List<PrgmDto> getlogList(PrgmDto prgmDto) throws HmfbException {
    	return sqlSession.selectList("hmfb.web.log.LogMapper.getlogList", prgmDto);
    }
    
    /**
    
     * @Method Name : getlogTotCnt
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 로그정보 총 건수 조회
   
     * @변경이력 : 
   
     */
    public int getlogTotCnt(PrgmDto prgmDto) throws HmfbException {
    	return (Integer)sqlSession.selectOne("hmfb.web.log.LogMapper.getlogTotCnt", prgmDto);
    }
    
    /**
    
     * @Method Name : getlogFileList
   
     * @작성자 : 송원호
   
     * @작성일 : 2022. 12. 28
   
     * @Method 설명 : 로그정보 FILE 내역조회
   
     * @변경이력 : 
   
     */
    public Page<PrgmDto> getlogFileList(PrgmDto prgmDto, PaginationInfo paginationInfo) throws HmfbException {

    	List<JsonNode> logJsonList = new ArrayList<JsonNode>();
		
		for(ServerInfo serverInfo : serverProps.getServerInfoList()) {
			if(StringUtil.isEmpty(prgmDto.getSearchSys()) || serverInfo.getSys().equals(prgmDto.getSearchSys())) {
				File logFiles = new File(logProperties.getLogPath() + File.separator + "logs"+ File.separator + serverInfo.getName() + File.separator + "PRGM");
				if(!ObjectUtils.isEmpty(logFiles.listFiles())) {
					for(File file : logFiles.listFiles()) {
						//log.info(serverInfo.getSys() + " " + file.getName());
						try {
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");	
							Date searchStDt = format.parse(prgmDto.getSearchStDt().replace(".", "-"));
							Date searchEdDt = format.parse(prgmDto.getSearchEdDt().replace(".", "-"));
							Date searchDt = format.parse(file.getName().subSequence(5, 15).toString());
							if(searchDt.compareTo(searchStDt) >= 0 && searchDt.compareTo(searchEdDt) <= 0) {
								BufferedReader reader = new BufferedReader(new FileReader(file));
								String line;
								while ((line = reader.readLine()) != null) {
									 JsonNode logInfo = objectMapper.readTree(line);
									 boolean isSet = false;
									 if(StringUtil.isEmpty(prgmDto.getSearchKeyword()) && StringUtil.isEmpty(prgmDto.getSearchPrgmId())) {
										 isSet = true;
									 } else if(!StringUtil.isEmpty(prgmDto.getSearchKeyword()) && !StringUtil.isEmpty(prgmDto.getSearchPrgmId())) {
										 if(logInfo.get("prgmLog").asText().contains(prgmDto.getSearchKeyword()) && logInfo.get("prgmId").asText().toUpperCase().contains(prgmDto.getSearchPrgmId().toUpperCase()))
											 isSet = true;
									 } else {
										 if(!StringUtil.isEmpty(prgmDto.getSearchKeyword()) && logInfo.get("prgmLog").asText().contains(prgmDto.getSearchKeyword())) {
											 isSet = true;
										 }
										 if(!StringUtil.isEmpty(prgmDto.getSearchPrgmId()) && logInfo.get("prgmId").asText().toUpperCase().contains(prgmDto.getSearchPrgmId().toUpperCase())) {
											 isSet = true;
										 }
									 }
									 
									 if(isSet)
										 logJsonList.add(logInfo);
						        }
								reader.close();
							} 
						} catch (ParseException | IOException e) {
							// TODO Auto-generated catch block
							throw new HmfbException(e);
						}
					}
				}
			}
		}
		
		paginationInfo.setTotalRecordCount(logJsonList.size());
		prgmDto.setFirstIndex(paginationInfo.getCurrentRecordIndex());
		prgmDto.setLastIndex(paginationInfo.getNextRecordIndex() > paginationInfo.getTotalRecordCount() ?paginationInfo.getTotalRecordCount() : paginationInfo.getNextRecordIndex());
		prgmDto.setPageIndex(paginationInfo.getCurrentPageNo());
		prgmDto.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		
		PageRequest pageRequest = PageRequest.of(prgmDto.getPageIndex(), prgmDto.getRecordCountPerPage(), Sort.by("regDtTm"));
		return new PageImpl<>(logJsonList.stream().map(log -> PrgmDto
				.builder()
				.sys(log.get("sys").asText())
				.uuid(log.get("uuid").asText())
				.timestamp(log.get("timestamp").asText())
				.prgmId(log.get("prgmId").asText())
				.prgmLog(log.get("prgmLog").asText())
				.regDtTm(log.get("regDt").asText()+log.get("regTm").asText())
				.build()).collect(Collectors.toList()).subList(prgmDto.getFirstIndex(), prgmDto.getLastIndex()), pageRequest, logJsonList.size());
			
    }
    
}
