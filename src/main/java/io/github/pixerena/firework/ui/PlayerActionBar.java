package io.github.pixerena.firework.ui;

import io.github.pixerena.firework.internal.ui.UI;
import io.github.pixerena.firework.reactive.Memo;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

@UI
public abstract class PlayerActionBar {
    public abstract Memo<Component> getContent();
    public abstract Memo<List<Player>> getPlayers();
}
