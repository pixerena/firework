package io.github.pixerena.firework.reactive;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class DefaultSignal<T> implements Signal<T> {
    private @Nullable T value;
    private final Set<Reaction> subscriptions = new HashSet<>();

    private DefaultSignal(@Nullable T value) {
        this.value = value;
    }

    @Contract("_ -> new")
    static <T> @NotNull DefaultSignal<T> create(@Nullable T value) {
        return new DefaultSignal<>(value);
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

    private void subscribe(Reaction reaction) {
        subscriptions.add(reaction);
        reaction.getDependencies().add(subscriptions);
    }
}
