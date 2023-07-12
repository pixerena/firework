package com.pixerena.firework.plugin.component;

import com.google.inject.Injector;
import com.google.inject.Module;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface ComponentManager {
    @NotNull Collection<Module> provideModules();

    void onPluginEnable(@NotNull Injector injector);

    void onPluginDisable(@NotNull Injector injector);
}
