package io.github.pixerena.firework;

import com.google.inject.Guice;
import com.google.inject.Module;
import com.google.inject.Stage;
import io.github.classgraph.ClassGraph;
import io.github.pixerena.firework.internal.ComponentModule;
import io.github.pixerena.firework.internal.PaperModule;
import io.github.pixerena.firework.internal.event.EventListenerManager;
import io.github.pixerena.firework.internal.event.EventModule;
import io.github.pixerena.firework.internal.lifecycle.LifecycleModule;
import io.github.pixerena.firework.internal.lifecycle.LifecycleNotifier;
import io.github.pixerena.firework.internal.ui.UIComponentModule;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import java.util.*;

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
 *
 * <pre>
 * public class ExamplePlugin extends FireworkPlugin {
 *    public ExamplePlugin() {
 *        super("my.plugin.package");
 *    }
 * }
 * </pre>
 */
public class FireworkPlugin extends JavaPlugin {
    private final List<String> scannedPackages = new ArrayList<>();
    private final Set<Module> modules = new HashSet<>();

    private LifecycleNotifier lifecycleNotifier;

    private Logger logger;

    /**
     * Creates a new instance of {@link FireworkPlugin}.
     * @param scannedPackages The packages that will be scanned for classes that implements common Bukkit interfaces.
     */
    protected FireworkPlugin(String... scannedPackages) {
        // Add default scanned packages
        this.scannedPackages.add("io.github.pixerena.firework.internal.ui");

        this.scannedPackages.addAll(Arrays.asList(scannedPackages));
    }

    @Override
    public void onLoad() {
        super.onLoad();

        // Get SLF4J logger
        logger = getSLF4JLogger();

        logger.info("Scanning classes and resources info");
        try (var scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages(scannedPackages.toArray(new String[0]))
                .scan()
        ) {
            modules.addAll(Set.of(
                    new ComponentModule(scanResult),
                    new EventModule(scanResult),
                    new LifecycleModule(scanResult),
                    new UIComponentModule(scanResult)
            ));
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();

        // Create injector
        modules.add(new PaperModule(this));
        var injector = Guice.createInjector(Stage.PRODUCTION, modules);

        // Register event listeners
        this.logger.info("Registering event listeners");
        injector.getInstance(EventListenerManager.class).registerEventListeners();

        // Retrieve lifecycle notifier
        lifecycleNotifier = injector.getInstance(LifecycleNotifier.class);

        // Schedule Bukkit task on first tick to notify firstTick event
        getServer().getScheduler().runTask(this, () -> lifecycleNotifier.notifyServerFirstTick());

        // Notify onEnable event
        lifecycleNotifier.notifyPluginEnable();
    }

    @Override
    public void onDisable() {
        // Notify onDisable event
        lifecycleNotifier.notifyPluginDisable();
    }
}
