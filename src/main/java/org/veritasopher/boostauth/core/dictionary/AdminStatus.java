package org.veritasopher.boostauth.core.dictionary;

import lombok.Getter;

/**
 * Administrator Status
 *
 * @author Yepeng Ding
 */
public enum AdminStatus {

    DISABLED(0), NORMAL(1), DELETED(-1);

    @Getter
    private final int value;

    AdminStatus(int value) {
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
