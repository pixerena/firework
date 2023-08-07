package io.github.pixerena.firework.reactive;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

class Context {
    private static final List<Reaction> context = new CopyOnWriteArrayList<>();

    static List<Reaction> getContext() {
        return context;
    }
}
