package io.github.pixerena.firework.reactive;

public interface Effect {
    static void create(Runnable fn) {
        Reaction.create(fn);
    }
}
