import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.example.Main;
import org.example.Polynomial;
import org.junit.jupiter.api.Test;


/**
 * Tests for operations with polynomials and Main.
 */
public class PolynomialTest {

    @Test
    void checkMain() throws Exception {
        Main.main(new String[]{});
        assertTrue(true);
    }

//    @Test
//    void checkPolynomialCreation(){
//        Polynomial p1 = new Polynomial(new int[]{4, 3, 6, 7});
//    }

    @Test
    void checksSum() {
        Polynomial p1 = new Polynomial(new int[]{4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[]{3, 2, 8});
        Polynomial answer = new Polynomial(new int[]{7, 5, 14, 7});
        assert (answer.equals(p1.plus(p2)));
    }

    @Test
    void checkMinus() {
        Polynomial p1 = new Polynomial(new int[]{4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[]{3, 2, 8});
        Polynomial answer = new Polynomial(new int[]{1, 1, -2, 7});
        assert (answer.equals(p1.minus(p2)));
    }

    @Test
    void checkProduct() {
        Polynomial p1 = new Polynomial(new int[]{4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[]{3, 2, 8});
        Polynomial answer = new Polynomial(new int[]{12, 17, 56, 57, 62, 56});
        assert (answer.equals(p1.times(p2)));
    }

    @Test
    void checkEvaluation() {
        Polynomial p1 = new Polynomial(new int[]{12, 17, 56, 57, 62, 56});
        assert (3510 == p1.evaluate(2)); //check
        assert (0 == p1.evaluate(-1)); //check negative
        assert (12 == p1.evaluate((0))); //check 0
    }

    @Test()
    void checkDiff() throws Exception {
        Polynomial p1 = new Polynomial(new int[]{4, 3, 6, 7});
        Polynomial answer1 = new Polynomial(new int[]{12, 42});
        assert (answer1.equals(p1.differentiate(2)));


        try {
            p1.differentiate(0);
            fail("expected exception was not occurred");
        } catch (Exception e) {
            assert (true);
        }
    }

    @Test
    void checkEquals() {
        Polynomial p1 = new Polynomial(new int[]{4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[]{4, 3, 6, 7});
        assert (p1.equals(p2));

        assert (p1.equals(p2.minus(p1).plus(p1)));

        assert (!p1.equals(p2.plus(p1)));
    }

    @Test
    void checkToString() {
        Polynomial p1 = new Polynomial(new int[]{4, 3, 6, 7});
        String answer = "7x^3 + 6x^2 + 3x + 4";
        assert (answer.equals(p1.toString()));
    }
}
