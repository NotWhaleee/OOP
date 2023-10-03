package ru.nsu.kozorez;


import java.io.Console;
import java.util.Arrays;


/**
 * Starts the program.
 */
public class Main {
    /**
     * {@summary starts the program.}
     *
     * @param args default arguments.
     */
    public static void main(String[] args) {
        System.out.print("Input: ");
        Console consul = System.console();

        String[] input;

        if (consul == null) {
            input = new String[]{"0"};
        } else {
            input = System.console().readLine().split(" ");
            if (input[0].isEmpty()) {
                System.out.println("Output: ");
                System.exit(0);
            }
        }

        int[] arr = new int[input.length];

        for (int i = 0; i < input.length; i++) {
            arr[i] = Integer.parseInt(input[i]);
        }

        int[] res = Heapsort.sort(arr);

        System.out.print("Output: ");
        System.out.println(Arrays.toString(res));
    }
}