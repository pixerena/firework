package io.github.pixerena.firework.plugin.component;

import com.google.inject.Injector;
import com.google.inject.Module;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Provides a common interface for any firework component manager provide modules and listen to plugin lifecycle events.
 * @since 0.1.0
 */
public interface ComponentManager {
    /**
     * Provides a collection of modules to be installed in the Guice injector.
     * @return A collection of modules to be installed in the Guice injector.
     * @since 0.1.0
     */
    @NotNull Collection<Module> provideModules();

    /**
     * Called when the plugin is enabled.
     * @see org.bukkit.plugin.Plugin#onEnable()
     * @param injector The Guice injector.
     * @since 0.1.0
     */
    void onPluginEnable(@NotNull Injector injector);

    /**
     * Called when the plugin is disabled.
     * @see org.bukkit.plugin.Plugin#onDisable()
     * @param injector The Guice injector.
     * @since 0.1.0
     */
    void onPluginDisable(@NotNull Injector injector);
}
