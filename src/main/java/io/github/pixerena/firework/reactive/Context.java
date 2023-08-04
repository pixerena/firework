package io.github.pixerena.firework.reactive;

import java.util.ArrayList;
import java.util.List;

class Context {
    private static final List<Effect> context = new ArrayList<>();

    static List<Effect> getContext() {
        return context;
    }
}
