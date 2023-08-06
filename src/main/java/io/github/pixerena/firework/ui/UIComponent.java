package io.github.pixerena.firework.ui;

import io.github.pixerena.firework.inject.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface UIComponent {
}
