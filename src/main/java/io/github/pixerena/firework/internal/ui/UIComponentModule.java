package io.github.pixerena.firework.internal.ui;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.multibindings.OptionalBinder;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import io.github.pixerena.firework.ui.PlayerActionBar;
import io.github.pixerena.firework.ui.Sidebar;
import io.github.pixerena.firework.ui.UIComponent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class UIComponentModule extends AbstractModule {
    private final Map<Class<Object>, Class<Object>> uiComponentClasses = new HashMap<>();
    public UIComponentModule(@NotNull ScanResult scanResult) {
        var uiComponentClassInfoList = scanResult.getClassesWithAnnotation(UIComponent.class).filter(ClassInfo::isStandardClass);
        for (var uiComponentClassInfo: uiComponentClassInfoList) {
            var uiClassInfo = uiComponentClassInfo.getSuperclasses().filter(classInfo -> classInfo.hasAnnotation(UI.class)).get(0);
            uiComponentClasses.put(uiClassInfo.loadClass(Object.class), uiComponentClassInfo.loadClass(Object.class));
        }
    }

    @Override
    protected void configure() {
        // Create UI optional and multi binder
        OptionalBinder.newOptionalBinder(binder(), Sidebar.class);
        Multibinder<PlayerActionBar> playerActionBarMultibinder = Multibinder.newSetBinder(binder(), PlayerActionBar.class);

        for (var entry: uiComponentClasses.entrySet()) {
            if (entry.getKey().equals(PlayerActionBar.class)) {
                playerActionBarMultibinder.addBinding().to(entry.getValue().asSubclass(PlayerActionBar.class)).in(Scopes.SINGLETON);
            }
            else {
                bind(entry.getKey()).to(entry.getValue()).in(Scopes.SINGLETON);
            }
        }
    }
}
