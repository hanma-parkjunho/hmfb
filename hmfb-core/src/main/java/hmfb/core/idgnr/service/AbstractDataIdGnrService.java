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

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.DisposableBean;

/**

 * @FileName : AbstractDataIdGnrService.java

 * @작성자 : 송원호

 * @작성일 : 2023. 01. 16 

 * @프로그램 설명 : ID Generation 서비스를 위한 Data Id  Abstract Service 클래스

 * @변경이력 :

 */

public abstract class AbstractDataIdGnrService extends AbstractIdGnrService implements IdGnrService, DisposableBean {

    /**
     * DB 연결을 위한 dataSource
     */
    protected DataSource dataSource = null;

    /**
     * 쿼리 정보
     */
    protected String query = "";

    /**
     * 현재 블럭에 할당된 아이디 수
     */
    protected int mAllocated;

    /**
     * 생성할 다음 ID
     */
    protected long mNextId;

    /**
     * DB 연결 Connection 얻기
     * @return Connection 정보
     * @throws SQLException Connection을 얻지 못했을 경우
     */
    protected Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * 소멸자
     */
    public void destroy() {
        dataSource = null;
    }

    /**
     * Configuration에 정의된 Datasource를 Setting
     * @param dataSource dataSource 정보
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Configuration에 정의된 Query를 Setting
     * @param query Query 정보
     */
    public void setQuery(String query) {
        this.query = query;
    }

}

