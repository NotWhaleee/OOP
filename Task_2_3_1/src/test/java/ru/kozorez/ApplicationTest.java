package ru.kozorez;

import org.junit.jupiter.api.Test;

/**
 * test application.
 */
public class ApplicationTest {

    /**
     * test application start up.
     */
    @Test
    void testStartUp() {
        Application application = new Application();
        application.main(new String[1]);
    }
}
