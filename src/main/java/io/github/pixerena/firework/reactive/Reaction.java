package io.github.pixerena.firework.reactive;

import java.util.HashSet;
import java.util.Set;

class Reaction implements Runnable {
    private final Runnable fn;
    private final Set<Set<Reaction>> dependencies = new HashSet<>();

    private Reaction(Runnable fn) {
        this.fn = fn;
    }

    static void create(Runnable fn) {
        var effect = new Reaction(fn);
        effect.execute();
    }

    @Override
    public void run() {
        fn.run();
    }

    Set<Set<Reaction>> getDependencies() {
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

    private void cleanup(Reaction reaction) {
        for (var dep: reaction.getDependencies()) {
            dep.remove(reaction);
        }
        reaction.getDependencies().clear();
    }
}
