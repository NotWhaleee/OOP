package ru.nsu.kozorez;

import java.util.Arrays;

/**
 * Class for simple operations with polynomials.
 */
public class Polynomial {
    private int[] coeffs;

    /**
     * get coeffs.
     *
     * @return array of coeffs.
     */
    public int[] getCoeffs() {
        return coeffs;
    }

    /**
     * set coeffs.
     *
     * @param coeffs array of coeffs
     */
    public void setCoeffs(int[] coeffs) {
        this.coeffs = coeffs;
    }

    /**
     * initialises a polynomial.
     * the leading coefficients are not equal to 0 by the rules.
     *
     * @param ints an array of ints as coefficients of the polynomial. from the smallest power of to the largest.
     */
    public Polynomial(int[] ints) {
        this.coeffs = ints.clone();
    }

    /**
     * inverts coeffs in the array. is being used in the minus function.
     *
     * @return polynomial with inverted coeffs.
     */
    private Polynomial invert() {
        int[] newCoeffs = new int[this.coeffs.length];
        Polynomial result = new Polynomial(newCoeffs);
        for (int i = 0; i < this.coeffs.length; i++) {
            result.coeffs[i] = -this.coeffs[i];
        }
        return result;
    }


    /**
     * summarises 2 polynomials.
     *
     * @param that second polynomial
     * @return sum of 2 polynomials
     */
    public Polynomial plus(Polynomial that) {
        int[] newCoeffs = new int[Math.max(this.coeffs.length, that.coeffs.length)];
        Polynomial result = new Polynomial(newCoeffs);
        for (int i = 0; i < result.coeffs.length; i++) {
            if (i >= this.coeffs.length) {
                result.coeffs[i] = that.coeffs[i];
            } else if (i >= that.coeffs.length) {
                result.coeffs[i] = this.coeffs[i];
            } else {
                result.coeffs[i] = this.coeffs[i] + that.coeffs[i];
            }
        }
        return result;
    }

    /**
     * subtracts one polynomial from another.
     *
     * @param that subtrahend
     * @return subtraction of 2 polynomials
     */
    public Polynomial minus(Polynomial that) {
        Polynomial result;
        result = this.plus(that.invert());
        return result;
    }

    /**
     * multiplies polynomials.
     *
     * @param that second polynomial
     * @return the product
     */
    public Polynomial times(Polynomial that) {
        int[] newCoeffs = new int[this.coeffs.length + that.coeffs.length - 1];
        Polynomial result = new Polynomial(newCoeffs);

        for (int i = 0; i < that.coeffs.length; i++) {
            for (int j = 0; j < this.coeffs.length; j++) {
                result.coeffs[i + j] += this.coeffs[j] * that.coeffs[i];
            }
        }
        return result;
    }


    /**
     * calculates the value of a polynomial at a point.
     *
     * @param x the point
     * @return result (int)
     */
    public int evaluate(int x) {
        int result = 0;
        for (int i = 0; i < this.coeffs.length; i++) {
            result += (int) (this.coeffs[i] * Math.pow(x, i));
        }
        return result;
    }

    /**
     * takes a derivative.
     *
     * @param order order of the derivative
     * @return result (polynomial)
     * @throws IllegalArgumentException order must be greater then 0
     */

    public Polynomial differentiate(int order) { //order > 0
        if (order < 1) {
            throw new IllegalArgumentException("Order must be greater then 0");
        }

        Polynomial result;
        if (this.coeffs.length > 1) {
            result = new Polynomial(new int[this.coeffs.length - 1]);
        } else {
            return new Polynomial(new int[1]);
        }
        for (int i = 0; i < result.coeffs.length; i++) {
            result.coeffs[i] = this.coeffs[i + 1] * (i + 1);
        }
        if (order > 1) {
            result = result.differentiate(order - 1);
        }
        return result;
    }


    /**
     * overrides hashCode func.
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(coeffs);
    }

    /**
     * checks if the polynomials are equal. the leading coefficients are not equal to 0 by the rules.
     *
     * @param o second polynomial
     * @return result(true or false)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Polynomial that = (Polynomial) o;
        if (this.coeffs.length != that.coeffs.length) {
            return false;
        }
        for (int i = 0; i < this.coeffs.length; i++) {
            if (this.coeffs[i] != that.coeffs[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * represents polynomials as a string value.
     *
     * @return polynomial
     */
    @Override
    public String toString() {
        String res = "";

        for (int i = coeffs.length - 1; i > 1; i--) {
            res = res.concat(coeffs[i] + "x^" + i + " + ");
        }

        if (coeffs.length > 1) {
            res = res.concat(coeffs[1] + "x + ");
        }

        res = res.concat(String.valueOf(coeffs[0]));

        return res;
    }

}
