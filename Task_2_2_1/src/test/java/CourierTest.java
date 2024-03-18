import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kozorez.Courier;

/**
 * Test operations with courier.
 */
class CourierTest {
    private Courier courier;

    @BeforeEach
    void setUp() {
        courier = new Courier(10, 200);
    }

    @Test
    void testGetCapacity() {
        assertEquals(10, courier.getCapacity());
    }

    @Test
    void testInitialCarries() {
        assertEquals(0, courier.getCarries());
    }

    @Test
    void testSetCarries() {
        courier.setCarries(5);
        assertEquals(5, courier.getCarries());
    }

    @Test
    void testInitialBusyState() {
        assertFalse(courier.isBusy());
    }

    @Test
    void testSetBusyState() {
        courier.setBusy(true);
        assertTrue(courier.isBusy());
    }

    @Test
    void testGetSpeed() {
        assertEquals(200, courier.getSpeed());
    }

    @Test
    void testToString() {
        String expected = "Courier{capacity=10, carries=0, isBusy=false, speed=200}";
        assertEquals(expected, courier.toString());
    }
}
