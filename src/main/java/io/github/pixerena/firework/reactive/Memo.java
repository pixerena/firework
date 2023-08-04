package io.github.pixerena.firework.reactive;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public interface Memo {
    static <T> @NotNull Signal<T> create(Supplier<T> fn) {
        Signal<T> signal = Signal.create();
        Effect.create(() -> signal.set(fn.get()));
        return signal;
    }
}
