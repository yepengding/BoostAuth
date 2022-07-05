package org.veritasopher.boostauth.core.dictionary;

import lombok.Getter;

/**
 * Response Code
 *
 * @author Yepeng Ding
 */
public enum ResponseCode {

    SUCCESS(200), FAILURE(-1);

    @Getter
    private final int value;

    ResponseCode(int value) {
        this.value = value;
    }
}
