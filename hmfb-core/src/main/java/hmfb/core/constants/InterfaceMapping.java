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
	INF_FIRM_17("0600","200","거래명세통지결번요청",0);


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