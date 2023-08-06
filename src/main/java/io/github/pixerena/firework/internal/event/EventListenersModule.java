package io.github.pixerena.firework.internal.event;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import io.github.pixerena.firework.event.EventListener;
import org.bukkit.event.Listener;

import java.util.List;

class EventListenersModule extends AbstractModule {
    private final List<Class<Listener>> eventListenerClasses;

    public EventListenersModule(ScanResult scanResult) {
        // Load event listener classes
        var eventListenerClassInfoList = scanResult.getClassesWithAnnotation(EventListener.class).filter(ClassInfo::isStandardClass);
        eventListenerClasses = eventListenerClassInfoList.loadClasses(Listener.class);
    }

    @Override
    protected void configure() {
        var listenerBinder = Multibinder.newSetBinder(binder(), Listener.class, EventListener.class);
        for (var clazz: eventListenerClasses) {
            listenerBinder.addBinding().to(clazz).in(Scopes.SINGLETON);
        }
    }
}
