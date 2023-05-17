package hmfb.core.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**

 * @FileName : MenuAuthDto.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : 메뉴권한 DTO 클래스

 * @변경이력 :

 */

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Setter
public class MenuAuthDto {

    private String menuSeqNo;
    private String authDvcd;
    private String regDt;
    private String regTm;
    private String updDt;
    private String updTm;

}
