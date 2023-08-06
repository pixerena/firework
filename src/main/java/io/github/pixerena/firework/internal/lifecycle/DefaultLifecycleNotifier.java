package io.github.pixerena.firework.internal.lifecycle;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.pixerena.firework.inject.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Singleton
class DefaultLifecycleNotifier implements LifecycleNotifier {
    private final Set<Object> components;
    private final LifecycleMethodRegistry methodRegistry;

    @Inject
    DefaultLifecycleNotifier(@Component Set<Object> components, LifecycleMethodRegistry methodRegistry) {
        this.components = components;
        this.methodRegistry = methodRegistry;
    }

    @Override
    public void notifyPluginEnable() throws LifecycleMethodInvocationException {
        invokeLifecycleMethod(methodRegistry.getEnableMethods());
    }

    @Override
    public void notifyServerFirstTick() throws LifecycleMethodInvocationException {
        invokeLifecycleMethod(methodRegistry.getFirstTickMethods());
    }

    @Override
    public void notifyPluginDisable() throws LifecycleMethodInvocationException {
        invokeLifecycleMethod(methodRegistry.getDisableMethods());
    }

    private void invokeLifecycleMethod(Map<Class<?>, List<Method>> methodsMap) throws LifecycleMethodInvocationException {
        for(var entry: methodsMap.entrySet()) {
            var clazz = entry.getKey();
            for (var component: components.toArray()) {
                if (component.getClass().equals(clazz)) {
                    for (var method: entry.getValue()) {
                        try {
                            method.invoke(component);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new LifecycleMethodInvocationException(clazz, method, e);
                        }
                    }
                    break;
                }
            }
        }
    }
}
