import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.kozorez.Main;
import ru.nsu.kozorez.Polynomial;


/**
 * Tests for operations with polynomials and Main.
 */
public class PolynomialTest {

    @Test
    void checkMain() {
        Main.main(new String[]{});
        assertTrue(true);
    }

    @Test
    void checksSum() {
        Polynomial p1 = new Polynomial(new int[]{4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[]{3, 2, 8});
        Polynomial answer = new Polynomial(new int[]{7, 5, 14, 7});
        assertEquals(answer, p1.plus(p2));
    }

    @Test
    void checkMinus() {
        Polynomial p1 = new Polynomial(new int[]{4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[]{3, 2, 8});
        Polynomial answer = new Polynomial(new int[]{1, 1, -2, 7});
        assertEquals(answer, p1.minus(p2));
    }

    @Test
    void checkProduct() {
        Polynomial p1 = new Polynomial(new int[]{4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[]{3, 2, 8});
        Polynomial answer = new Polynomial(new int[]{12, 17, 56, 57, 62, 56});
        assertEquals(answer, p1.times(p2));
    }

    @Test
    void checkEvaluation() {
        Polynomial p1 = new Polynomial(new int[]{12, 17, 56, 57, 62, 56});
        assertEquals(3510, p1.evaluate(2)); //check
        assertEquals(0, p1.evaluate(-1)); //check negative
        assertEquals(12, p1.evaluate((0))); //check 0
    }

    @Test()
    void checkDiff() {
        Polynomial p1 = new Polynomial(new int[]{4, 3, 6, 7});
        Polynomial answer1 = new Polynomial(new int[]{12, 42});
        assertEquals(answer1, p1.differentiate(2));

        assertThrows(IllegalArgumentException.class, () -> p1.differentiate(0));
    }

    @Test
    void checkEquals() {
        Polynomial p1 = new Polynomial(new int[]{4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[]{4, 3, 6, 7});
        Polynomial p3 = new Polynomial(new int[]{4, 3, 6, 7});

        assertEquals(p1, p2);
        assertEquals(p2, p1);
        assertEquals(p1, p3);
        assertEquals(p1, p3);

        assertEquals(p1, p2.minus(p1).plus(p1));

        assertNotEquals(p1, p2.plus(p1));
    }

    @Test
    void checkToString() {
        Polynomial p1 = new Polynomial(new int[]{4, 3, 6, 7});
        String answer = "7x^3 + 6x^2 + 3x + 4";
        assertEquals(answer, p1.toString());
    }
}
