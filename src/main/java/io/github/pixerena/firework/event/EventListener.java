package io.github.pixerena.firework.event;

import com.google.inject.BindingAnnotation;
import io.github.pixerena.firework.inject.Component;

import java.lang.annotation.*;

/**
 * Marks a class as an event listener.
 *
 * <p>
 * Event listeners are classes that listen for events and handle them.
 * Every event listener must implement {@link org.bukkit.event.Listener} interface
 * and contains at least one method annotated with {@link org.bukkit.event.EventHandler}.
 * </p>
 *
 * <p>
 * Event listeners are automatically registered by the Firework framework,
 * and it inherits the {@link io.github.pixerena.firework.inject.Component} annotation.
 * </p>
 */
@Target({ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@BindingAnnotation
@Documented
@Component
public @interface EventListener {
}
