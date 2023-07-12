package com.pixerena.firework.event;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Types;
import com.pixerena.firework.plugin.component.ComponentManager;
import com.pixerena.firework.plugin.component.ComponentModule;
import io.github.classgraph.ScanResult;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class EventListenerManager implements ComponentManager<Listener> {
    private final List<Class<Listener>> eventListenersClasses;

    public EventListenerManager(ScanResult scanResult) {
        this.eventListenersClasses = scanResult.getClassesImplementing(Listener.class)
                .loadClasses(Listener.class);
    }

    @Override
    public @NotNull Collection<Module> provideModules() {
        return List.of(new ComponentModule<>(Listener.class, this.eventListenersClasses));
    }

    @Override
    public void onPluginEnable(@NotNull Injector injector) {
        var listenerKey = Key.get(new TypeLiteral<Set<Listener>>(){});
        var listeners = injector.getInstance(listenerKey);
        var plugin = injector.getInstance(JavaPlugin.class);
        var pluginManager = injector.getInstance(PluginManager.class);

        for (var listener: listeners) {
            pluginManager.registerEvents(listener, plugin);
        }
    }

    @Override
    public void onPluginDisable(@NotNull Injector injector) {

    }
}
