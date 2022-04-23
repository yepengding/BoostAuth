package org.veritasopher.boostauth.core.exception;

import org.veritasopher.boostauth.core.dictionary.ResponseCode;

/**
 * System Exception
 *
 * @author Yepeng Ding
 */
public class SystemException extends BaseException {

    private int code = ResponseCode.FAILURE;

    public SystemException(String message) {
        super(message);
    }

    public SystemException(int code, String message) {
        super(message);
        this.code = code;
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
