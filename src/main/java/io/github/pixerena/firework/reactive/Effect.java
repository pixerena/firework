package io.github.pixerena.firework.reactive;

import java.util.HashSet;
import java.util.Set;

public class Effect implements Runnable {
    private final Runnable fn;
    private final Set<Set<Effect>> dependencies = new HashSet<>();

    private Effect(Runnable fn) {
        this.fn = fn;
    }

    public static void create(Runnable fn) {
        var effect = new Effect(fn);
        effect.execute();
    }

    @Override
    public void run() {
        fn.run();
    }

    Set<Set<Effect>> getDependencies() {
        return dependencies;
    }

    private void execute() {
        var context = Context.getContext();

        cleanup(this);
        context.add(this);
        try {
            run();
        } finally {
            context.remove(context.size() - 1);
        }
    }

    private void cleanup(Effect effect) {
        for (var dep: effect.getDependencies()) {
            dep.remove(effect);
        }
        effect.getDependencies().clear();
    }
}
