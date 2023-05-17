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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import hmfb.core.exception.HmfbException;

/**

 * @FileName : AbstractDataBlockIdGnrService.java

 * @작성자 : 송원호

 * @작성일 : 2023. 01. 16 

 * @프로그램 설명 : ID Generation 서비스를 위한 Data Block ID Abstract Service 클래스

 * @변경이력 :

 */

public abstract class AbstractDataBlockIdGnrService extends AbstractDataIdGnrService implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDataBlockIdGnrService.class);

	/**
	 * 첫번째로 할당된 BigDecimal 아이디
	 */
	private BigDecimal mFirstBigDecimal;

	/**
	 * 첫번째로 할당된 Long 아이디
	 */
	private long mFirstLong;

	/**
	 * 현재 블럭에 할당된 아이디 수
	 */
	private int mAllocated;

	/**
	 * 블럭 사이즈
	 */
	protected int blockSize;

	/**
	 * 주어진 길이만큼의 BigDecimal 블럭을 할당하는 
	 * @param blockSize 할당하고자 하는 블럭사이즈
	 * @return 할당 블럭의 첫번째 ID
	 * @throws HmfbException 여타이유에 의해 아이디 생성이 불가능 할때
	 */
	protected abstract BigDecimal allocateBigDecimalIdBlock(int blockSize) throws HmfbException;

	/**
	 * 주어진 길이만큼의 long 블럭을 할당하는 
	 * @param blockSize 할당하고자 하는 블럭사이즈
	 * @return 할당 블럭의 첫번째 ID
	 * @throws HmfbException 여타이유에 의해 아이디 생성이 불가능 할때
	 */
	protected abstract long allocateLongIdBlock(int blockSize) throws HmfbException;

	/**
	 * BigDecimal 타입의 유일 아이디 제공
	 * @return BigDecimal 타입 아이디 리턴
	 * @throws HmfbException 여타이유에 의해 아이디 생성이 불가능 할때
	 */
	protected BigDecimal getNextBigDecimalIdInner() throws HmfbException {
		if (mAllocated >= blockSize) {
			try {
				mFirstBigDecimal = allocateBigDecimalIdBlock(blockSize);
				mAllocated = 0;
			} catch (HmfbException be) {
				mAllocated = Integer.MAX_VALUE;
				throw be;
			}
		}
		BigDecimal id = mFirstBigDecimal.add(new BigDecimal(mAllocated));
		mAllocated++;
		return id;
	}

	/**
	 * Long 타입의 유일 아이디 제공
	 * @return long 타입 아이디 리턴
	 * @throws HmfbException 여타이유에 의해 아이디 생성이 불가능 할때
	 */
	protected long getNextLongIdInner() throws HmfbException {
		if (mAllocated >= blockSize) {
			try {
				mFirstLong = allocateLongIdBlock(blockSize);
				mAllocated = 0;
			} catch (HmfbException e) {
				mAllocated = Integer.MAX_VALUE;
				throw e;
			}
		}

		long id = mFirstLong + mAllocated;
		if (id < 0) {
			LOGGER.error(messageSource.getMessage("error.idgnr.greater.maxid", new String[] { "Long" }, Locale.getDefault()));
			throw new HmfbException(messageSource, "error.idgnr.greater.maxid");
		}

		mAllocated++;
		return id;
	}

	/**
	 * application Context configuration 에서 blockSize 입력받기
	 * @param blockSize application Context Configuration 에 세팅한 blocksize
	 */
	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	/**
	 * Container에 의해서 호출
	 * @throws Exception 초기화 도출 오류발생
	 */
	public void afterPropertiesSet() throws Exception {
		mAllocated = Integer.MAX_VALUE;
	}

}
