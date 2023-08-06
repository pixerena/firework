package io.github.pixerena.firework.internal.event;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.pixerena.firework.event.EventListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

@Singleton
class DefaultEventListenerManager implements EventListenerManager {
    private final JavaPlugin plugin;
    private final PluginManager pluginManager;
    private final Set<Listener> eventListeners;

    @Inject
    public DefaultEventListenerManager(JavaPlugin plugin, PluginManager pluginManager, @EventListener Set<Listener> eventListeners) {
        this.plugin = plugin;
        this.pluginManager = pluginManager;
        this.eventListeners = eventListeners;
    }

    @Override
    public void registerEventListeners() {
        for (var listener: eventListeners) {
            pluginManager.registerEvents(listener, plugin);
        }
    }
}
