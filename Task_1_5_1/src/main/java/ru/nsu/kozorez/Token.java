package ru.nsu.kozorez;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.log;
import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

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
        double answer;
        switch (this) {
            case COS -> answer = calcCOS(values);
            case SIN -> answer = calcSIN(values);
            case LOG -> answer = calcLOG(values);
            case POW -> answer = calcPOW(values);
            case SQRT -> answer = calcSQRT(values);
            case PLUS -> answer = calcPLUS(values);
            case MINUS -> answer = calcMINUS(values);
            case PRODUCT -> answer = calcPRODUCT(values);
            case DIVISION -> answer = calcDIVISION(values);
            default -> throw new IllegalArgumentException("Incorrect operation");
        }
        return answer;
    }

    /**
     * calculates cos.
     *
     * @param values values needed to calculate an operation
     * @return result of an operation
     */
    private double calcCOS(double[] values) {
        return cos(values[0]);
    }

    /**
     * calculates sin.
     *
     * @param values values needed to calculate an operation
     * @return result of an operation
     */
    private double calcSIN(double[] values) {
        return sin(values[0]);
    }

    /**
     * calculates log.
     *
     * @param values values needed to calculate an operation
     * @return result of an operation
     */
    private double calcLOG(double[] values) {
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
    private double calcPOW(double[] values) {
        return pow(values[0], values[1]);
    }

    /**
     * calculates sqrt.
     *
     * @param values values needed to calculate an operation
     * @return result of an operation
     */
    private double calcSQRT(double[] values) {
        return sqrt(values[0]);
    }

    /**
     * calculates plus.
     *
     * @param values values needed to calculate an operation
     * @return result of an operation
     */
    private double calcPLUS(double[] values) {
        return values[0] + values[1];
    }

    /**
     * calculates minus.
     *
     * @param values values needed to calculate an operation
     * @return result of an operation
     */
    private double calcMINUS(double[] values) {
        return values[0] - values[1];
    }

    /**
     * calculates product.
     *
     * @param values values needed to calculate an operation
     * @return result of an operation
     */
    private double calcPRODUCT(double[] values) {
        return values[0] * values[1];
    }

    /**
     * calculates division.
     *
     * @param values values needed to calculate an operation
     * @return result of an operation
     */
    private double calcDIVISION(double[] values) {
        if (values[1] == 0) {
            throw new ArithmeticException("Division by zero!");
        }
        return values[0] / values[1];
    }

}
