package org.veritasopher.boostauth.core.exception;

/**
 * Base Exception
 *
 * @author Yepeng Ding
 */
public class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
