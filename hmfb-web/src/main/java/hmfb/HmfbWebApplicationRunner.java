package hmfb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import hmfb.web.schema.SchemaComponent;

/**

 * @FileName : HmfbWebApplicationRunner.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : WEB 기동 시 필요한 기능 수행

 * @변경이력 :

 */

@Component
public class HmfbWebApplicationRunner implements ApplicationRunner {
	
	@Autowired
	SchemaComponent sc;
	
	/**
	
	  * @Method Name : run
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : 한마펌뱅킹 WEB Application run
	
	  * @변경이력 : 
	
	  */
    @Override
    public void run(ApplicationArguments args) throws Exception  {
    	//WEB 기동 시 Schema 및 data 생성		
    	String schemaInfo = sc.getSchemaInfo();
		for(String tableName : sc.tablenames) {
			sc.setSchema(sc.tableInfos.get(tableName), !schemaInfo.contains(tableName));
		}
		
    }

}
