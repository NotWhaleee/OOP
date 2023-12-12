package ru.nsu.kozorez;

import java.util.ArrayDeque;
import java.util.Deque;


import static java.lang.Double.valueOf;


/**
 * Prefix evaluator.
 */
public class PrefixCalculations {


    /**
     * evaluates prefix expressions.
     *
     * @param expression prefix expression
     * @return result
     */


    public Double evaluate(String expression) {

        String[] splitedExpr = expression.split(" ");
        Deque<Object> tokenizedExpr = tokenizeOperations(splitedExpr);
        Deque<Double> numbers = new ArrayDeque<>();

        while (!tokenizedExpr.isEmpty()){
            Object token = tokenizedExpr.pop();
            if(token.getClass() == Double.class){
                numbers.push((Double) token);
            }else {
                Token operation = (Token) token;
                double[] values = new double[operation.getArity()];
                if(numbers.size() < operation.getArity()){
                    throw new IllegalArgumentException("Not enough numbers for the math operation!");
                }
                for (int i = 0; i < operation.getArity(); i++) {
                    values[i] = numbers.pop();
                }
                numbers.push(operation.calculateOperation(values));
            }
        }
        if(numbers.size() > 1){
            throw new IllegalArgumentException("To many arguments!");
        }
        if(numbers.isEmpty()){
            throw new IllegalArgumentException("Not enough numbers for the math operation!");
        }
        return numbers.pop();

    }

    /**
     * tokenizes input splited expression.
     *
     * @param splitedExpr splited exspression
     * @return stack of tokens
     */
    public Deque<Object> tokenizeOperations(String[] splitedExpr) {
        Deque<Object> tokenStack = new ArrayDeque<>();
        for (int i = 0; i < splitedExpr.length; i++) {
            switch (splitedExpr[i]) {
                case "cos" -> tokenStack.push(Token.COS);
                case "sin" -> tokenStack.push(Token.SIN);
                case "log" -> tokenStack.push(Token.LOG);
                case "pow" -> tokenStack.push(Token.POW);
                case "sqrt" -> tokenStack.push(Token.SQRT);
                case "+" -> tokenStack.push(Token.PLUS);
                case "-" -> tokenStack.push(Token.MINUS);
                case "*" -> tokenStack.push(Token.PRODUCT);
                case "/" -> tokenStack.push(Token.DIVISION);
                default -> {
                    try {
                        tokenStack.push(valueOf(splitedExpr[i]));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Incorrect operation: " + splitedExpr[i]);
                    }
                }
            }
        }
        return tokenStack;
    }
}
