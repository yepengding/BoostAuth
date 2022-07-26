package org.veritasopher.boostauth.core.dictionary;

import lombok.Getter;

/**
 * Error Code
 *
 * @author Yepeng Ding
 */
public enum ErrorCode {

    NOT_EXIST(-1000), EXIST(-1001), UNAUTHENTICATED(-1002), UNAUTHORIZED(-1003);

    @Getter
    private final int value;

    ErrorCode(int value) {
        this.value = value;
    }

}
