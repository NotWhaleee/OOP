import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kozorez.pizzeria.Storage;
import ru.kozorez.staff.couriers.Courier;

/**
 * Test operations with storage.
 */
class StorageTest {
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage(100);
    }

    @Test
    void testGetStored() {
        assertEquals(0, storage.getStored());
    }

    @Test
    void testGetDelivered() {
        assertEquals(0, storage.getDelivered());
    }

    @Test
    void testIncrementStored() {
        storage.incrementStored();
        assertEquals(1, storage.getStored());
    }

    @Test
    void testResetDelivered() {
        storage.resetDelivered();
        assertEquals(0, storage.getDelivered());
    }

    @Test
    void testDecreaseStored() {
        storage.incrementStored();
        storage.decreaseStored(1);
        assertEquals(0, storage.getStored());
    }

    @Test
    void testDeliverToTheStorage() {
        assertTrue(storage.deliverToTheStorage());
        // Add more test cases for different scenarios
    }

    @Test
    void testReturnToTheStorage() {
        Courier courier = new Courier(10, 200);
        courier.setCarries(5);
        storage.returnToTheStorage(courier);
        assertEquals(5, storage.getStored());
        assertEquals(-5, storage.getDelivered());
    }

    @Test
    void testTakeFromTheStorage() {
        Courier courier = new Courier(10, 200);
        storage.incrementStored();
        storage.incrementStored();
        assertTrue(storage.takeFromTheStorage(courier));
        assertEquals(0, storage.getStored());
        assertEquals(2, storage.getDelivered());
    }

    @Test
    void testToString() {
        String expected = "Storage{capacity=100, stored=0, delivered=0}";
        assertEquals(expected, storage.toString());
    }
}
