package io.github.pixerena.firework.reactive;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A signal is a value that can be set and get.
 * It is used to create reactive values.
 * @param <T> The type of the value.
 */
public interface Signal<T> extends Memo<T> {
    /**
     * Creates a signal with no initial value, which will be set as null.
     * @param <T> The type of the value.
     * @return The signal.
     */
    static <T> @NotNull Signal<T> create() {
        return create(null);
    }

    /**
     * Creates a signal with an initial value.
     * @param value The initial value.
     * @param <T> The type of the value.
     * @return The signal.
     */
    static <T> @NotNull Signal<T> create(@Nullable T value) {
        return DefaultSignal.create(value);
    }

    /**
     * Sets the value of the signal.
     * @param value The value.
     */
    void set(@Nullable T value);
}
