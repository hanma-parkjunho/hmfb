package hmfb.core.utils;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import hmfb.core.constants.InterfaceMapping;
import hmfb.core.dto.F0100100Dto;
import hmfb.core.dto.F0100200Dto;
import hmfb.core.dto.F0100300Dto;
import hmfb.core.dto.F0200200Dto;
import hmfb.core.dto.F0200300Dto;
import hmfb.core.dto.F0200400Dto;
import hmfb.core.dto.F0300100Dto;
import hmfb.core.dto.F0300200Dto;
import hmfb.core.dto.F0300300Dto;
import hmfb.core.dto.F0300400Dto;
import hmfb.core.dto.F0300500Dto;
import hmfb.core.dto.F0400100Dto;
import hmfb.core.dto.F0500100Dto;
import hmfb.core.dto.F0500200Dto;
import hmfb.core.dto.F0500300Dto;
import hmfb.core.dto.F0600100Dto;
import hmfb.core.dto.F0600200Dto;
import hmfb.core.dto.F1000100Dto;
import hmfb.core.dto.F1000200Dto;
import hmfb.core.dto.F2000101Dto;
import hmfb.core.dto.F2000550Dto;
import hmfb.core.dto.F2000850Dto;
import hmfb.core.dto.F3000101Dto;
import hmfb.core.dto.F3000201Dto;
import hmfb.core.dto.F4000101Dto;
import hmfb.core.dto.F4000201Dto;
import hmfb.core.dto.F6000101Dto;
import hmfb.core.dto.F7000101Dto;
import hmfb.core.dto.F7000200Dto;
import hmfb.core.service.BaseMessage;

public class MessageFactoryHelper {
    private static final Map<InterfaceMapping, Supplier<BaseMessage>> FACTORY;
    static {
        FACTORY = new HashMap<>();
        
        FACTORY.put(InterfaceMapping.INF_FIRM_01, F0100100Dto::new);
        FACTORY.put(InterfaceMapping.INF_FIRM_02, F0100200Dto::new);
        FACTORY.put(InterfaceMapping.INF_FIRM_03, F0100300Dto::new);
        FACTORY.put(InterfaceMapping.INF_FIRM_04, F0200200Dto::new);
        FACTORY.put(InterfaceMapping.INF_FIRM_05, F0200300Dto::new);
        FACTORY.put(InterfaceMapping.INF_FIRM_06, F0200400Dto::new);
        FACTORY.put(InterfaceMapping.INF_FIRM_07, F0300100Dto::new);
        FACTORY.put(InterfaceMapping.INF_FIRM_08, F0300200Dto::new);
        FACTORY.put(InterfaceMapping.INF_FIRM_09, F0300300Dto::new);
        FACTORY.put(InterfaceMapping.INF_FIRM_10, F0300400Dto::new);
        FACTORY.put(InterfaceMapping.INF_FIRM_11, F0300500Dto::new);
        FACTORY.put(InterfaceMapping.INF_FIRM_12, F0400100Dto::new);
        FACTORY.put(InterfaceMapping.INF_FIRM_13, F0500100Dto::new);
        FACTORY.put(InterfaceMapping.INF_FIRM_14, F0500200Dto::new);
        FACTORY.put(InterfaceMapping.INF_FIRM_15, F0500300Dto::new);
        FACTORY.put(InterfaceMapping.INF_FIRM_16, F0600100Dto::new);
        FACTORY.put(InterfaceMapping.INF_FIRM_17, F0600200Dto::new);
        
        // 지우면 안될것같음
        
        FACTORY.put(InterfaceMapping.INF_FIRM_18, F1000100Dto::new); // INF_FIRM_18 -> 업무개시
        FACTORY.put(InterfaceMapping.INF_FIRM_19, F1000200Dto::new); // INF_FIRM_19 -> 업무종료
        FACTORY.put(InterfaceMapping.INF_FIRM_20, F2000101Dto::new); // INF_FIRM_20 -> 당,타행 지급(자금)이체
        FACTORY.put(InterfaceMapping.INF_FIRM_21, F2000550Dto::new); // INF_FIRM_21 -> 자동이체 신청
        FACTORY.put(InterfaceMapping.INF_FIRM_22, F2000850Dto::new); // INF_FIRM_22 -> 자동이체 청구
        FACTORY.put(InterfaceMapping.INF_FIRM_23, F7000101Dto::new); // INF_FIRM_23 -> 지급이체(자금이체) 처리결과조회
        FACTORY.put(InterfaceMapping.INF_FIRM_24, F6000101Dto::new); // INF_FIRM_24 -> 당, 타행 예금주 성명조회        
        FACTORY.put(InterfaceMapping.INF_FIRM_25, F3000101Dto::new); // INF_FIRM_25 -> 타행이체결과 불능분 통지
        FACTORY.put(InterfaceMapping.INF_FIRM_26, F3000201Dto::new); // INF_FIRM_26 -> 타행이체결과 결번요구
        FACTORY.put(InterfaceMapping.INF_FIRM_27, F7000200Dto::new); // INF_FIRM_27 -> 모계좌 잔액조회
        FACTORY.put(InterfaceMapping.INF_FIRM_28, F4000101Dto::new); // INF_FIRM_28 -> 예금거래(교환어음) 명세통지
        FACTORY.put(InterfaceMapping.INF_FIRM_29, F4000201Dto::new); // INF_FIRM_29 -> 예금거리(교환어음) 명세 결번요구
    
}

    public static BaseMessage createMessage(final InterfaceMapping messageType) {
        return Optional.ofNullable(FACTORY.get(messageType))
                .map(Supplier::get)
                .orElseThrow(() -> new IllegalArgumentException("해당 Message Type 이 존재하지 않습니다. : " + messageType));
    }
}
