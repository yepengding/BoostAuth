package org.veritasopher.boostauth.core.exception.type;

import org.veritasopher.boostauth.core.dictionary.ErrorCode;
import org.veritasopher.boostauth.core.exception.BaseException;

/**
 * Authorization Exception
 *
 * @author Yepeng Ding
 */
public class AuthorizationException extends BaseException {

    public AuthorizationException(String message) {
        super(ErrorCode.UNAUTHORIZED, message);
    }

}
