package io.github.pixerena.firework.internal.ui.actionbar;

import com.google.inject.Inject;
import io.github.pixerena.firework.inject.Component;
import io.github.pixerena.firework.lifecycle.OnEnable;
import io.github.pixerena.firework.reactive.Effect;
import io.github.pixerena.firework.ui.PlayerActionBar;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@SuppressWarnings("unused")
public class PlayerActionBarManager {
    private static final long ACTION_BAR_UPDATE_INTERVAL = 20;

    private final Set<PlayerActionBar> playerActionBars;
    private final Map<PlayerActionBar, BukkitTask> renderingTasks = new HashMap<>();

    private final JavaPlugin plugin;
    private final Server server;
    private final Logger logger;

    @Inject
    public PlayerActionBarManager(Set<PlayerActionBar> playerActionBars, JavaPlugin plugin, Server server, Logger logger) {
        this.playerActionBars = playerActionBars;
        this.plugin = plugin;
        this.server = server;
        this.logger = logger;
    }

    @OnEnable
    public void onEnable() {
        if (!playerActionBars.isEmpty()) {
            logger.info("Find {} PlayerActionBar instances, start managing them", playerActionBars.size());
        }
        for (var playerActionBar: playerActionBars) {
            Effect.create(() -> this.renderActionBar(playerActionBar));
        }
    }

    private void renderActionBar(PlayerActionBar playerActionBar) {
        final var content = playerActionBar.getContent().get();
        final var players = playerActionBar.getPlayers().get();

        // Get previously BukkitTask and cancel it
        var previousTask = renderingTasks.remove(playerActionBar);
        if (previousTask != null) {
            previousTask.cancel();
            server.getScheduler().cancelTask(previousTask.getTaskId());
        }

        // Only show ActionBar when both content and players are not empty
        if (content != null && !content.equals(net.kyori.adventure.text.Component.empty()) &&
                players != null && !players.isEmpty()
        ) {
            // Show updated content
            for (var player: players) player.sendActionBar(content);

            // Schedule new BukkitTask to keep showing ActionBar
            var task = server.getScheduler().runTaskTimer(plugin, () -> {
                for (var player: players) player.sendActionBar(content);
            }, 0, ACTION_BAR_UPDATE_INTERVAL);
            renderingTasks.put(playerActionBar, task);
        }
    }
}
