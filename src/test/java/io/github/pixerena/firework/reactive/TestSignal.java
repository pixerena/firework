package io.github.pixerena.firework.reactive;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestSignal {
    @Test
    void testCreate() {
        Signal<Integer> count = Signal.create();
        assertNull(count.get());
    }

    @Test
    void testCreateWithDefault() {
        Signal<Integer> count = Signal.create(5);
        assertEquals(count.get(), 5);
    }

    @Test
    void testGetAndSet() {
        Signal<Integer> count = Signal.create(0);
        count.set(5);
        assertEquals(count.get(), 5);
    }
}
