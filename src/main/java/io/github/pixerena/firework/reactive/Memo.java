package io.github.pixerena.firework.reactive;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * A memo is a signal that is set when it is created.
 * @param <T> The type of the value.
 * @see Signal
 */
public interface Memo<T> {
    /**
     * Creates a readonly derived reactive memoized signal
     * @param <T> The type of the value.
     * @param fn The function to be executed and returns the value that will be stored in signal.
     * @return The memorized signal.
     */
    static <T> @NotNull Memo<T> create(Supplier<T> fn) {
        Signal<T> signal = Signal.create();
        Effect.create(() -> signal.set(fn.get()));
        return signal;
    }

    /**
     * Gets the value of the memo.
     * @return The value.
     */
    T get();
}
