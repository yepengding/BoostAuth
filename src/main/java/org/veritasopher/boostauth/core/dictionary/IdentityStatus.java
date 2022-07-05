package org.veritasopher.boostauth.core.dictionary;

import lombok.Getter;

/**
 * Identity Status
 *
 * @author Yepeng Ding
 */
public enum IdentityStatus {

    NORMAL(0), PREREGISTER(1);

    @Getter
    private final int value;

    IdentityStatus(int value) {
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

    /**
     * Check whether a given value is not at this status
     *
     * @param value value
     * @return true if the given value is not at this status
     */
    public boolean isFalse(int value) {
        return this.value != value;
    }

}
