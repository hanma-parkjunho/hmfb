package hmfb.core.constants;

public enum InterfaceMapping {
    
    INF_FIRM_01("0100","100","업무개시",0),
    INF_FIRM_02("0100","200","업무종료",0),
    INF_FIRM_03("0100","300","시스템상황체크",0),
	INF_FIRM_04("0200","200","2자이체",0),
	INF_FIRM_05("0200","300","3자이체",0),
	INF_FIRM_06("0200","400","4자이체",0),
	INF_FIRM_07("0300","100","경유계좌등록",0),
	INF_FIRM_08("0300","200","경유계좌해지",0),
	INF_FIRM_09("0300","300","가상계좌과제정보등록",0),
	INF_FIRM_10("0300","400","은행대리점등록",0),
	INF_FIRM_11("0300","500","모계좌별가상계좌요청",0),
	INF_FIRM_12("0400","100","이체처리결과조회",0),
	INF_FIRM_13("0500","100","모계좌잔액조회",0),
	INF_FIRM_14("0500","200","수취인조회",0),
	INF_FIRM_15("0500","300","해지예상조회",0),
	INF_FIRM_16("0600","100","거래명세통지",0),
	INF_FIRM_17("0600","200","거래명세통지결번요청",0),
	
	/* 표준 인터페이스 추가 [s] */
	INF_FIRM_18("1000","100","업무개시",0),	
	INF_FIRM_19("1000","200","업무종료",0),
	INF_FIRM_20("2000","101","당,타행 지급이체(자금이체)",0),
	INF_FIRM_21("2000","550", "자동이체 신청",0),
	INF_FIRM_22("2000","850", "자동이체 청구",0),
	INF_FIRM_23("7000","101","지급이체(자금이체) 처리결과조회",0),
	INF_FIRM_24("6000","101","당,타행 예금주 성명조회",0),
	INF_FIRM_25("3000","101","타행이체결과 불능분 통지",0), /* 은행 -> 기관 수신 인터페이스  */
	INF_FIRM_26("3000","201","타행이체결과 결번요구",0),
	INF_FIRM_27("7000","200","모계좌 잔액조회",0),
	INF_FIRM_28("4000","101","예금거래(교환어음)명세통지",0),  /* 은행 -> 기관 수신 인터페이스 */
	INF_FIRM_29("4000","201","예금거래(교환어음)명세 결번요구",0);
	/* 표준 인터페이스  추가 [e] */

	private String interfaceId;   // 인터페이스 id
	private String tranId;        // 거래 id
	private String interfaceName; // 인터페이스 명
	private String intfVsno;      // 버전번호
	
	InterfaceMapping(String string, String string2, String string3, int i) {
		this.interfaceId = string;
		this.tranId = string2;
		this.interfaceName = string3;
		this.intfVsno = Integer.toString(i);
	}
	
	public String getInterfaceId() {
	    return interfaceId;
	}
	
	public String getTranId() {
	    return tranId;		
	}
	
	public String getInterfaceName() {
	    return interfaceName;
	}
	
	public String getIntfVsno() {
	    return intfVsno;
	}
	
}