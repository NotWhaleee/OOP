import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import ru.kozorez.Pizzeria.Pizzeria;

/**
 * Test pizzeria.
 */
class PizzeriaTest {
    @Test
    void setUp() {
        Pizzeria pizzeria = new Pizzeria();
        assertDoesNotThrow(pizzeria::run);
    }
}
