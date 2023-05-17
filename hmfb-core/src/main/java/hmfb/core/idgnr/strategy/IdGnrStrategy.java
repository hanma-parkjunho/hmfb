package hmfb.core.idgnr.strategy;

/**

 * @FileName : IdGnrStrategy.java

 * @작성자 : 송원호

 * @작성일 : 2023. 01. 16 

 * @프로그램 설명 : Id Generation 정책 Interface 클래스

 * @변경이력 :

 */

public interface IdGnrStrategy {

	/**
	
	  * @Method Name : makeId
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2023. 01. 16 
	
	  * @Method 설명 : 정책을 담은 아이디 생성하여 결과 리턴
	
	  * @변경이력 : 
	
	  */
    String makeId(String originalId);

}
