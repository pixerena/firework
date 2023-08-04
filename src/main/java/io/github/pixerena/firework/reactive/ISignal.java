package io.github.pixerena.firework.reactive;

import org.jetbrains.annotations.Nullable;

public interface ISignal<T> {
    @Nullable T get();
    void set(@Nullable T value);
}
