package hmfb.core.idgnr.service;

/*
 * Copyright 2008-2009 MOPAS(Ministry of Public Administration and Security).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.math.BigDecimal;
import java.util.Locale;
import javax.sql.DataSource;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import hmfb.core.exception.ErrorCode;
import hmfb.core.exception.HmfbException;

/**

 * @FileName : IdGnrStrategy.java

 * @작성자 : 송원호

 * @작성일 : 2023. 01. 16 

 * @프로그램 설명 : ID Generation 서비스를 위한 Table 구현 클래스

 * @변경이력 :

 */

public class TableIdGnrService extends AbstractDataBlockIdGnrService {
	                                   
	private static final Logger LOGGER = LoggerFactory.getLogger(TableIdGnrService.class);

    /**
     * ID생성을 위한 테이블 정보 디폴트는 ids임.
     */
	private String table = "ids";

    /**
     * 테이블 정보에 기록되는 대상 키정보 대개의 경우는 아이디로 생성되는 테이블명을 기재함
     */
    private String tableName = "id";

    /**
     * 테이블명(구분값)에 대한 테이블 필드명 지정
     */
    private String tableNameFieldName = "table_name";

    /**
     * Next Id 정보를 보관하는 필드명 지정
     */
    private String nextIdFieldName = "next_id";

    /**
     * Jdbc template
     */
    private JdbcTemplate jdbcTemplate;

    /**
     * TransactionTemplate
     */
    private TransactionTemplate transactionTemplate;

    /**
     * 생성자
     */
    public TableIdGnrService() {
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.transactionTemplate.setPropagationBehaviorName("PROPAGATION_REQUIRES_NEW");
        this.transactionTemplate.setIsolationLevelName("ISOLATION_READ_COMMITTED");
    }

    /**
     * tableName에 대한 초기 값이 없는 경우 초기 id 값 등록 (blockSize 처리)
     * @param useBigDecimals
     * @param blockSize
     */
    private Object insertInitId(final boolean useBigDecimals, final int blockSize) {
		LOGGER.debug(messageSource.getMessage("debug.idgnr.init.idblock", new Object[] { tableName }, Locale.getDefault()));
        Object initId = null;
    	String insertQuery = "INSERT INTO " + table + "(" + tableNameFieldName + ", " + nextIdFieldName + ") " + "values('" + tableName + "', ?)";
        LOGGER.debug("Insert Query : {}", insertQuery);
    	if (useBigDecimals) {
	   		initId = new BigDecimal(blockSize);
    	} else {
    		initId = new Long(blockSize);
    	}
    	jdbcTemplate.update(insertQuery, initId);
    	return initId;
    }

    /**
     * blockSize 대로 ID 지정
     * @param blockSize 지정되는 blockSize
     * @param useBigDecimals BigDecimal 사용 여부
     * @return BigDecimal을 사용하면 BigDecimal 아니면 long 리턴
     * @throws HmfbException ID생성을 위한 블럭 할당이 불가능할때
     */
	private Object allocateIdBlock(final int blockSize, final boolean useBigDecimals) throws HmfbException {
		try {
			LOGGER.debug(messageSource.getMessage("debug.idgnr.allocate.idblock", new Object[] { new Integer(blockSize), tableName }, Locale.getDefault()));
			return transactionTemplate.execute(new TransactionCallback<Object>() {
				@SuppressWarnings("deprecation")
				public Object doInTransaction(TransactionStatus status) {
					Object nextId;
					Object newNextId;
					try {
						String selectQuery = "SELECT " + nextIdFieldName + " FROM " + table + " WHERE " + tableNameFieldName + " = ?";
						LOGGER.debug("Select Query : {}", selectQuery);
						if (useBigDecimals) {
							try {
								nextId = jdbcTemplate.queryForObject(selectQuery, new Object[] { tableName }, BigDecimal.class);
							} catch (EmptyResultDataAccessException erdae) {
								nextId = null;
							}

							if (nextId == null) { // no row
								insertInitId(useBigDecimals, blockSize);
								return new BigDecimal(1);
							}
						} else {
							try {
								nextId = jdbcTemplate.queryForObject(selectQuery, new Object[] { tableName }, Long.class);
							} catch (EmptyResultDataAccessException erdae) {
								nextId = -1L;
							}

							if ((Long) nextId == -1L) { // no row
								insertInitId(useBigDecimals, blockSize);

								return new Long(1);
							}
						}
					} catch (DataAccessException dae) {
						//2017.02.28 장동한 시큐어코딩(ES)-오류 메시지를 통한 정보노출[CWE-209]
						status.setRollbackOnly();
						throw new RuntimeException(new HmfbException(messageSource, "error.idgnr.select.idblock", new String[] { tableName }, null));
					}

					try {
						String updateQuery = "UPDATE " + table + " SET " + nextIdFieldName + " = ?" + " WHERE " + tableNameFieldName + " = ?";
						LOGGER.debug("Update Query : {}", updateQuery);

						if (useBigDecimals) {
							newNextId = ((BigDecimal) nextId).add(new BigDecimal(blockSize));
						} else {
							newNextId = new Long(((Long) nextId).longValue() + blockSize);
						}

						jdbcTemplate.update(updateQuery, newNextId, tableName);
						return nextId;
					} catch (DataAccessException dae) {
						status.setRollbackOnly();
						throw new RuntimeException(new HmfbException(messageSource, "error.idgnr.update.idblock", new String[] { tableName }, null));
					}
				}
			});
		} catch (RuntimeException re) {
			if (re.getCause() instanceof HmfbException) {
				throw (HmfbException) re.getCause();
			} else {
				throw new HmfbException(messageSource.getMessage(ErrorCode.E500.getMessage(), new Object[] {}, Locale.getDefault()), re);
			}
		}
    }

    /**
     * blockSize 대로 ID 지정(BigDecimal)
     * @param blockSize 지정되는 blockSize
     * @return 할당된 블럭의 첫번째 아이디
     * @throws HmfbException ID생성을 위한 블럭 할당이 불가능할때
     */
	protected BigDecimal allocateBigDecimalIdBlock(int blockSize) throws HmfbException {
		return (BigDecimal) allocateIdBlock(blockSize, true);
	}

    /**
     * blockSize 대로 ID 지정(long)
     * @param blockSize 지정되는 blockSize
     * @return 할당된 블럭의 첫번째 아이디
     * @throws HmfbException ID생성을 위한 블럭 할당이 불가능할때
     */
    protected long allocateLongIdBlock(int blockSize) throws HmfbException {
        Long id = (Long) allocateIdBlock(blockSize, false);
        return id.longValue();
    }

    /**
     * ID생성을 위한 테이블 정보 Injection
     * @param table config로 지정되는 정보
     */
    public void setTable(String table) {
        this.table = table;
    }

    /**
     * ID 생성을 위한 테이블의 키정보 ( 대개의경우는 대상 테이블명을 기재함 )
     * @param tableName config로 지정되는 정보
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     *  테이블명(구분값)에 대한 테이블 필드명 정보 지정
     * @param tableNameFieldName
     */
    public void setTableNameFieldName(String tableNameFieldName) {
    	this.tableNameFieldName = tableNameFieldName;
    }

    /**
     * Next Id 정보를 보관하는 필드명 정보 지정
     * @param nextIdFieldName
     */
    public void setNextIdFieldName(String nextIdFieldName) {
    	this.nextIdFieldName = nextIdFieldName;
    }

}
