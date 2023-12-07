package ru.nsu.kozorez;

import java.util.ArrayDeque;
import java.util.Deque;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.log;
import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

/**
 * Prefix evaluator
 */
public class PrefixCalculations {

    /**
     * evaluetes prefix expressions
     *
     * @param expression prefix expression
     * @return result
     */

    // class Token
    // Value <: Token -> double val
    // Op <: Token -> int arity
    public Double evaluate(String expression) {
        // Token[] = parse(expression)
        String[] pieces = expression.split(" ");
        Deque<Double> stack = new ArrayDeque<>();
        double a, b;
        for (int i = pieces.length - 1; i >= 0; i--) {
            switch (pieces[i]) {
                case "sin":
                    a = stack.pop();
                    stack.push(sin(a));
                    break;
                case "cos":
                    a = stack.pop();
                    stack.push(cos(a));
                    break;
                case "log":
                    a = stack.pop();
                    if (a <= 0) {
                        throw new ArithmeticException("log " + a + " is undefined!");
                    }
                    stack.push(log(a));
                    break;
                case "pow":
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(pow(a, b));
                    break;
                case "sqrt":
                    a = stack.pop();
                    stack.push(sqrt(a));
                    break;
                case "+":
                    if (stack.size() < 2) {
                        throw new IllegalArgumentException("Not enough numbers for the math  operation!");
                    }
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(a + b);
                    break;
                case "-":
                    if (stack.size() < 2) {
                        throw new IllegalArgumentException("Not enough numbers for the math operation!");
                    }
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(a - b);
                    break;
                case "*":
                    if (stack.size() < 2) {
                        throw new IllegalArgumentException("Not enough numbers for the math operation!");
                    }
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(a * b);
                    break;
                case "/":
                    if (stack.size() < 2) {
                        throw new IllegalArgumentException("Not enough numbers for the math operation!");
                    }
                    a = stack.pop();
                    b = stack.pop();
                    if (b == 0) {
                        throw new ArithmeticException("Division by zero!");
                    }
                    stack.push(a / b);
                    break;
                default:
                    try {
                        stack.push(Double.valueOf(pieces[i]));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Incorrect operation: " + pieces[i]);
                    }
            }
        }
        if (stack.size() > 1) {
            throw new IllegalArgumentException("To many arguments!");
        } else {
            return stack.pop();
        }
    }
}
