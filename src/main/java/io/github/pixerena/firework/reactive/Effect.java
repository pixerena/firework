package io.github.pixerena.firework.reactive;

/**
 * An effect is a function that is executed when it is created or its inner signal is changed.
 */
public interface Effect {
    /**
     * Creates an effect.
     * @param fn The function to be executed.
     */
    static void create(Runnable fn) {
        Reaction.create(fn);
    }
}
