package io.github.pixerena.firework.ui;

import io.github.pixerena.firework.internal.ui.UI;
import net.kyori.adventure.text.Component;

import java.util.List;

@UI
public abstract class Sidebar {
    public abstract Component getTitle();
    public abstract List<String> getContent();
    public abstract boolean isDisplayed();
}
