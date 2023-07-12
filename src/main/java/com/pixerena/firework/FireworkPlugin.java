package com.pixerena.firework;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.pixerena.firework.event.EventListenerManager;
import com.pixerena.firework.plugin.component.ComponentManager;
import io.github.classgraph.ClassGraph;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class FireworkPlugin extends JavaPlugin {

    private Logger logger;

    private final Set<ComponentManager> componentManagers = new HashSet<>();
    private final String[] scannedPackages;

    private Injector injector;

    public FireworkPlugin(String... scannedPackages) {
        this.scannedPackages = scannedPackages;
    }

    @Override
    public void onLoad() {
        super.onLoad();

        // Get SLF4J logger
        this.logger = getSLF4JLogger();

        this.logger.info("Scanning classes and resources info");
        try (var scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages(scannedPackages)
                .scan()
        ) {
            this.componentManagers.add(new EventListenerManager(scanResult));
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();

        this.injector = createInjector();

        for (var bindingEntry: injector.getAllBindings().entrySet()) {
            logger.debug(bindingEntry.getKey().toString());
        }

        // Notify ComponentManager plugin enable event
        for (var componentManager: this.componentManagers) componentManager.onPluginEnable(injector);
    }

    @Override
    public void onDisable() {
        super.onDisable();

        // Notify ComponentManager plugin disable event
        for (var componentManager: this.componentManagers) componentManager.onPluginDisable(injector);
    }

    protected Collection<Module> providesAdditionalModules() {
        return new ArrayList<>();
    }

    private Injector createInjector() {
        var modules = new ArrayList<Module>();

        // Add Paper plugin and server essential properties
        modules.add(new PaperModule(this));

        // Add ComponentManager managed modules
        for (var componentManager: this.componentManagers) modules.addAll(componentManager.provideModules());

        // Add user provided additional modules
        modules.addAll(this.providesAdditionalModules());

        return Guice.createInjector(modules.toArray(Module[]::new));
    }
}
