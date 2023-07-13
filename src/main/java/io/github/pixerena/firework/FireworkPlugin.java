package io.github.pixerena.firework;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import io.github.classgraph.ClassGraph;
import io.github.pixerena.firework.event.EventListenerManager;
import io.github.pixerena.firework.plugin.component.ComponentManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is the main class of the Firework framework and the entrypoint for the plugin.
 * <p>
 * It will scan the classpath for classes that implements common Bukkit interfaces such as {@link org.bukkit.event.Listener}
 * and register them in the {@link PluginManager}.
 * <p>
 * Every plugin that uses Firework must extend this class. The constructor of this class receives a list of packages
 * that will be scanned for classes that implements common Bukkit interfaces.
 * <p>
 * The Firework framework uses Guice for dependency injection. The {@link #providesAdditionalModules()} method can be
 * overridden to provide additional modules to the Guice injector.
 * <p>
 * Noted that the {@link FireworkPlugin} overrides the {@link JavaPlugin#onLoad()}, {@link JavaPlugin#onEnable()} and
 * {@link JavaPlugin#onDisable()} methods. If you override any of these methods, you must call the super method.
 */
public class FireworkPlugin extends JavaPlugin {

    private Logger logger;

    private final Set<ComponentManager> componentManagers = new HashSet<>();
    private final String[] scannedPackages;

    private Injector injector;

    /**
     * Creates a new instance of {@link FireworkPlugin}.
     * @param scannedPackages The packages that will be scanned for classes that implements common Bukkit interfaces.
     */
    protected FireworkPlugin(String... scannedPackages) {
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

    /**
     * This method can be overridden to provide additional modules to the Guice injector.
     * @return A collection of additional modules.
     */
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
