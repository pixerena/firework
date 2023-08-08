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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

@io.github.pixerena.firework.inject.Component
public class SidebarManager {
    private static final String DEFAULT_OBJECTIVE_NAME = SidebarManager.class.getCanonicalName() + ".DEFAULT_OBJECTIVE_NAME";

    private final ReentrantLock lock = new ReentrantLock(true);

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
        objective.displayName(sidebar.getTitle().get());
    }

    private void renderContent() {
        var lineCount = sidebar.getLineCount().get();
        AtomicInteger emptyCount = new AtomicInteger();
        for (var i = 0; i < lineCount; i++) {
            int position = i;
            Effect.create(() -> {
                lock.lock();
                try {
                    var line = sidebar.getLine(position).get();

                    // Remove the old score from list and reset it
                    if (position < scores.size()) {
                        var oldScore = scores.remove(position);
                        scoreboard.resetScores(oldScore.getEntry());
                    }

                    // Handle multiple empty lines
                    if (line.isEmpty()) {
                        line = " ".repeat(emptyCount.get());
                        emptyCount.getAndIncrement();
                    }

                    // Create new score and insert it to list
                    var score = objective.getScore(line);
                    score.setScore(lineCount - position);
                    scores.add(position, score);
                } finally {
                    lock.unlock();
                }
            });
        }
    }

    private void onDisplayedUpdate() {
        if (sidebar.isDisplayed().get()) objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        else objective.setDisplaySlot(null);
    }
}
