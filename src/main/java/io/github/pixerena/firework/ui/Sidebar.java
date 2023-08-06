package io.github.pixerena.firework.ui;

import io.github.pixerena.firework.internal.ui.UI;
import net.kyori.adventure.text.Component;

import java.util.List;

/**
 * Inherit this class to create a sidebar.
 *
 * <p>
 * The sidebar is a component that is displayed on the right side of the screen.
 * It utilizes the {@link org.bukkit.scoreboard.Scoreboard} API to display the sidebar.
 * </p>
 *
 * <p>
 * To create a sidebar, you must create a class that inherits this class and annotate it with {@link UIComponent}.
 * Every abstract method in this class can receive a reactive value. Therefore, every time the reactive value changes,
 * the sidebar will be updated.
 * </p>
 *
 * <pre>
 * {@literal @}UIComponent
 * public class ExampleSidebar extends Sidebar {
 *    {@code private final Signal<String> content = Signal.of("Hello World");}
 *
 *    {@literal @}Override
 *    public Component getTitle() {
 *        return Component.text("Example Sidebar");
 *    }
 *
 *    {@literal @}Override
 *    public{@code List<String>} getContent() {
 *        return List.of(content.get());
 *    }
 *
 *    {@literal @}Override
 *    public boolean isDisplayed() {
 *    return true;
 *    }
 * }
 * </pre>
 *
 * @since 0.4.0
 */
@UI
public abstract class Sidebar {
    /**
     * Returns the title of the sidebar.
     *
     * @return the title of the sidebar
     */
    public abstract Component getTitle();

    /**
     * Returns the content of the sidebar.
     *
     * @return the content of the sidebar
     */
    public abstract List<String> getContent();

    /**
     * Returns whether the sidebar is displayed or not.
     *
     * @return whether the sidebar is displayed or not
     */
    public abstract boolean isDisplayed();
}
