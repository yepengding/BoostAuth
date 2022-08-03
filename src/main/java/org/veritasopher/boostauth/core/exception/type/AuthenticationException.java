package org.veritasopher.boostauth.core.exception.type;

import org.veritasopher.boostauth.core.dictionary.ErrorCode;
import org.veritasopher.boostauth.core.exception.BaseException;

/**
 * Authentication Exception
 *
 * @author Yepeng Ding
 */
public class AuthenticationException extends BaseException {

    public AuthenticationException(String message) {
        super(ErrorCode.UNAUTHENTICATED, message);
    }

    public AuthenticationException(ErrorCode code, String message) {
        super(code, message);
    }

}
