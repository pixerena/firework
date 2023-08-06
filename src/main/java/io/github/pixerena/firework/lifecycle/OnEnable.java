package io.github.pixerena.firework.lifecycle;

import java.lang.annotation.*;

/**
 * Marks a method to be called when the plugin is enabled.
 *
 * <p>
 * The annotated method must be public, take no arguments and return void,
 * and must be contained in a class that is annotated with {@link io.github.pixerena.firework.inject.Component}.
 * </p>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OnEnable {
}
