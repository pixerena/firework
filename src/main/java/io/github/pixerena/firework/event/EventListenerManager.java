package io.github.pixerena.firework.event;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import io.github.pixerena.firework.plugin.component.ComponentManager;
import io.github.pixerena.firework.plugin.component.ComponentModule;
import io.github.classgraph.ScanResult;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * This class is responsible for managing the {@link Listener}.
 * It will scan the classpath for classes that implements {@link Listener} and
 * register them in the {@link PluginManager}.
 */
public class EventListenerManager implements ComponentManager {
    private final List<Class<Listener>> eventListenersClasses;

    /**
     * Creates a new instance of {@link EventListenerManager}.
     * @param scanResult The result of the classpath scan.
     */
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
