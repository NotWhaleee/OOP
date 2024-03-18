import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kozorez.Baker;

/**
 * Test operations with bakers.
 */
class BakerTest {
    private Baker baker;

    @BeforeEach
    void setUp() {
        baker = new Baker(100);
    }

    @Test
    void testGetSpeed() {
        assertEquals(100, baker.getSpeed());
    }

    @Test
    void testInitialBusyState() {
        assertFalse(baker.getIsBusy());
    }

    @Test
    void testSetBusyState() {
        baker.setIsBusy(true);
        assertTrue(baker.getIsBusy());
    }

    @Test
    void testToString() {
        String expected = "Baker{speed=100, isBusy=false}";
        assertEquals(expected, baker.toString());
    }
}
