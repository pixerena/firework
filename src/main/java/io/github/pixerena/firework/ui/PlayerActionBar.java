package io.github.pixerena.firework.ui;

import io.github.pixerena.firework.internal.ui.UI;
import io.github.pixerena.firework.reactive.Memo;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * PlayerActionBar is a UI component that can be used to show dynamic and persistent action bar to players.
 *
 * <p>
 * The content of action bar is a {@link Memo} of {@link Component}, which means it can be changed dynamically.
 * The players that will receive the action bar is a {@link Memo} of {@link List} of {@link Player}, which can be
 * set to feet your needs. Note that you can't directly modify the list returned by {@link Memo#get()}, you should
 * use {@link io.github.pixerena.firework.reactive.Signal#set(Object)} and set a new copy of list instead.
 * </p>
 *
 * <pre>
 * {@literal @}UIComponent
 * public class ExampleActionBar extends PlayerActionBar {
 *    {@code private final Signal<Component> content = Signal.create(Component.text("Initial action bar", NamedTextColor.GOLD));}
 *    {@code private final List<Player> players = new ArrayList<>();}
 *    {@code private final Signal<List<Player>> playerSignal = Signal.create(List.copyOf(players));}
 *
 *    public ExampleActionBar() {
 *        // ...
 *    }
 *
 *    {@literal @}Override
 *    public{@code Memo<Component>} getContent() {
 *        return content;
 *    }
 *
 *    {@literal @}Override
 *    public{@code Memo<List<Player>>} getPlayers() {
 *        return playerSignal;
 *    }
 * }
 * </pre>
 *
 * @since 0.5.0
 */
@UI
public abstract class PlayerActionBar {
    /**
     * Get the content of action bar.
     * @return the content of action bar
     *
     * @since 0.5.0
     */
    public abstract Memo<Component> getContent();

    /**
     * Get the players that will receive the action bar.
     * @return the players that will receive the action bar in an immutable list
     *
     * @since 0.5.0
     */
    public abstract Memo<List<Player>> getPlayers();
}
