package hmfb.core.constants;

import static hmfb.core.utils.MessageParserHelper.fillAfterSpace;
import static hmfb.core.utils.MessageParserHelper.fillBeforeZeroSpace;

public enum MessageFieldType {
    NUMERIC {
        @Override
        public String fill(String text, int byteLimit) {
            return fillBeforeZeroSpace(text, byteLimit);
        }
    },
    ALPHABET {
        @Override
        public String fill(String text, int byteLimit) {
            return fillAfterSpace(text, byteLimit);
        }
    },
    GRP_CNT {
        @Override
        public String fill(String text, int byteLimit) {
            return fillBeforeZeroSpace(text, byteLimit);
        }
    };

    public abstract String fill(final String text, final int byteLimit);
}
