/**
 * This is the main module for the Firework framework.
 *
 * <p>
 * This module is responsible for providing the core functionality of the
 * Firework framework. We split each feature into its own package for ease of
 * use and to make it easier to understand the framework.
 * </p>
 *
 * @see io.github.pixerena.firework.FireworkPlugin
 */
@SuppressWarnings("requires-transitive-automatic")
module com.pixerena.firework {
    requires transitive com.google.guice;
    requires transitive net.kyori.adventure.text.logger.slf4j;
    requires transitive org.bukkit;
    requires transitive org.jetbrains.annotations;
    requires transitive org.slf4j;

    requires io.github.classgraph;

    exports io.github.pixerena.firework;
    exports io.github.pixerena.firework.inject;
    exports io.github.pixerena.firework.event;
    exports io.github.pixerena.firework.lifecycle;
    exports io.github.pixerena.firework.reactive;
}