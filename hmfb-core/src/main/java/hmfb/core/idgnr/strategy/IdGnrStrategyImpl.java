package hmfb.core.idgnr.strategy;

/**

 * @FileName : IdGnrStrategyImpl.java

 * @작성자 : 송원호

 * @작성일 : 2023. 01. 16 

 * @프로그램 설명 : Id Generation 정책(Strategy) 를 위한 기본 구현 클래스

 * @변경이력 :

 */

public class IdGnrStrategyImpl implements IdGnrStrategy {

	private static final int DEFAULT_CIPERS = 5;

    private String prefix;

    private int cipers = DEFAULT_CIPERS;

    private char fillChar = '0';

    /**
	
	  * @Method Name : makeId
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2023. 01. 16 
	
	  * @Method 설명 : 생성된 String 타입의 ID를 입력받아서 정책으로 입력된 PREFIX에 기본 채울 
	  *              문자를 지정 길이만큼 넣어서 아이디 생성.
	
	  * @변경이력 : 
	
	  */
    public String makeId(String originalId) {
        return prefix + fillString(originalId, fillChar, cipers);
    }

    /**
	
	  * @Method Name : setCipers
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2023. 01. 16 
	
	  * @Method 설명 : Cipers.
	
	  * @변경이력 : 
	
	  */
    public void setCipers(int cipers) {
        this.cipers = cipers;
    }

    /**
	
	  * @Method Name : setPrefix
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2023. 01. 16 
	
	  * @Method 설명 : Prefix.
	
	  * @변경이력 : 
	
	  */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
	
	  * @Method Name : setFillChar
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2023. 01. 16 
	
	  * @Method 설명 : fillChar.
	
	  * @변경이력 : 
	
	  */
    public void setFillChar(char fillChar) {
        this.fillChar = fillChar;
    }

    /**
	
	  * @Method Name : IdGnrStrategyImpl
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2023. 01. 16 
	
	  * @Method 설명 : IdGnrStrategyImpl 생성자.
	
	  * @변경이력 : 
	
	  */
    public IdGnrStrategyImpl(String prefix, int cipers, char fillChar) {
        super();
        this.prefix = prefix;
        this.cipers = cipers;
        this.fillChar = fillChar;
    }

    /**
	
	  * @Method Name : IdGnrStrategyImpl
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2023. 01. 16 
	
	  * @Method 설명 : IdGnrStrategyImpl 생성자.
	
	  * @변경이력 : 
	
	  */
    public IdGnrStrategyImpl() {
        super();
    }

    /**
	
	  * @Method Name : fillString
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2023. 01. 16 
	
	  * @Method 설명 : 대상 길이만큼 디폴트 Char로 채우기.
	
	  * @변경이력 : 
	
	  */
    public static String fillString(String originalStr, char ch, int cipers) {
        int originalStrLength = originalStr.length();
        if (cipers < originalStrLength) {
            return null;
        }

        int difference = cipers - originalStrLength;
        StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < difference; i++) {
            strBuf.append(ch);
        }

        strBuf.append(originalStr);
        return strBuf.toString();
    }

}