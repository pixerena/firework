package io.github.pixerena.firework.ui;

import com.google.inject.Inject;
import io.github.pixerena.firework.lifecycle.OnFirstTick;
import io.github.pixerena.firework.reactive.Memo;
import io.github.pixerena.firework.reactive.Signal;
import net.kyori.adventure.text.Component;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@UIComponent
public class TestSidebar extends Sidebar {
    private final Signal<Date> date = Signal.create(new Date());
    private final List<String> content = List.of(
            "test content",
            "",
            "Another content",
            "",
            "More content"
    );

    private final JavaPlugin plugin;
    private final Server server;

    @Inject
    public TestSidebar(JavaPlugin plugin, Server server) {
        this.plugin = plugin;
        this.server = server;
    }

    @Override
    public Memo<Component> getTitle() {
        return Memo.create(() -> Component.text("title"));
    }

    @Override
    public Memo<Integer> getLineCount() {
        return Memo.create(() -> content.size() + 1);
    }

    @Override
    public Memo<String> getLine(int position) {
        if (position == 0) return Memo.create(() -> new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(date.get()));
        return Memo.create(() -> content.get(position - 1));
    }

    @Override
    public Memo<Boolean> isDisplayed() {
        return Memo.create(() -> true);
    }

    @OnFirstTick
    public void onFirstTick() {
        server.getScheduler().runTaskTimer(plugin, () -> date.set(new Date()), 0, 20);
    }
}
