package io.github.pixerena.firework.internal.ui.sidebar;

import com.google.inject.Inject;
import io.github.pixerena.firework.lifecycle.OnDisable;
import io.github.pixerena.firework.lifecycle.OnFirstTick;
import io.github.pixerena.firework.reactive.Effect;
import io.github.pixerena.firework.ui.Sidebar;
import net.kyori.adventure.text.Component;
import org.bukkit.Server;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@io.github.pixerena.firework.inject.Component
public class SidebarManager {
    private static final String DEFAULT_OBJECTIVE_NAME = SidebarManager.class.getCanonicalName() + ".DEFAULT_OBJECTIVE_NAME";

    private final List<Score> scores = new ArrayList<>();
    private final Server server;
    private final Logger logger;

    private Sidebar sidebar;

    private Scoreboard scoreboard;
    private Objective objective;

    @Inject
    SidebarManager(Server server, Logger logger, Optional<Sidebar> sidebar) {
        this.server = server;
        this.logger = logger;
        sidebar.ifPresent(value -> this.sidebar = value);
    }

    @OnFirstTick
    @SuppressWarnings("unused")
    public void onFirstTick() {
        if (sidebar != null) {
            logger.info("Find sidebar implementation, start managing it");
            scoreboard = server.getScoreboardManager().getMainScoreboard();
            manageScoreboard(scoreboard);
        }
    }

    @OnDisable
    @SuppressWarnings("unused")
    public void onDisable() {
        if (objective != null) {
            logger.info("Unregister sidebar from server");
            objective.unregister();
        }
    }

    private void manageScoreboard(@NotNull Scoreboard scoreboard) {
        objective = scoreboard.getObjective(DEFAULT_OBJECTIVE_NAME);
        if (objective == null) {
            objective = scoreboard.registerNewObjective(DEFAULT_OBJECTIVE_NAME, Criteria.create(DEFAULT_OBJECTIVE_NAME), Component.empty());
        }

        Effect.create(this::renderTitle);
        Effect.create(this::renderContent);
        Effect.create(this::onDisplayedUpdate);
    }

    private void renderTitle() {
        objective.displayName(sidebar.getTitle());
    }

    private void renderContent() {
        // Remove old scores
        scores.forEach(score -> scoreboard.resetScores(score.getEntry()));

        var emptyCount = 0;
        var content = sidebar.getContent();
        for (var i = 0; i < content.size(); i++) {
            var entity = content.get(i);
            if (entity.isEmpty()) {
                entity = " ".repeat(emptyCount);
                emptyCount++;
            }
            var score = objective.getScore(entity);
            score.setScore(content.size() - i);
            scores.add(score);
        }
    }

    private void onDisplayedUpdate() {
        if (sidebar.isDisplayed()) objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        else objective.setDisplaySlot(null);
    }
}
