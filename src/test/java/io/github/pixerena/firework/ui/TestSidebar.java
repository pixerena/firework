package io.github.pixerena.firework.ui;

import net.kyori.adventure.text.Component;

import java.util.List;

@UIComponent
public class TestSidebar extends Sidebar {

    @Override
    public Component getTitle() {
        return Component.text("title");
    }

    @Override
    public List<String> getContent() {
        return List.of("test content");
    }

    @Override
    public boolean isDisplayed() {
        return true;
    }
}
