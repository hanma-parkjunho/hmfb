package hmfb.core.service;

import hmfb.core.utils.MessageParserHelper;

public interface BaseMessage {
    default String getMessage() {
        return MessageParserHelper.getMessage(this);
    }
}
