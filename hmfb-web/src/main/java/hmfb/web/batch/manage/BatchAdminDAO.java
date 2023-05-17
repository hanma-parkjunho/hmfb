package hmfb.web.batch.manage;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**

 * @FileName : BatchAdminDAO.java

 * @작성자 : 김덕기

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : BatchAdminDAO 클레스 

 * @변경이력 :

 */

@Repository
public class BatchAdminDAO {
	
	@Autowired
    @Qualifier("defaulSqlSessionTemplate")
    private SqlSession sqlSession;
	
	public <T> T selectOne(String sqlId, Object param) {
		return sqlSession.selectOne(sqlId, param);
	}
	
	public <T> List<T> selectList(String sqlId, Object param) {
		return sqlSession.selectList(sqlId, param);
	}
	
	public int insert(String sqlId, Object param) {
		return sqlSession.insert(sqlId, param);
	}
	
	public int update(String sqlId, Object param) {
		return sqlSession.update(sqlId, param);
	}
	
	public int delete(String sqlId, Object param) {
		return sqlSession.delete(sqlId, param);
	}
	    
}
