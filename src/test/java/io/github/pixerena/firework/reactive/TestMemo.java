package io.github.pixerena.firework.reactive;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

public class TestMemo {
    private static final Logger logger = LoggerFactory.getLogger(TestMemo.class);
    @Test
    void testCreate() {
        var firstName = Signal.create("John");
        var lastName = Signal.create("smith");
        var showFullName = Signal.create(true);

        var displayName = Memo.create(() -> {
            if (Boolean.FALSE.equals(showFullName.get())) return firstName.get();
            return firstName.get() + " " + lastName.get();
        });

        Effect.create(() -> logger.info(displayName::get));

        logger.info(() -> "2. Set showFullName: false ");
        showFullName.set(false);

        logger.info(() -> "3. Change lastName");
        lastName.set("Legend");

        logger.info(() -> "4. Set showFullName: true");
        showFullName.set(true);
    }
}
