package io.github.pixerena.firework.reactive;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Signal<T> implements ISignal<T> {
    private @Nullable T value;
    private final Set<Effect> subscriptions = new HashSet<>();

    private Signal(@Nullable T value) {
        this.value = value;
    }

    @Contract(" -> new")
    public static <T> @NotNull Signal<T> create() {
        return create(null);
    }

    @Contract("_ -> new")
    public static <T> @NotNull Signal<T> create(@Nullable T value) {
        return new Signal<>(value);
    }

    @Override
    public @Nullable T get() {
        var context = Context.getContext();

        if (!context.isEmpty()) {
            var running = context.get(context.size() - 1);
            subscribe(running);
        }

        return value;
    }

    @Override
    public void set(@Nullable T nextValue) {
        if (Objects.deepEquals(value, nextValue)) return;
        value = nextValue;

        for (var sub: subscriptions) {
            sub.run();
        }
    }

    private void subscribe(Effect effect) {
        subscriptions.add(effect);
        effect.getDependencies().add(subscriptions);
    }
}
