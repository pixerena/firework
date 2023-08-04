package io.github.pixerena.firework.reactive;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.Objects;

public class TestEffect {
    private static final Logger logger = LoggerFactory.getLogger(TestEffect.class);
    @Test
    void effect() {
        var count = Signal.create(0);
        Effect.create(() -> logger.info(() -> Objects.requireNonNull(count.get()).toString()));
        count.set(5);
        count.set(10);
    }
}
