package io.github.pixerena.firework.event;

import com.google.inject.BindingAnnotation;
import io.github.pixerena.firework.inject.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@BindingAnnotation
@Documented
@Component
public @interface EventListener {
}
