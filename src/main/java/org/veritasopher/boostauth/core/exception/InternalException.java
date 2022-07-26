package org.veritasopher.boostauth.core.exception;

import org.veritasopher.boostauth.core.dictionary.ResponseCode;

/**
 * Internal Exception
 *
 * @author Yepeng Ding
 */
public class InternalException extends BaseException {

    private int code = ResponseCode.FAILURE.getValue();

    public InternalException(String message) {
        super(message);
    }

    public InternalException(int code, String message) {
        super(message);
        this.code = code;
    }

    public InternalException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
