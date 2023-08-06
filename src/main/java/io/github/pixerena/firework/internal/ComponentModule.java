package io.github.pixerena.firework.internal;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import io.github.pixerena.firework.inject.Component;

import java.util.List;

public class ComponentModule extends AbstractModule {
    private final List<Class<?>> componentClasses;

    public ComponentModule(ScanResult scanResult) {
        // Load component classes
        var componentClassInfoList = scanResult.getClassesWithAnnotation(Component.class).filter(ClassInfo::isStandardClass);
        componentClasses = componentClassInfoList.loadClasses();
    }

    @Override
    protected void configure() {
        Multibinder<Object> componentBinder = Multibinder.newSetBinder(binder(), Object.class, Component.class);

        for (var clazz: componentClasses) {
            bind(clazz).in(Scopes.SINGLETON);

            // Bind all components with @Component key
            componentBinder.addBinding().to(clazz).in(Scopes.SINGLETON);
        }
    }
}
