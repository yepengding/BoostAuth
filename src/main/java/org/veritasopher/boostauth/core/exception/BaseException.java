package org.veritasopher.boostauth.core.exception;

import org.veritasopher.boostauth.core.dictionary.ErrorCode;
import org.veritasopher.boostauth.core.dictionary.ResponseCode;

/**
 * Base Exception
 *
 * @author Yepeng Ding
 */
public class BaseException extends RuntimeException {

    protected int code = ResponseCode.FAILURE.getValue();

    public BaseException(String message) {
        super(message);
    }

    public BaseException(ErrorCode code, String message) {
        super(message);
        this.code = code.getValue();
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
