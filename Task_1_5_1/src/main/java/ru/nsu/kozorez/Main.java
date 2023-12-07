package ru.nsu.kozorez;

import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
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
        String expression4 = "sin1 + - 1 2 1"; // ans = 0
        String expression5 = "+ - 20.5 * 3.1 4.05 1.11"; //ans = 9.055
        String expression6 = "+ - 1 2 3 4"; //ans = 9.055
        String expression7 = "log -1"; //ans = 9.055

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