package org.veritasopher.boostauth.core.exception;

import org.springframework.lang.Nullable;

import java.util.function.Supplier;

/**
 * Assert Util for Exception Handling
 *
 * @author Yepeng Ding
 */
public class Assert {

    public static <X extends Throwable> void notNull(@Nullable Object object, Supplier<? extends X> exceptionSupplier) throws X {
        if (object == null) {
            throw exceptionSupplier.get();
        }
    }

    public static <X extends Throwable> void isTrue(boolean expression, Supplier<? extends X> exceptionSupplier) throws X {
        if (!expression) {
            throw exceptionSupplier.get();
        }
    }

}
