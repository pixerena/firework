package io.github.pixerena.firework.lifecycle;

import com.google.inject.Inject;
import io.github.pixerena.firework.inject.Component;
import org.slf4j.Logger;

import java.util.Date;

@Component
public class TestLifecycle {
    private final Date instantiatedDate;

    private final Logger logger;
    @Inject
    public TestLifecycle(Logger logger) {
        this.logger = logger;
        this.instantiatedDate = new Date();
    }

    @OnEnable
    public void firstOnEnable() {
        this.logger.info("firstOnEnable");
        this.logger.info("execution time: {}", new Date());
        this.logger.info("instantiation time: {}", instantiatedDate);
    }

    @OnEnable
    public void secondOnEnable() {
        this.logger.info("secondOnEnable");
        this.logger.info("execution time: {}", new Date());
        this.logger.info("instantiation time: {}", instantiatedDate);
    }

    @OnFirstTick
    public void onFirstTick() {
        this.logger.info("onFirstTick");
        this.logger.info("execution time: {}", new Date());
        this.logger.info("instantiation time: {}", instantiatedDate);
    }

    @OnDisable
    public void onDisable() {
        this.logger.info("onDisable");
        this.logger.info("execution time: {}", new Date());
        this.logger.info("instantiation time: {}", instantiatedDate);
    }
}
