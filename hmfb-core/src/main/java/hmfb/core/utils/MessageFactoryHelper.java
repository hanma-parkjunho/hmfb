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
        
    }

    public static BaseMessage createMessage(final InterfaceMapping messageType) {
        return Optional.ofNullable(FACTORY.get(messageType))
                .map(Supplier::get)
                .orElseThrow(() -> new IllegalArgumentException("해당 Message Type 이 존재하지 않습니다. : " + messageType));
    }
}
