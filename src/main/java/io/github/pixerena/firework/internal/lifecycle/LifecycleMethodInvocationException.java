package io.github.pixerena.firework.internal.lifecycle;

import java.lang.reflect.Method;

public class LifecycleMethodInvocationException extends RuntimeException {
    private final Class<?> componentClass;
    private final Method lifecycleMethod;

    LifecycleMethodInvocationException(Class<?> componentClass, Method lifecycleMethod, Throwable cause) {
        super(String.format("Failed to invoke lifecycle method in %s.%s", componentClass.getCanonicalName(), lifecycleMethod.getName()), cause);
        this.componentClass = componentClass;
        this.lifecycleMethod = lifecycleMethod;
    }

    Class<?> getComponentClass() {
        return componentClass;
    }

    Method getLifecycleMethod() {
        return lifecycleMethod;
    }
}
