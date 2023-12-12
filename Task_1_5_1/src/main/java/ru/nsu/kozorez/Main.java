package ru.nsu.kozorez;

import java.util.Scanner;

/**
 * starts the application.
 */
public class Main {
    /**
     * starts the application.
     *
     * @param args no args needed.
     */
    public static void main(String[] args) {

        PrefixCalculations cmonDoMath = new PrefixCalculations();

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.equals("exit")) {

            try {
                System.out.println(cmonDoMath.evaluate(input));
            } catch (Exception exception) {
                System.out.println(exception);
            }
            input = scanner.nextLine();
        }
        scanner.close();
    }
}