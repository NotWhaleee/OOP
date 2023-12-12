package ru.nsu.kozorez;

import static java.lang.Math.cos;
import static java.lang.Math.log;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;


/**
 * Tokens enum.
 */
public enum Token {
    SIN(1),
    COS(1),
    LOG(1),
    POW(2),
    SQRT(1),
    PLUS(2),
    MINUS(2),
    PRODUCT(2),
    DIVISION(2);

    private final int arity;


    /**
     * constructor for tokens.
     *
     * @param arity arity of an operation
     */
    Token(int arity) {
        this.arity = arity;
    }

    /**
     * gets artiry of an operation.
     *
     * @return arity of an operation
     */
    public int getArity() {
        return arity;
    }

    /**
     * calculates math opration.
     *
     * @param values values to calculate an operation
     * @return result of an operation
     */
    public double calculateOperation(double[] values) {
        switch (this) {
            case COS:
                return calcCos(values);
            case SIN:
                return calcSin(values);
            case LOG:
                return calcLog(values);
            case POW:
                return calcPow(values);
            case SQRT:
                return calcSqrt(values);
            case PLUS:
                return calcPlus(values);
            case MINUS:
                return calcMinus(values);
            case PRODUCT:
                return calcProduct(values);
            case DIVISION:
                return calcDivision(values);
            default:
                throw new IllegalArgumentException("Incorrect operation");
        }
    }

    /**
     * calculates cos.
     *
     * @param values values needed to calculate an operation
     * @return result of an operation
     */
    private double calcCos(double[] values) {
        return cos(values[0]);
    }

    /**
     * calculates sin.
     *
     * @param values values needed to calculate an operation
     * @return result of an operation
     */
    private double calcSin(double[] values) {
        return sin(values[0]);
    }

    /**
     * calculates log.
     *
     * @param values values needed to calculate an operation
     * @return result of an operation
     */
    private double calcLog(double[] values) {
        if (values[0] <= 0) {
            throw new ArithmeticException("log " + values[0] + " is undefined!");
        }
        return log(values[0]);
    }

    /**
     * calculates power.
     *
     * @param values values needed to calculate an operation
     * @return result of an operation
     */
    private double calcPow(double[] values) {
        return pow(values[0], values[1]);
    }

    /**
     * calculates sqrt.
     *
     * @param values values needed to calculate an operation
     * @return result of an operation
     */
    private double calcSqrt(double[] values) {
        if (values[0] < 0) {
            throw new ArithmeticException("sqrt " + values[0] + " is undefined!");
        }
        return sqrt(values[0]);
    }

    /**
     * calculates plus.
     *
     * @param values values needed to calculate an operation
     * @return result of an operation
     */
    private double calcPlus(double[] values) {
        return values[0] + values[1];
    }

    /**
     * calculates minus.
     *
     * @param values values needed to calculate an operation
     * @return result of an operation
     */
    private double calcMinus(double[] values) {
        return values[0] - values[1];
    }

    /**
     * calculates product.
     *
     * @param values values needed to calculate an operation
     * @return result of an operation
     */
    private double calcProduct(double[] values) {
        return values[0] * values[1];
    }

    /**
     * calculates division.
     *
     * @param values values needed to calculate an operation
     * @return result of an operation
     */
    private double calcDivision(double[] values) {
        if (values[1] == 0) {
            throw new ArithmeticException("Division by zero!");
        }
        return values[0] / values[1];
    }

}
