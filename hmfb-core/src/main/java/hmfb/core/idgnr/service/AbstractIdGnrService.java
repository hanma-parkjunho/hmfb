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
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;

import hmfb.core.exception.HmfbException;
import hmfb.core.idgnr.strategy.IdGnrStrategy;

/**

 * @FileName : AbstractIdGnrService.java

 * @작성자 : 송원호

 * @작성일 : 2023. 01. 16 

 * @프로그램 설명 : ID Generation 서비스를 위한 Abstract Service 클래스

 * @변경이력 :

 */

public abstract class AbstractIdGnrService implements IdGnrService, ApplicationContextAware, BeanFactoryAware {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractIdGnrService.class);

	/**
	 * BeanFactory
	 */
	private BeanFactory beanFactory;

	/**
	 * BIG_DECIMAL_MAX_LONG 정의
	 */
	private static final BigDecimal BIG_DECIMAL_MAX_LONG = new BigDecimal(Long.MAX_VALUE);

	/**
	 * 내부 synchronization을 위한 정보
	 */
	private final Object mSemaphore = new Object();

	/**
	 * 정책정보 생성
	 */
	private IdGnrStrategy strategy = new IdGnrStrategy() {
		public String makeId(String originalId) {
			return originalId;
		}
	};

	/**
	 * BigDecimal 사용 여부
	 */
	protected boolean useBigDecimals = false;

	/**
	 * MessageSource
	 */
	protected MessageSource messageSource;

	/**
	 * 기본 생성자
	 */
	public AbstractIdGnrService() {
	}

	/**
	 * BigDecimal 타입의 유일 아이디 제공
	 * @return BigDecimal 타입의 다음 ID
	 * @throws HmfbException if an Id could not be allocated for any reason.
	 */
	protected abstract BigDecimal getNextBigDecimalIdInner() throws HmfbException;

	/**
	 * long 타입의 유일 아이디 제공
	 * @return long 타입의 다음 ID
	 * @throws HmfbException 여타이유에 의해 아이디 생성이 불가능 할때
	 */
	protected abstract long getNextLongIdInner() throws HmfbException;

	/**
	 * BigDecimal 사용여부 세팅
	 * @param useBigDecimals BigDecimal 사용여부
	 */
	public void setUseBigDecimals(boolean useBigDecimals) {
		this.useBigDecimals = useBigDecimals;
	}

	/**
	 * BigDecimal 사용여부 정보 리턴
	 * @return boolean check using BigDecimal
	 */
	protected boolean isUsingBigDecimals() {
		return useBigDecimals;
	}

	/**
	 * 특별한 최대 값보다 작은 Long 타입의 다음 ID
	 * @param maxId 최대값
	 * @return long value to be less than the specified maxId
	 * @throws HmfbException 다음 ID가 입력받은 MaxId보다 클때
	 */
	protected long getNextLongIdChecked(long maxId) throws HmfbException {
		long nextId;
		if (useBigDecimals) {
			BigDecimal bd;
			synchronized (mSemaphore) {
				bd = getNextBigDecimalIdInner();
			}

			if (bd.compareTo(BIG_DECIMAL_MAX_LONG) > 0) {
				LOGGER.error(messageSource.getMessage("error.idgnr.greater.maxid", new String[] { "Long" }, Locale.getDefault()));
				throw new HmfbException(messageSource, "error.idgnr.greater.maxid");
			}
			nextId = bd.longValue();
		} else {
			synchronized (mSemaphore) {
				nextId = getNextLongIdInner();
			}
		}

		if (nextId > maxId) {
			LOGGER.error(messageSource.getMessage("error.idgnr.greater.maxid", new String[] { "Long" }, Locale.getDefault()));
			throw new HmfbException(messageSource, "error.idgnr.greater.maxid");
		}

		return nextId;
	}

	/**
	 * Returns BigDecimal 타입의 다음 ID 제공
	 * @return BigDecimal the next Id.
	 * @throws HmfbException 다음 아이디가 유효한 BigDecimal의 범위를 벗어날때
	 */
	public BigDecimal getNextBigDecimalId() throws HmfbException {
		BigDecimal bd;
		if (useBigDecimals) {
			synchronized (mSemaphore) {
				bd = getNextBigDecimalIdInner();
			}
		} else {
			synchronized (mSemaphore) {
				bd = new BigDecimal(getNextLongIdInner());
			}
		}
		return bd;
	}

	/**
	 * Returns long 타입의 다음 ID 제공
	 * @return the next Id.
	 * @throws HmfbException 다음 아이디가 유효한 long의 범위를 벗어날때
	 */
	public long getNextLongId() throws HmfbException {
		return getNextLongIdChecked(Long.MAX_VALUE);
	}

	/**
	 * Returns int 타입의 다음 ID 제공
	 * @return the next Id.
	 * @throws HmfbException 다음 아이디가 유효한 integer의 범위를 벗어날때
	 */
	public int getNextIntegerId() throws HmfbException {
		return (int) getNextLongIdChecked(Integer.MAX_VALUE);
	}

	/**
	 * Returns Short 타입의 다음 ID 제공
	 * @return the next Id.
	 * @throws HmfbException 다음 아이디가 유효한 Short의 범위를 벗어날때
	 */
	public short getNextShortId() throws HmfbException {
		return (short) getNextLongIdChecked(Short.MAX_VALUE);
	}

	/**
	 * Returns Byte 타입의 다음 ID 제공
	 * @return the next Id.
	 * @throws HmfbException 다음 아이디가 유효한 Byte 범위를 벗어날때
	 */
	public byte getNextByteId() throws HmfbException {
		return (byte) getNextLongIdChecked(Byte.MAX_VALUE);
	}

	/**
	 * String 타입의 Id 제공하는데 정책의 아이디 생성 호출함
	 * @return the next Id.
	 * @throws HmfbException 다음 아이디가 유효한 byte의 범위를 벗어날때
	 */
	public String getNextStringId() throws HmfbException {
		return strategy.makeId(getNextBigDecimalId().toString());
	}

	/**
	 * 정책 클래스를 입력받아서 String 타입의 Id 제공
	 * @param strategy 생성된 정책 오브젝트
	 * @return the next Id.
	 * @throws HmfbException 다음 아이디가 유효한 byte의 범위를 벗어날때
	 */
	public String getNextStringId(IdGnrStrategy strategy) throws HmfbException {
		this.strategy = strategy;
		return getNextStringId();
	}

	/**
	 * 정책정보를 String으로 입력받아서 String 타입의 Id 제공
	 * @param strategyId 정책 스트링
	 * @return the next Id.
	 * @throws HmfbException 다음 아이디가 유효한 byte의 범위를 벗어날때
	 */
	public String getNextStringId(String strategyId) throws HmfbException {
		this.strategy = (IdGnrStrategy) this.beanFactory.getBean(strategyId);
		return getNextStringId();
	}

	/**
	 * 정책 얻기
	 * @return IdGenerationStrategy
	 */
	public IdGnrStrategy getStrategy() {
		return strategy;
	}

	/**
	 * 정책 세팅
	 * @param strategy to be set by Spring Framework
	 */
	public void setStrategy(IdGnrStrategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * set BeanFactory
	 * @param beanFactory to be set by Spring Framework
	 */
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	/**
	 * Message Source Injection
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.messageSource = (MessageSource) applicationContext.getBean("messageSource");
	}

}

