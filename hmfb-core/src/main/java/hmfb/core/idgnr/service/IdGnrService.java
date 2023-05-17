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
import hmfb.core.exception.HmfbException;
import hmfb.core.idgnr.strategy.IdGnrStrategy;

/**

 * @FileName : IdGnrService.java

 * @작성자 : 송원호

 * @작성일 : 2023. 01. 16 

 * @프로그램 설명 : Id Generation 서비스의 인터페이스 클래스

 * @변경이력 :

 */
public interface IdGnrService {

	/**
	
	  * @Method Name : getNextBigDecimalId
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2023. 01. 16 
	
	  * @Method 설명 : BigDecimal 형식의 Id 제공
	
	  * @변경이력 : 
	
	  */
    BigDecimal getNextBigDecimalId() throws HmfbException;

    /**
	
	  * @Method Name : getNextLongId
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2023. 01. 16 
	
	  * @Method 설명 : Long 형식의 Id 제공
	
	  * @변경이력 : 
	
	  */
    long getNextLongId() throws HmfbException;

    /**
	
	  * @Method Name : getNextIntegerId
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2023. 01. 16 
	
	  * @Method 설명 :  Integer 형식의 Id 제공
	
	  * @변경이력 : 
	
	  */
    int getNextIntegerId() throws HmfbException;

    /**
	
	  * @Method Name : getNextShortId
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2023. 01. 16 
	
	  * @Method 설명 :  Short 형식의 Id 제공
	
	  * @변경이력 : 
	
	  */
    short getNextShortId() throws HmfbException;

    /**
	
	  * @Method Name : getNextByteId
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2023. 01. 16 
	
	  * @Method 설명 :  Byte 형식의 Id 제공
	
	  * @변경이력 : 
	
	  */
    byte getNextByteId() throws HmfbException;

    /**
	
	  * @Method Name : getNextStringId
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2023. 01. 16 
	
	  * @Method 설명 :  String 형식의 Id 제공
	
	  * @변경이력 : 
	
	  */
    String getNextStringId() throws HmfbException;

    /**
	
	  * @Method Name : getNextStringId
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2023. 01. 16 
	
	  * @Method 설명 :  정책을 스트링으로 입력받고 String 형식의 Id 제공
	
	  * @변경이력 : 
	
	  */
    String getNextStringId(String strategyId) throws HmfbException;

    /**
	
	  * @Method Name : getNextStringId
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2023. 01. 16 
	
	  * @Method 설명 :  정책을 정책클래스로 입력받고 String 형식의 Id 제공
	
	  * @변경이력 : 
	
	  */
    String getNextStringId(IdGnrStrategy strategy) throws HmfbException;

}
