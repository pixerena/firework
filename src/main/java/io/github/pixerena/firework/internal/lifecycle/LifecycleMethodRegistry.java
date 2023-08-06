package io.github.pixerena.firework.internal.lifecycle;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

class LifecycleMethodRegistry {
    private final Map<Class<?>, List<Method>> enableMethods;
    private final Map<Class<?>, List<Method>> firstTickMethods;
    private final Map<Class<?>, List<Method>> disableMethods;

    LifecycleMethodRegistry(Map<Class<?>, List<Method>> enableMethods, Map<Class<?>, List<Method>> firstTickMethods, Map<Class<?>, List<Method>> disableMethods) {
        this.enableMethods = enableMethods;
        this.firstTickMethods = firstTickMethods;
        this.disableMethods = disableMethods;
    }

    public Map<Class<?>, List<Method>> getEnableMethods() {
        return enableMethods;
    }

    public Map<Class<?>, List<Method>> getFirstTickMethods() {
        return firstTickMethods;
    }

    public Map<Class<?>, List<Method>> getDisableMethods() {
        return disableMethods;
    }
}
