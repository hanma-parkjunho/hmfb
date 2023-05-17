package hmfb.framework.batch.constant;

/**
 * 상수 클래스 
 * 주의 : final 키워드 사용 금지.
 * TODO : Enum 으로 변경 
 * @author KDK
 *
 */
public class BatchConstants {
	/** 여부 구분 Y */
	public static String YN_Y = "Y";
	/** 여부 구분 N */
	public static String YN_N = "N";
	
	public static String SPACE	= " ";
	/** CSV 파일 리더에서 사용 중 */
	public static String DELIM_COMMA		= ",";
	
	public static String CONTEXT_ROOT	= "/";
	public static String APP_NAME		= "sprbank";
	
	/** 일자 포맷 : 년월일 */
	public static String FMT_YMD	= "yyyyMMdd";
	/** 일자 포맷 : 년월일_시분초 */
	public static String FMT_YMD_HMS	= "yyyyMMddhhmmss";
	/** 일자 포맷 : 년월일_밀리초까지 */
	public static String FMT_YMD_MLS	= "yyyyMMddhhmmssSSS";
	/* 일자 포맷 : 년월일_나노초까지 : multi-thread 에서 채번 시 nanotime 고려 필요. 
	 *                                              random.setSeed(System.nanoTime()); String.valueOf(random.nextInt(10));
	 */
	/* 파일 유형 */
	public static String FILE_TYPE_DELIM			= "DELIM";
	public static String FILE_TYPE_FIXED			= "FIXED";

	
	/********************************************************************
	 * 			배치 파라미터 KEY 
	 * ******************************************************************/
	public static String BAT_PARAM_JOB_YMD		= "jobYmd";
	public static String BAT_PARAM_JOB_CODE		= "jobCode";
	public static String BAT_PARAM_RETURN_YN	= "returnYn";
	public static String BAT_PARAM_RUN_PARAM	= "runParam";
	public static String BAT_PARAM_JOB_UUID		= "uniqueRunId";
	/********************************************************************
	 * 			배치 예외 SKIP 정책 구분코드 
	 * ******************************************************************/	
	public static String SKIP_NON		= "N";
	public static String SKIP_YES		= "S";
	public static String SKIP_ABORT	= "A";
	
	/********************************************************************
	 * 			배치 입출력 유형  
	 * ******************************************************************/
	public static String JOB_TYPE_CUSTOM	= "CTM";
	public static String JOB_TYPE_F2D		= "F2D";
	public static String JOB_TYPE_D2F		= "D2F";
	public static String JOB_TYPE_D2D		= "D2D";
	
	public static String JOB_IO_TYPE_DB			= "D";
	public static String JOB_IO_TYPE_FILE		= "F";
	
	
	public static String KEY_BAT_CTX = "BatchJobContext";
	
	public static String KEY_FILE_IO = "FileIOMetaDTO";
	
	/********************************************************************
	 * 			배치 주기 구분코드 
	 * ******************************************************************/
	/** 배치 주기 : 수시 */
	public static String BAT_CYCLE_ANY				= "A";
	/** 배치 주기 : 일별 */
	public static String BAT_CYCLE_DAILY			= "D";
	/** 배치 주기 : 월별 */
	public static String BAT_CYCLE_MONTH		= "M";
	/** 배치 주기 : 분기별 */
	public static String BAT_CYCLE_QUARTER	= "Q";
	/** 배치 주기 : 년별 */
	public static String BAT_CYCLE_YEAR			= "Y";
	
	/********************************************************************
	 * 			배치 상태 구분코드 
	 * ******************************************************************/
	/** 정상 완료 */
	public static String BAT_STATUS_COMPLETED	= "C";
	/** 실패 */
	public static String BAT_STATUS_FAILED			= "F";
	
	/********************************************************************
	 * 			배치 진행상태 구분코드 
	 * ******************************************************************/
	/** 초기화 : BatchJobLauncher.execute 진입 시  */
	public static String PROC_STATUS_INIT		= "I";
	/** Job 진입 : beforeJob */
	public static String PROC_STATUS_START		= "S";
	/** process STEP 진입 : beforeStep, afterChunk 에서 건수 누적  */
	public static String PROC_STATUS_RUNNING	= "R";
	/** 정상 완료 : afterJob. */
	public static String PROC_STATUS_COMPLETED	= "C";
	/** 실패 */
	public static String PROC_STATUS_FAILED		= "F";
	
	/********************************************************************
	 * 			배치 전처리에서 조건에 따른 정상 종료  
	 * ******************************************************************/
	public static String NORMAL_TERMINATED = "NORMAL_TERMINATED";
}
