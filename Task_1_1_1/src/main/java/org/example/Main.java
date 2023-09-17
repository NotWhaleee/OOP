package org.example;


import java.io.Console;
import java.util.Arrays;

/**
 * Starts the program
 */
public class Main {
    /**
     * @param args default arguments
     */
    public static void main(String[] args) {
        System.out.print("Input: ");
        Console consul = System.console();

        String[] consoleInput;

        if (consul == null) {
            consoleInput = new String[]{"0"};
        } else {
            consoleInput = System.console().readLine().split(" ");
        }

        int[] arr = new int[consoleInput.length];

        for (int i = 0; i < consoleInput.length; i++) {
            arr[i] = Integer.parseInt(consoleInput[i]);
        }

        int[] res = Heapsort.sort(arr);

        System.out.print("Output: ");
        System.out.println(Arrays.toString(res));
    }
}