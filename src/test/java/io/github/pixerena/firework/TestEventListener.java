package io.github.pixerena.firework;

import io.github.pixerena.firework.event.EventListener;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@EventListener
public class TestEventListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage(Component.text("Hello " + event.getPlayer().getName()));
    }
}
