package ru.nsu.kozorez;

import java.util.Stack;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.log;
import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

public class PrefixCalculations {
    public Double evaluate(String expression) {
        String[] pieces = expression.split(" ");
        Stack<Double> stack = new Stack<>();
        Double a, b;
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
                        throw new IllegalArgumentException("Not enough numbers for the mathe operation!");
                    }
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(a + b);
                    break;
                case "-":
                    if (stack.size() < 2) {
                        throw new IllegalArgumentException("Not enough numbers for the mathe operation!");
                    }
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(a - b);
                    break;
                case "*":
                    if (stack.size() < 2) {
                        throw new IllegalArgumentException("Not enough numbers for the mathe operation!");
                    }
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(a * b);
                    break;
                case "/":
                    if (stack.size() < 2) {
                        throw new IllegalArgumentException("Not enough numbers for the mathe operation!");
                    }
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(a / b);
                    break;
                default:
                    stack.push(Double.valueOf(pieces[i]));
            }
        }
        return stack.pop();
    }
}
