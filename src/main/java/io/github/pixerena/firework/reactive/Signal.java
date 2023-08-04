package io.github.pixerena.firework.reactive;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Signal<T> {
    static <T> @NotNull Signal<T> create() {
        return create(null);
    }

    static <T> @NotNull Signal<T> create(@Nullable T value) {
        return DefaultSignal.create(value);
    }

    @Nullable T get();
    void set(@Nullable T value);
}
