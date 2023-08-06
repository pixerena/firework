package io.github.pixerena.firework;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;
import io.github.classgraph.ClassGraph;
import io.github.pixerena.firework.internal.ComponentModule;
import io.github.pixerena.firework.internal.PaperModule;
import io.github.pixerena.firework.internal.event.EventListenerManager;
import io.github.pixerena.firework.internal.event.EventModule;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

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
 * Noted that the {@link FireworkPlugin} overrides the {@link JavaPlugin#onLoad()}, {@link JavaPlugin#onEnable()} and
 * {@link JavaPlugin#onDisable()} methods. If you override any of these methods, you must call the super method.
 */
public class FireworkPlugin extends JavaPlugin {

    private Logger logger;

    private final String[] scannedPackages;

    private Injector injector;
    private final Set<Module> modules = new HashSet<>();

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
        logger = getSLF4JLogger();

        logger.info("Scanning classes and resources info");
        try (var scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages(scannedPackages)
                .scan()
        ) {
            modules.addAll(Set.of(
                    new ComponentModule(scanResult),
                    new EventModule(scanResult)
            ));
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();

        // Create injector
        modules.add(new PaperModule(this));
        injector = Guice.createInjector(Stage.PRODUCTION, modules);
        logBindings(injector);

        // Register event listeners
        injector.getInstance(EventListenerManager.class).registerEventListeners();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void logBindings(Injector injector) {
        for (var entry: injector.getAllBindings().entrySet()) {
            logger.info(entry.getKey() + ": " + entry.getValue());
        }
    }
}
