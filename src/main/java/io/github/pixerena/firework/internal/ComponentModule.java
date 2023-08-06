package io.github.pixerena.firework.internal;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
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
        for (var clazz: componentClasses) {
            bind(clazz).in(Scopes.SINGLETON);
        }
    }
}
