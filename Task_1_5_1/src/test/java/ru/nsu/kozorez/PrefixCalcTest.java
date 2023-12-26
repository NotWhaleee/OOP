package ru.nsu.kozorez;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;


/**
 * Prefix calculations test.
 */
public class PrefixCalcTest {
    @Test
    void testComplexExpression() {
        PrefixCalculations cmonDoMath = new PrefixCalculations();
        final String expression = "+ - 20 * 3 4 1"; //ans = 9


        assertEquals(9, cmonDoMath.evaluate(expression));
    }

    @Test
    void testPow() {
        PrefixCalculations cmonDoMath = new PrefixCalculations();
        final String expression2 = "pow 4 2 ";
        assertEquals(16, cmonDoMath.evaluate(expression2));
    }

    @Test
    void testSin() {
        PrefixCalculations cmonDoMath = new PrefixCalculations();
        final String expression3 = "sin 1";
        assertEquals(0.8414709848078965, cmonDoMath.evaluate(expression3));
    }

    @Test
    void testDouble() {
        PrefixCalculations cmonDoMath = new PrefixCalculations();
        final String expression4 = "+ - 20.5 * 3.1 4.05 1.11"; //ans = 9.055
        assertEquals(9.055, cmonDoMath.evaluate(expression4));
    }

    @Test
    void testCos() {
        PrefixCalculations cmonDoMath = new PrefixCalculations();
        final String expression5 = "cos 4"; //ans = -0.6536436208636119
        assertEquals(-0.6536436208636119, cmonDoMath.evaluate(expression5));
    }

    @Test
    void testLog() {
        PrefixCalculations cmonDoMath = new PrefixCalculations();
        final String expression6 = "log 32"; //ans = 3.4657359027997265
        assertEquals(3.4657359027997265, cmonDoMath.evaluate(expression6));
    }

    @Test
    void testSqrt() {
        PrefixCalculations cmonDoMath = new PrefixCalculations();
        final String expression7 = "sqrt 4"; //ans = 2
        assertEquals(2, cmonDoMath.evaluate(expression7));
    }


    @Test
    void testWrongOperationExc() {
        PrefixCalculations cmonDoMath = new PrefixCalculations();

        assertThrowsExactly(IllegalArgumentException.class, () ->
                cmonDoMath.evaluate("cocosinus 1"));
        assertThrowsExactly(IllegalArgumentException.class, () ->
                cmonDoMath.evaluate("@"));

    }

    @Test
    void testToManyArgsExcPl() {
        PrefixCalculations cmonDoMath = new PrefixCalculations();

        assertThrowsExactly(IllegalArgumentException.class, () ->
                cmonDoMath.evaluate("+ - 1 2 3 4"));
    }

    @Test
    void testToManyArgsExcCos() {
        PrefixCalculations cmonDoMath = new PrefixCalculations();

        assertThrowsExactly(IllegalArgumentException.class, () ->
                cmonDoMath.evaluate("cos 1 2"));
    }

    @Test
    void testToManyArgsExcPow() {
        PrefixCalculations cmonDoMath = new PrefixCalculations();

        assertThrowsExactly(IllegalArgumentException.class, () ->
                cmonDoMath.evaluate("pow 1 2 4"));
    }

    @Test
    void testNotEnoughArgsExcPl() {
        PrefixCalculations cmonDoMath = new PrefixCalculations();

        assertThrowsExactly(IllegalArgumentException.class, () ->
                cmonDoMath.evaluate("+ 1"));
    }

    @Test
    void testNotEnoughArgsExcLog() {
        PrefixCalculations cmonDoMath = new PrefixCalculations();

        assertThrowsExactly(IllegalArgumentException.class, () ->
                cmonDoMath.evaluate("log"));
    }

    @Test
    void testNotEnoughArgsExcMult() {
        PrefixCalculations cmonDoMath = new PrefixCalculations();

        assertThrowsExactly(IllegalArgumentException.class, () ->
                cmonDoMath.evaluate("*"));
    }

    @Test
    void testArithmeticExcDivBy0() {
        PrefixCalculations cmonDoMath = new PrefixCalculations();

        assertThrowsExactly(ArithmeticException.class, () ->
                cmonDoMath.evaluate("/ 1 0"));
    }

    @Test
    void testArithmeticExcLog() {
        PrefixCalculations cmonDoMath = new PrefixCalculations();

        assertThrowsExactly(ArithmeticException.class, () ->
                cmonDoMath.evaluate("log -1"));
    }

    @Test
    void testArithmeticExcSqrtOfNeg() {
        PrefixCalculations cmonDoMath = new PrefixCalculations();

        assertThrowsExactly(ArithmeticException.class, () ->
                cmonDoMath.evaluate("sqrt -1"));
    }

}