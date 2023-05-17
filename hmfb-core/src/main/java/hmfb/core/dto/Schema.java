package hmfb.core.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**

 * @FileName : Schema.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : 스키마 데이터 처리 클레스

 * @변경이력 :

 */

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Data
public class Schema {
	
	private final String STR_SCHEMA_MAPPER = "hmfb.web.schema.SchemaMapper";
	
	private String tableName;
	private List<?> datas;
	
	public String getCreateSql() {
		return this.STR_SCHEMA_MAPPER + ".create" + this.tableName;
	}
	
	public String getDropSql() {
		return this.STR_SCHEMA_MAPPER + ".drop" + this.tableName;
	}
	
	public String getInsertSql() {
		return this.STR_SCHEMA_MAPPER + ".insert" + this.tableName;
	}
	
	public String getDeleteSql() {
		return this.STR_SCHEMA_MAPPER + ".delete" + this.tableName;
	}
	
}
