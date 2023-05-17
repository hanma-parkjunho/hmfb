package hmfb.framework.batch.db;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import hmfb.core.management.ApplicationContextProvider;

@Repository
public class BatchDao {
	
	@Autowired
    @Qualifier("batchSqlSessionTemplate")
	private SqlSessionTemplate sqlSession;
		
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
	/**
	 * POJO 에서 참조용. 현재 배치 프로그램들에서 사용 
	 * @return
	 */
	public static BatchDao getDao() {
		ApplicationContext context = ApplicationContextProvider.getApplicationContext();
		return context.getBean(BatchDao.class);
	}
}
