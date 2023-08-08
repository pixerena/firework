package io.github.pixerena.firework.ui;

import io.github.pixerena.firework.internal.ui.UI;
import io.github.pixerena.firework.reactive.Memo;
import net.kyori.adventure.text.Component;

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
 *    {@literal @}Inject
 *    public ExampleSidebar() {
 *        // ...
 *    }
 *
 *    {@literal @}Override
 *    public {@code Memo<Component>} getTitle() {
 *        return Memo.create(() -> Component.text("title"));
 *    }
 *
 *    {@literal @}Override
 *    public {@code Memo<Integer>} getLineCount() {
 *        return Memo.create(() -> 2);
 *    }
 *
 *    {@literal @}Override
 *    public {@code Memo<String>} getLine(int position) {
 *        if (position == 0) return Memo.create(() -> "first line");
 *        return Memo.create(() -> "second line");
 *    }
 *
 *    {@literal @}Override
 *    public {@code Memo<Boolean>} isDisplayed() {
 *        return Memo.create(() -> true);
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
     * @return the title of the sidebar
     * @since 0.6.0
     */
    public abstract Memo<Component> getTitle();

    /**
     * Returns the number of lines of the sidebar content.
     * @return the number of lines of the sidebar content
     * @since 0.6.0
     */
    public abstract Memo<Integer> getLineCount();

    /**
     * Returns the line of given position of the sidebar content.
     * @param position the position of the line to get
     * @return the line of given position of the sidebar content
     * @since 0.6.0
     */
    public abstract Memo<String> getLine(int position);

    /**
     * Returns whether the sidebar is displayed or not.
     * @return whether the sidebar is displayed or not
     * @since 0.6.0
     */
    public abstract Memo<Boolean> isDisplayed();
}
