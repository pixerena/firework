package io.github.pixerena.firework.internal.lifecycle;

import com.google.inject.AbstractModule;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import io.github.pixerena.firework.inject.Component;
import io.github.pixerena.firework.lifecycle.OnDisable;
import io.github.pixerena.firework.lifecycle.OnEnable;
import io.github.pixerena.firework.lifecycle.OnFirstTick;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LifecycleModule extends AbstractModule {
    private final LifecycleMethodRegistry registry;
    public LifecycleModule(ScanResult scanResult) {
        var componentClassInfoList = scanResult.getClassesWithAnnotation(Component.class).filter(ClassInfo::isStandardClass);
        var enableMethods = getClassAndMethodsWithAnnotation(scanResult, OnEnable.class, componentClassInfoList);
        var firstTickMethods = getClassAndMethodsWithAnnotation(scanResult, OnFirstTick.class, componentClassInfoList);
        var disableMethods = getClassAndMethodsWithAnnotation(scanResult, OnDisable.class, componentClassInfoList);
        this.registry = new LifecycleMethodRegistry(enableMethods, firstTickMethods, disableMethods);
    }

    @Override
    protected void configure() {
        bind(LifecycleMethodRegistry.class).toInstance(registry);
        bind(LifecycleNotifier.class).to(DefaultLifecycleNotifier.class);
    }

    private Map<Class<?>, List<Method>> getClassAndMethodsWithAnnotation(ScanResult scanResult, Class<? extends Annotation> annotationClass, ClassInfoList ...intersect) {
        Map<Class<?>, List<Method>> methodsMap = new HashMap<>();
        var classInfoList = scanResult.getClassesWithMethodAnnotation(annotationClass).intersect(intersect);
        for (var classInfo: classInfoList) {
            var methodInfoList = classInfo.getMethodInfo().filter(methodInfo -> methodInfo.hasAnnotation(annotationClass));
            List<Method> methods = new ArrayList<>();
            for (var methodInfo: methodInfoList) {
                var method = methodInfo.loadClassAndGetMethod();
                methods.add(method);
            }
            methodsMap.put(classInfo.loadClass(), methods);
        }
        return methodsMap;
    }
}
