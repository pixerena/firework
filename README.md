# Firework

![GitHub Workflow Status (with event)](https://img.shields.io/github/actions/workflow/status/pixerena/firework/gradle.yml?logo=github)
![Maven Central](https://img.shields.io/maven-central/v/io.github.pixerena/firework?label=maven%20central&color=blue)
![GitHub](https://img.shields.io/github/license/pixerena/firework)


A progressive Minecraft plugin framework for managing event listener with ease and building reactive UI.

> **Warning**
> Firework framework is under active development and all the API are subject to changes before reaching v1. Use at your own risk.

## Installation

Install firework with gradle

```kotlin
// build.gradle.kts

dependencies {
    implementation("io.github.pixerena:firework:0.6.0")
}
```

```groovy
// build.gradle

dependencies {
    implementation "io.github.pixerena:firework:0.6.0"
}
```



## Features

- [x] Dependency injection powered by [Guice](https://github.com/google/guice)
- [x] Auto registered Bukkit [event listeners](https://jd.papermc.io/paper/1.20/org/bukkit/event/Listener.html)
- [ ] Create custom command
- [x] Server and plugin lifecycle hooks
- [x] Reactive utilities inspired by [SolidJS](https://www.solidjs.com/)
- [x] Minecraft UI components
    - [x] Custom sidebar using [scoreboard](https://jd.papermc.io/paper/1.20/org/bukkit/scoreboard/Scoreboard.html)
    - [x] Reactive and persistent action bar
    - [ ] And much more ...



## Usage

```java
package com.example.plugin;

import io.github.pixerena.firework.FireworkPlugin;

public class ExamplePlugin extends FireworkPlugin {
   public ExamplePlugin() {
       super("com.example.plugin");
   }
}
```

To get started with firework framework, just extend the `FireworkPlugin` class and provide your project's root package to super constructor.

Then add the class to the `main` section of `plugin.yml`.

```yaml
name: ExamplePlugin
version: 1.0.0
main: com.example.plugin.ExamplePlugin
description: An example plugin
author: Pixerena
website: https://github.com/pixerena
api-version: '1.20'
```

### Component

Component is the basic building block for the DI system.
Every class marked with `@Component` annotation will be initialized as singleton and can be injected to another component's constructor.

```java
import com.google.inject.Inject;
import io.github.pixerena.firework.inject.Component;

@Component
public class ExampleComponent {
   private final AnotherComponent anotherCompo;
   
   @Inject
   public ExampleComponent(AnotherComponent anotherCompo) {
     this.anotherComponent = anotherComponent;
   }
}

@Component
public class AnotherComponent {
  // ...
  @Inject
  public AnotherComponent() {
     // ...
  }
}
```

### Event Listeners

Every event listeners should implement `Listener` interface and marked with `@EventListener` annotation.
Inherently, `@EventListener` is also a component, so it can inject other components to its constructor.

```java
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import io.github.pixerena.firework.event.EventListener;

@EventListener
public class ExampleListener implements Listener {
   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent event) {
        // ...
   }
}
```


## Documentation

- [Latest javadoc](https://javadoc.io/doc/io.github.pixerena/firework/latest/com.pixerena.firework/module-summary.html)

## License

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
