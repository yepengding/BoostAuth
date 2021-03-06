package org.veritasopher.boostauth.core.exception;

import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

/**
 * Assert Util for Exception Handling
 *
 * @author Yepeng Ding
 */
public class Assert {
    public static void isNull(@Nullable Object object, String message) {
        if (object != null) {
            throw new SystemException(message);
        }
    }

    public static void isNull(@Nullable Object object, int code, String message) {
        if (object != null) {
            throw new SystemException(code, message);
        }
    }

    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new SystemException(message);
        }
    }

    public static void notNull(@Nullable Object object, int code, String message) {
        if (object == null) {
            throw new SystemException(code, message);
        }
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new SystemException(message);
        }
    }

    public static void isTrue(boolean expression, int code, String message) {
        if (!expression) {
            throw new SystemException(code, message);
        }
    }

    public static void notEmpty(@Nullable Object[] array, String message) {
        if (ObjectUtils.isEmpty(array)) {
            throw new SystemException(message);
        }
    }
}
