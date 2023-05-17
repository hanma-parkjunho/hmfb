package hmfb.core.utils;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

@Component
public class SequenceUtil {
    private final static AtomicInteger atomicInteger = new AtomicInteger();
    private final static int minValue = 1;
    private final static int maxValue = 999999;

    public static String getNextValue() {
        int nextVal = atomicInteger.updateAndGet(n -> (n >= maxValue) ? minValue : n+1);
        return MessageParserHelper.fillBeforeZeroSpace(Integer.toString(nextVal), 6);
    }
}
