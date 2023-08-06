package io.github.pixerena.firework.inject;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.*;

/**
 * Annotation to mark a class as a component.
 *
 * <p>
 * Components are classes that are instantiated by the injector and are
 * used to provide functionality to the application. They are also
 * used to provide dependencies to other components.
 * </p>
 *
 * <p>
 * You can inject components into other components by using the
 * {@link com.google.inject.Inject} annotation on constructor.
 * Also note that components are all singletons, so you can inject
 * them into other components without worrying about creating
 * multiple instances.
 * </p>
 */
@Target({ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@BindingAnnotation
public @interface Component {
}
