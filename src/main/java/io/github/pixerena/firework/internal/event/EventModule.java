package io.github.pixerena.firework.internal.event;

import com.google.inject.AbstractModule;
import io.github.classgraph.ScanResult;

public class EventModule extends AbstractModule {
    private final EventListenersModule eventListenersModule;

    public EventModule(ScanResult scanResult) {
        this.eventListenersModule = new EventListenersModule(scanResult);
    }

    @Override
    protected void configure() {
        install(eventListenersModule);
        bind(EventListenerManager.class).to(DefaultEventListenerManager.class);
    }
}
