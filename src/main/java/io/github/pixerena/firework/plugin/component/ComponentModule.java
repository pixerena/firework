package io.github.pixerena.firework.plugin.component;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;

import java.util.Collection;

/**
 * A generic Guice module that binds a set of consumer-provided components to a given type.
 * @param <T> The type of the components.
 * @since 0.1.0
 */
public class ComponentModule<T> extends AbstractModule {
    private final Class<T> type;
    private final Collection<Class<T>> componentsClasses;

    /**
     * Creates a new instance of the {@link ComponentModule}.
     * @param type The type of the components.
     * @param componentsClasses The classes of the components.
     * @since 0.1.0
     */
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
