package ru.nsu.kozorez;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;


/**
 * Prefix calculations test.
 */
public class PrefixCalcTest {

    @Test
    void testComplexExpressions() {
        PrefixCalculations cmonDoMath = new PrefixCalculations();
        String expression = "+ - 20 * 3 4 1"; //ans = 9
        String expression2 = "- * 3 + 3 7 / pow 4 2 2"; // ans = 22
        String expression3 = "sin + - 1 2 1"; // ans = 0
        String expression4 = "+ - 20.5 * 3.1 4.05 1.11"; //ans = 9.055
        String expression5 = "cos 4"; //ans = -0.6536436208636119
        String expression6 = "log 32"; //ans = 3.4657359027997265
        String expression7 = "sqrt 4"; //ans = 2

        assertEquals(9, cmonDoMath.evaluate(expression));
        assertEquals(22, cmonDoMath.evaluate(expression2));
        assertEquals(0, cmonDoMath.evaluate(expression3));
        assertEquals(9.055, cmonDoMath.evaluate(expression4));
        assertEquals(-0.6536436208636119, cmonDoMath.evaluate(expression5));
        assertEquals(3.4657359027997265, cmonDoMath.evaluate(expression6));
        assertEquals(2, cmonDoMath.evaluate(expression7));
    }

    @Test
    void testWrongOperationExc() {
        PrefixCalculations cmonDoMath = new PrefixCalculations();

        assertThrowsExactly(IllegalArgumentException.class, () -> cmonDoMath.evaluate("cocosinus 1"));
        assertThrowsExactly(IllegalArgumentException.class, () -> cmonDoMath.evaluate("@"));

    }

    @Test
    void testToManyArgsExc() {
        PrefixCalculations cmonDoMath = new PrefixCalculations();

        assertThrowsExactly(IllegalArgumentException.class, () -> cmonDoMath.evaluate("+ - 1 2 3 4"));
        assertThrowsExactly(IllegalArgumentException.class, () -> cmonDoMath.evaluate("cos 1 2"));
        assertThrowsExactly(IllegalArgumentException.class, () -> cmonDoMath.evaluate("pow 1 2 4"));
    }

    @Test
    void testNotEnoughArgsExc() {
        PrefixCalculations cmonDoMath = new PrefixCalculations();

        assertThrowsExactly(IllegalArgumentException.class, () -> cmonDoMath.evaluate("+ 1"));
        assertThrowsExactly(IllegalArgumentException.class, () -> cmonDoMath.evaluate("log"));
        assertThrowsExactly(IllegalArgumentException.class, () -> cmonDoMath.evaluate("*"));
    }

    @Test
    void testArithmeticExc() {
        PrefixCalculations cmonDoMath = new PrefixCalculations();

        assertThrowsExactly(ArithmeticException.class, () -> cmonDoMath.evaluate("/ 1 0"));
        assertThrowsExactly(ArithmeticException.class, () -> cmonDoMath.evaluate("log -1"));
        assertThrowsExactly(ArithmeticException.class, () -> cmonDoMath.evaluate("sqrt -1"));
    }

}