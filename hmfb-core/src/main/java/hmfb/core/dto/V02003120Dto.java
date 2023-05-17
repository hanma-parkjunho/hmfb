package hmfb.core.dto;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import hmfb.core.service.BaseMessage;
import lombok.Data;

@Data
public class V02003120Dto implements BaseMessage{
    
    @FixedString(order = 1, type = MessageFieldType.ALPHABET, value = 8)
    private String RCPMNY_INSTT_CODE;
    @FixedString(order = 2, type = MessageFieldType.ALPHABET, value = 16)
    private String RCPMNY_ACNUT_NO;
    @FixedString(order = 3, type = MessageFieldType.ALPHABET, value = 20)
    private String RCPMNY_ACNUT_NM;
    @FixedString(order = 4, type = MessageFieldType.ALPHABET, value = 8)
    private String DEFRAY_INSTT_CODE;
    @FixedString(order = 5, type = MessageFieldType.ALPHABET, value = 16)
    private String DEFRAY_ACNUT_NO;
    @FixedString(order = 6, type = MessageFieldType.ALPHABET, value = 20)
    private String DEFRAY_ACNUT_NM;
    @FixedString(order = 7, type = MessageFieldType.ALPHABET, value = 16)
    private String DEFRAY_ACNUT_PASSWORD;
    @FixedString(order = 8, type = MessageFieldType.ALPHABET, value = 20)
    private String SECRET_CRD;
    @FixedString(order = 9, type = MessageFieldType.NUMERIC, value = 13)
    private String DELNG_AMOUNT;
    @FixedString(order = 10, type = MessageFieldType.NUMERIC, value = 13)
    private String DELNG_AMOUNT_CASH;
    @FixedString(order = 11, type = MessageFieldType.NUMERIC, value = 13)
    private String DELNG_AMOUNT_UNSETL_CHECK;
    @FixedString(order = 12, type = MessageFieldType.ALPHABET, value = 1)
    private String BLCE_SMBOL;
    @FixedString(order = 13, type = MessageFieldType.NUMERIC, value = 13)
    private String BLCE;
    @FixedString(order = 14, type = MessageFieldType.NUMERIC, value = 13)
    private String UNSETL_BHF_BLCE;
    @FixedString(order = 15, type = MessageFieldType.NUMERIC, value = 28)
    private String FEE;
    @FixedString(order = 16, type = MessageFieldType.ALPHABET, value = 6)
    private String BIL_EXCHNG_CODE;
    @FixedString(order = 17, type = MessageFieldType.ALPHABET, value = 20)
    private String REFRN_NO;
    @FixedString(order = 18, type = MessageFieldType.ALPHABET, value = 4)
    private String CANCL_PRVONSH_CODE;
    @FixedString(order = 19, type = MessageFieldType.ALPHABET, value = 87)
    private String ACNT_PROCESS_INFO;
    @FixedString(order = 20, type = MessageFieldType.ALPHABET, value = 60)
    private String MS_TRACK_DATA;
    @FixedString(order = 21, type = MessageFieldType.ALPHABET, value = 5)
    private String FILLER;
    
}
