package org.veritasopher.boostauth.core.exception.type;

import org.veritasopher.boostauth.core.dictionary.ErrorCode;
import org.veritasopher.boostauth.core.exception.BaseException;

public class BadRequestException extends BaseException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(ErrorCode code, String message) {
        super(code, message);
    }

}
