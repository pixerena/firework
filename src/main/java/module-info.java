/**
 * This is the main module for the Firework framework.
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
}