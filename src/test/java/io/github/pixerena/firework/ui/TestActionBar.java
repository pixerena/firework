package io.github.pixerena.firework.ui;

import com.google.inject.Inject;
import io.github.pixerena.firework.event.EventListener;
import io.github.pixerena.firework.reactive.Memo;
import io.github.pixerena.firework.reactive.Signal;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EventListener
@UIComponent
public class TestActionBar extends PlayerActionBar implements Listener {
    private final Signal<Component> content = Signal.create(Component.text("Initial action bar", NamedTextColor.GOLD));
    private final List<Player> players = new ArrayList<>();
    private final Signal<List<Player>> playerSignal = Signal.create(List.copyOf(players));

    private final JavaPlugin plugin;
    private final Server server;

    @Inject
    public TestActionBar(JavaPlugin plugin, Server server) {
        this.plugin = plugin;
        this.server = server;
    }

    @Override
    public Memo<Component> getContent() {
        return content;
    }

    @Override
    public Memo<List<Player>> getPlayers() {
        return playerSignal;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
       players.add(event.getPlayer());

       content.set(Component.text("Hello " + event.getPlayer().getName(), NamedTextColor.GOLD));
       playerSignal.set(List.copyOf(players));

       server.getScheduler().runTaskTimer(plugin, () -> content.set(Component.text("Current time: " + new SimpleDateFormat("hh:mm:ss").format(new Date()))), 5 * 20, 5 * 20);
    }
}
