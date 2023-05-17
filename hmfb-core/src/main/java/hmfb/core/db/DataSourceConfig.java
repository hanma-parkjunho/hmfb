package hmfb.core.db;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import hmfb.core.prop.DbProperties;

/**

 * @FileName : DataSourceConfig.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : DataSource Config 클레스

 * @변경이력 :

 */

@Configuration
public class DataSourceConfig {
		
		@Autowired
		private DbProperties dbProperties;
		
		
		
		/**
		
		  * @Method Name : defaultDataSource
		
		  * @작성자 : 송원호
		
		  * @작성일 : 2022. 12. 28
		
		  * @Method 설명 : DataSource 생성 
		
		  * @변경이력 : 
		
		  */
		@Bean
        public DataSource defaultDataSource() throws UnsupportedEncodingException, IOException {
			return DataSourceBuilder.create()
        			                .driverClassName(dbProperties.getDriverClassName())
        			                .url(dbProperties.getUrl())
        			                .username(dbProperties.getUsername())
        			                .password(dbProperties.getPassword())
        			                .build();
        }
		
		/**
		
		  * @Method Name : transactionManager
		
		  * @작성자 : 송원호
		
		  * @작성일 : 2022. 12. 28
		
		  * @Method 설명 : DataSourceTransactionManager 생성 
		
		  * @변경이력 : 
		
		  */
		@Bean
		public DataSourceTransactionManager transactionManager(DataSource defaulDataSource) {
			return new DataSourceTransactionManager(defaulDataSource);
		}
		
		/**
		
		  * @Method Name : defaulSqlSessionFactory
		
		  * @작성자 : 송원호
		
		  * @작성일 : 2022. 12. 28
		
		  * @Method 설명 : SqlSessionFactory 생성 
		
		  * @변경이력 : 
		
		  */
        @Bean
        public SqlSessionFactory defaulSqlSessionFactory(DataSource defaulDataSource, ApplicationContext applicationContext) throws Exception {
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(defaulDataSource);
            sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:hmfb/core/sqlmap/mybatis-config.xml"));
            sqlSessionFactoryBean.setMapperLocations(resolveMapperLocations());
            return sqlSessionFactoryBean.getObject();
        }
        
        public Resource[] resolveMapperLocations() throws Exception {
            ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
            List<String> mapperLocations = new ArrayList<>();
            mapperLocations.add("classpath:sqlmap/mappers/**/*_" + dbProperties.getDbType() + ".xml");
            mapperLocations.add("classpath:hmfb/core/sqlmap/mappers/**/*_" + dbProperties.getDbType() + ".xml");
            List<Resource> resources = new ArrayList<>();
            if (!mapperLocations.isEmpty()) {
                for (String mapperLocation : mapperLocations) {
                	Resource[] mappers = resourceResolver.getResources(mapperLocation);
                    resources.addAll(Arrays.asList(mappers));
                }
            }
            return resources.toArray(new Resource[resources.size()]);
        }
        
        /**
		
		  * @Method Name : defaulSqlSessionTemplate
		
		  * @작성자 : 송원호
		
		  * @작성일 : 2022. 12. 28
		
		  * @Method 설명 : SqlSessionTemplate 생성 
		
		  * @변경이력 : 
		
		  */
        @Bean
        public SqlSessionTemplate defaulSqlSessionTemplate(SqlSessionFactory defaulSqlSessionFactory) throws Exception {
            return new SqlSessionTemplate(defaulSqlSessionFactory);
        }
        
        /**
		
		  * @Method Name : batchSqlSessionTemplate
		
		  * @작성자 : KDK
		
		  * @작성일 : 2022. 12. 28
		
		  * @Method 설명 : SqlSessionTemplate 생성 
		
		  * @변경이력 : 
		
		  */
        @Bean
        public SqlSessionTemplate batchSqlSessionTemplate(SqlSessionFactory defaulSqlSessionFactory) throws Exception {
        	return new SqlSessionTemplate(defaulSqlSessionFactory, ExecutorType.BATCH);
        }
}
