package io.github.pixerena.firework.plugin.component;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;

import java.util.Collection;

public class ComponentModule<T> extends AbstractModule {
    private final Class<T> type;
    protected final Collection<Class<T>> componentsClasses;

    public ComponentModule(Class<T> type, Collection<Class<T>> componentsClasses) {
        this.type = type;
        this.componentsClasses = componentsClasses;
    }

    @Override
    protected void configure() {
        // Use reflection workaround to get the parameterized supertype
        Multibinder<T> componentBinder = Multibinder.newSetBinder(binder(), type);
        for (var componentClass: this.componentsClasses) {
            componentBinder.addBinding().to(componentClass).in(Singleton.class);
        }
    }
}
