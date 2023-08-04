package io.github.pixerena.firework.reactive;

import java.util.ArrayList;
import java.util.List;

class Context {
    private static final List<Reaction> context = new ArrayList<>();

    static List<Reaction> getContext() {
        return context;
    }
}
