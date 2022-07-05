package org.veritasopher.boostauth.core.dictionary;

import lombok.Getter;

/**
 * Token Status
 *
 * @author Yepeng Ding
 */
public enum TokenStatus {

    NORMAL(0), INVALID(1);

    @Getter
    private final int value;

    TokenStatus(int value) {
        this.value = value;
    }

    /**
     * Check whether a given value is at this status
     *
     * @param value value
     * @return true if the given value is at this status
     */
    public boolean isTrue(int value) {
        return this.value == value;
    }
}
