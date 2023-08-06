package io.github.pixerena.firework.ui;

import io.github.pixerena.firework.inject.Component;

import java.lang.annotation.*;

/**
 * Annotate a class with this annotation to create a UI component.
 *
 * <p>
 * Every class that extend the UI classes in this package must be annotated with this annotation.
 * </p>
 *
 * @since 0.4.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface UIComponent {
}
