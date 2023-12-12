package ru.nsu.kozorez;

import java.util.Scanner;

/**
 * starts the application.
 */
public class Main {
    /**
     *  starts the application.
     *
     * @param args no args needed.
     */
    public static void main(String[] args) {

        PrefixCalculations cmonDoMath = new PrefixCalculations();

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.equals("exit")){

            try{
                System.out.println(cmonDoMath.evaluate(input));
            }catch (Exception exception){
                System.out.println(exception);
            }
            input = scanner.nextLine();
        }
        scanner.close();
        /*
        String expression = "+ - 20 * 3 4 1"; //ans = 9
        String expression2 = "- * 3 + 3 7 / pow 4 2 2"; // ans = 22
        String expression3 = "sin + - 1 2 1"; // ans = 0
        String expression4 = "sin1 + - 1 2 1"; // java.lang.IllegalArgumentException: Incorrect operation: sin1
        String expression5 = "+ - 20.5 * 3.1 4.05 1.11"; //ans = 9.055
        String expression6 = "+ - 1 2 3 4"; //ans = 2
        String expression7 = "log -1"; //java.lang.ArithmeticException: log -1.0 is undefined!

        System.out.println(cmonDoMath.evaluate(expression));
        System.out.println(cmonDoMath.evaluate(expression2));
        System.out.println(cmonDoMath.evaluate(expression3));

        try {
            System.out.println(cmonDoMath.evaluate(expression4));
        } catch (Exception exception) {
            System.out.println(exception);
        }

        System.out.println(cmonDoMath.evaluate(expression5));

        try {
            System.out.println(cmonDoMath.evaluate(expression6));
        } catch (Exception exception) {
            System.out.println(exception);
        }

        try {
            System.out.println(cmonDoMath.evaluate(expression7));
        } catch (Exception exception) {
            System.out.println(exception);
        }
    */
    }
}