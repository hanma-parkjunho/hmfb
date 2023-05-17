package hmfb.web.schema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import hmfb.core.dto.CmmnDto;
import hmfb.core.dto.IdgnrDto;
import hmfb.core.dto.MenuAuthDto;
import hmfb.core.dto.MenuDto;
import hmfb.core.dto.Schema;
import hmfb.core.enc.asc.AES256;
import hmfb.core.prop.AdminProperties;
import hmfb.core.utils.DateUtil;
import hmfb.web.user.security.UserDetail;
import lombok.extern.log4j.Log4j2;

/**

 * @FileName : SchemaComponent.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : 스키마 생성 정보

 * @변경이력 :

 */

@Log4j2
@Component
public class SchemaComponent {
	
	@Autowired
	private AdminProperties adminProperties;
	
	@Autowired
    @Qualifier("defaulSqlSessionTemplate")
    private SqlSession sqlSession;
	
	@Autowired
    private MessageSource messageSource; 
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	AES256 aesEncryptor;
	
	@Value("${hmfb.db.always-init}")
	private boolean alwaysInit;
    
	public String [] tablenames = {
			"HMFB_001",
			"HMFB_002",
			"HMFB_003",
			"HMFB_004",
			"HMFB_005",
			"HMFB_006",
			"HMFB_007",
			"IDGNR"
	};
	
	public Map<String,Schema> tableInfos;
	
	public String getSchemaInfo() throws Exception {
		tableInfos = new HashMap<String,Schema>();
    	
		for(String tableName : this.tablenames) {
			tableInfos.put(tableName, Schema.builder()
					.tableName(tableName)
					.datas(Arrays.stream(this.getClass().getMethods()).filter(m -> m.getName().equals(tableName)).collect(Collectors.counting()) > 0 ? (List<?>)this.getClass().getMethod(tableName).invoke(this) : null)
					.build());
		}
		return sqlSession.selectList("hmfb.web.schema.SchemaMapper.getSchemaInfo", this.tablenames).toString();
	}
	
	public void setSchema(Schema schema, boolean isNew) throws Exception {
		
		String strMappedStatementNames = sqlSession.getConfiguration().getMappedStatementNames().toString();
		
		if(isNew) {
			if(strMappedStatementNames.contains(schema.getCreateSql())) {
				log.info(schema.getTableName() + " 테이블 신규 생성");
				sqlSession.insert(schema.getCreateSql());
			} else {
				log.error(messageSource.getMessage("error.schema.noSchemaSql", new Object[] { schema.getTableName() }, Locale.getDefault()));
			}
			if(schema.getDatas() != null) {
				if(strMappedStatementNames.contains(schema.getInsertSql())) {
					log.info(schema.getTableName() + " 테이블 초기 데이터 등록");
					for(Object data : schema.getDatas()) {
						sqlSession.insert(schema.getInsertSql(), data);
					}
				} else {
					log.error(messageSource.getMessage("error.schema.noDataSql", new Object[] { schema.getTableName() }, Locale.getDefault()));
				}
			}
		} else {
			if(alwaysInit) {
				if(strMappedStatementNames.contains(schema.getCreateSql()) && 
				   strMappedStatementNames.contains(schema.getDropSql())) {
					log.info(schema.getTableName() + " 테이블 삭제 후 생성");
					sqlSession.insert(schema.getDropSql());
					sqlSession.insert(schema.getCreateSql());
				} else {
					log.error(messageSource.getMessage("error.schema.noSchemaSql", new Object[] { schema.getTableName() }, Locale.getDefault()));
				}
				if(schema.getDatas() != null &&
				   strMappedStatementNames.contains(schema.getDeleteSql()) && 
				   strMappedStatementNames.contains(schema.getInsertSql())) {
					log.info(schema.getTableName() + " 테이블 초기 데이터 삭제 후 등록");
					for(Object data : schema.getDatas()) {
						sqlSession.delete(schema.getDeleteSql(), data);
						sqlSession.insert(schema.getInsertSql(), data);
					}
				} else {
					log.error(messageSource.getMessage("error.schema.noDataSql", new Object[] { schema.getTableName() }, Locale.getDefault()));
				}
			}
		}
		
	}
	
	/**
	
	  * @Method Name : HMFB_001
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : HMFB_001 최초 데이터 설정
	
	  * @변경이력 : 
	
	  */
	public List<MenuAuthDto> HMFB_001() throws Exception {
   	
	   	List<MenuAuthDto> rows = new ArrayList<MenuAuthDto>(); 
	   	
	   	String dttm = DateUtil.getCurrentDttm();
	   	
	   	//관리자
	   	
	   	//사용자관리("0000000001")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000001").authDvcd("ADMIN").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	//사용자정보("0000000002")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000002").authDvcd("ADMIN").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	//권한정보("0000000003")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000003").authDvcd("ADMIN").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	    
	   	//메뉴관리("0000000004")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000004").authDvcd("ADMIN").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	//메뉴관리정보("0000000005")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000005").authDvcd("ADMIN").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	    //메뉴권한정보("0000000006")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000006").authDvcd("ADMIN").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	    
	   	//연계환경관리("0000000007")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000007").authDvcd("ADMIN").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	//기관정보("0000000008")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000008").authDvcd("ADMIN").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	    //연계정보("0000000009")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000009").authDvcd("ADMIN").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	    //서버별기동정보("0000000010")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000010").authDvcd("ADMIN").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	    //로그정보("0000000011")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000011").authDvcd("ADMIN").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	    //배치시간설정("0000000012")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000012").authDvcd("ADMIN").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	    //공통코드정보("0000000013")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000013").authDvcd("ADMIN").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	    //I/F사용유무정보("0000000014")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000014").authDvcd("ADMIN").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	  
	   	//뱅킹서비스관리("0000000015")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000015").authDvcd("ADMIN").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	//펌뱅킹항목정보("0000000016")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000016").authDvcd("ADMIN").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	    //가상계좌항목정보("0000000017")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000017").authDvcd("ADMIN").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	    //펌뱅킹개발테스트("0000000018")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000018").authDvcd("ADMIN").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	    
	   	//모니터링("0000000019")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000019").authDvcd("ADMIN").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	//실시간모니터링("0000000020")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000020").authDvcd("ADMIN").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	    //배치모니터링("0000000021")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000021").authDvcd("ADMIN").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	    //온라인모니터링("0000000022")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000022").authDvcd("ADMIN").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	
	   	//연계환경관리("0000000007")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000007").authDvcd("AGMNG").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	    //기관정보("0000000008")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000008").authDvcd("AGMNG").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	    //연계정보("0000000009")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000009").authDvcd("AGMNG").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	    //서버별기동정보("0000000010")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000010").authDvcd("AGMNG").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	    //로그정보("0000000011")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000011").authDvcd("AGMNG").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	    //배치시간설정("0000000012")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000012").authDvcd("AGMNG").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	    //공통코드정보("0000000013")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000013").authDvcd("AGMNG").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	    //I/F사용유무정보("0000000014")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000014").authDvcd("AGMNG").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	    
	   	//뱅킹서비스관리("0000000015")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000015").authDvcd("AGMNG").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	//펌뱅킹항목정보("0000000016")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000016").authDvcd("AGMNG").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	    //가상계좌항목정보("0000000017")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000017").authDvcd("AGMNG").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	    //펌뱅킹개발테스트("0000000018")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000018").authDvcd("AGMNG").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	
	   	//모니터링("0000000019")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000019").authDvcd("AGMNG").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	//실시간모니터링("0000000020")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000020").authDvcd("AGMNG").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	    //배치모니터링("0000000021")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000021").authDvcd("AGMNG").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	    //온라인모니터링("0000000022")
	   	rows.add(MenuAuthDto.builder().menuSeqNo("0000000022").authDvcd("AGMNG").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	
	   	return rows;
   	
   }
	
	/**
	
	  * @Method Name : HMFB_002
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : HMFB_002 최초 데이터 설정
	
	  * @변경이력 : 
	
	  */
	public List<MenuDto> HMFB_002() throws Exception {
   	
	   	List<MenuDto> rows = new ArrayList<MenuDto>(); 
	   	
	   	String dttm = DateUtil.getCurrentDttm();
	   	
	   	//ROOT("0000000000")
	   	rows.add(MenuDto.builder()
	   	    .seqNo("0000000000").uprnSeqNo(null)
	   	    .flnm("ROOT").url(null).lvl("0").odr("0").uzYn("Y")
	        .regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	
	   	//사용자관리("0000000001")
	   	rows.add(MenuDto.builder()
	   	   	    .seqNo("0000000001").uprnSeqNo("0000000000")
	   	   	    .flnm("사용자관리").url(null).lvl("1").odr("0").uzYn("Y").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	//사용자정보("0000000002")
	   	rows.add(MenuDto.builder()
	   	   	    .seqNo("0000000002").uprnSeqNo("0000000001")
	   	   	    .flnm("사용자정보").url("/user/userInfo").lvl("2").odr("1").uzYn("Y").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	    //권한정보("0000000003")
	   	rows.add(MenuDto.builder()
	   	   	    .seqNo("0000000003").uprnSeqNo("0000000001")
	   	   	    .flnm("권한정보").url("/user/authInfo").lvl("2").odr("2").uzYn("Y").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	
	   	//메뉴관리("0000000004")
	   	rows.add(MenuDto.builder()
	   	   	    .seqNo("0000000004").uprnSeqNo("0000000000")
	   	   	    .flnm("메뉴관리").url(null).lvl("1").odr("1").uzYn("Y").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	//메뉴관리정보("0000000005")
	   	rows.add(MenuDto.builder()
	   	   	    .seqNo("0000000005").uprnSeqNo("0000000004")
	   	   	    .flnm("메뉴관리정보").url("/menu/menuMngInfo").lvl("2").odr("1").uzYn("Y").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	//메뉴권한정보("0000000006")
	   	rows.add(MenuDto.builder()
	   	   	    .seqNo("0000000006").uprnSeqNo("0000000004")
	   	   	    .flnm("메뉴권한정보").url("/menu/menuAuthInfo").lvl("2").odr("2").uzYn("Y").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	
	   	//연계환경관리("0000000007")
	   	rows.add(MenuDto.builder()
	   	   	    .seqNo("0000000007").uprnSeqNo("0000000000")
	   	   	    .flnm("연계환경관리").url(null).lvl("1").odr("2").uzYn("Y").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	//기관정보("0000000008")
	   	rows.add(MenuDto.builder()
	   	   	    .seqNo("0000000008").uprnSeqNo("0000000007")
	   	   	    .flnm("기관정보").url(null).lvl("2").odr("1").uzYn("Y").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	//연계정보("0000000009")
	   	rows.add(MenuDto.builder()
	   	   	    .seqNo("0000000009").uprnSeqNo("0000000007")
	   	   	    .flnm("연계정보").url(null).lvl("2").odr("2").uzYn("Y").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	//서버별기동정보("0000000010")
	   	rows.add(MenuDto.builder()
	   	   	    .seqNo("0000000010").uprnSeqNo("0000000007")
	   	   	    .flnm("서버별기동정보").url("/server/serverInfo").lvl("2").odr("3").uzYn("Y").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	//로그정보("0000000011")
	   	rows.add(MenuDto.builder()
	   	   	    .seqNo("0000000011").uprnSeqNo("0000000007")
	   	   	    .flnm("로그정보").url("/log/logInfo").lvl("2").odr("4").uzYn("Y").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	//배치기본정보("0000000012")
	   	rows.add(MenuDto.builder()
	   	   	    .seqNo("0000000012").uprnSeqNo("0000000007")
	   	   	    .flnm("배치기본정보").url("/batchJobsInfo").lvl("2").odr("5").uzYn("Y").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	//공통코드정보("0000000013")
	   	rows.add(MenuDto.builder()
	   	   	    .seqNo("0000000013").uprnSeqNo("0000000007")
	   	   	    .flnm("공통코드정보").url("/cmmn/cmmnInfo").lvl("2").odr("6").uzYn("Y").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	//I/F사용유무정보("0000000014")
	   	rows.add(MenuDto.builder()
	   	   	    .seqNo("0000000014").uprnSeqNo("0000000007")
	   	   	    .flnm("I/F사용유무정보").url(null).lvl("2").odr("7").uzYn("Y").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	
	   	//뱅킹서비스관리("0000000015")
	   	rows.add(MenuDto.builder()
	   	   	    .seqNo("0000000015").uprnSeqNo("0000000000")
	   	   	    .flnm("뱅킹서비스관리").url(null).lvl("1").odr("3").uzYn("Y").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	//펌뱅킹항목정보("0000000016")
	   	rows.add(MenuDto.builder()
	   	   	    .seqNo("0000000016").uprnSeqNo("0000000015")
	   	   	    .flnm("펌뱅킹항목정보").url(null).lvl("2").odr("1").uzYn("Y").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	//가상계좌항목정보("0000000017")
	   	rows.add(MenuDto.builder()
	   	   	    .seqNo("0000000017").uprnSeqNo("0000000015")
	   	   	    .flnm("가상계좌항목정보").url(null).lvl("2").odr("2").uzYn("Y").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	//펌뱅킹개발테스트("0000000018")
	   	rows.add(MenuDto.builder()
	   	   	    .seqNo("0000000018").uprnSeqNo("0000000015")
	   	   	    .flnm("펌뱅킹개발테스트").url(null).lvl("2").odr("3").uzYn("Y").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	
	   	//모니터링("0000000019")
	   	rows.add(MenuDto.builder()
	   	   	    .seqNo("0000000019").uprnSeqNo("0000000000")
	   	   	    .flnm("모니터링").url(null).lvl("1").odr("4").uzYn("Y").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	//실시간모니터링("0000000020")
	   	rows.add(MenuDto.builder()
	   	   	    .seqNo("0000000020").uprnSeqNo("0000000019")
	   	   	    .flnm("실시간모니터링").url(null).lvl("2").odr("1").uzYn("Y").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	//배치모니터링("0000000021")
	   	rows.add(MenuDto.builder()
	   	   	    .seqNo("0000000021").uprnSeqNo("0000000019")
	   	   	    .flnm("배치모니터링").url("/monitorBatchJobs").lvl("2").odr("2").uzYn("Y").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	//온라인모니터링("0000000022")
	   	rows.add(MenuDto.builder()
	   	   	    .seqNo("0000000022").uprnSeqNo("0000000019")
	   	   	    .flnm("온라인모니터링").url(null).lvl("2").odr("3").uzYn("Y").regDt(dttm.substring(0, 8)).regTm(dttm.substring(8)).build());
	   	
	   	return rows;
	   	
   }
	
    /**
	
	  * @Method Name : HMFB_003
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : HMFB_003 최초 데이터 설정
	
	  * @변경이력 : 
	
	  */
	public List<UserDetail> HMFB_003() throws Exception {
    	
    	List<UserDetail> rows = new ArrayList<UserDetail>(); 
    	
    	String dttm = DateUtil.getCurrentDttm();
    	
    	//관리자 정보 설정
    	rows.add(UserDetail.builder()
    			.seqNo(adminProperties.getSeqNo())
    			.userId(adminProperties.getUserId())
    			.pswd(bCryptPasswordEncoder.encode(adminProperties.getPswd()))
    			.flnm(adminProperties.getFlnm())
    			.phnNo(aesEncryptor.encrypt(adminProperties.getPhnNo()))
    			.depart(adminProperties.getDepart())
    			.authDvcd(adminProperties.getAuthDvcd())
    			.delYn("N")
    			.regDt(dttm.substring(0, 8))
    			.regTm(dttm.substring(8))
    			.build());
    	
    	return rows;
    }
   
    /**
	
	  * @Method Name : HMFB_006
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : HMFB_006 최초 데이터 설정
	
	  * @변경이력 : 
	
	  */
	public List<CmmnDto> HMFB_006() throws Exception {
    	
    	List<CmmnDto> rows = new ArrayList<CmmnDto>(); 
    	
    	String dttm = DateUtil.getCurrentDttm();
    	
    	//공통코드 권한정보 
    	rows.add(CmmnDto.builder()
    			.codeGrp("AUTH_DVCD")
    			.code("ADMIN")
    			.name("관리자")
    			.seq("1")
    			.uzYn("Y")
    			.regDt(dttm.substring(0, 8))
    			.regTm(dttm.substring(8))	
    			.build());
    	
    	rows.add(CmmnDto.builder()
    			.codeGrp("AUTH_DVCD")
    			.code("AGMNG")
    			.name("기관담당자")
    			.seq("2")
    			.uzYn("Y")
    			.regDt(dttm.substring(0, 8))
    			.regTm(dttm.substring(8))		
    			.build());
    	
    	
    	return rows;
    }
    
	public List<IdgnrDto> IDGNR() throws Exception {
    	
    	List<IdgnrDto> rows = new ArrayList<IdgnrDto>(); 
    	
    	//ID Generation 초기 값 설정
    	rows.add(IdgnrDto.builder().tableName("USER_SEQ_NO").nextId(1L).build());
    	rows.add(IdgnrDto.builder().tableName("MENU_SEQ_NO").nextId(23L).build());
    	return rows;
    }

}
