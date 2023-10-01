package org.example;


//import java.io.Console;
//import java.util.Arrays;

/**
 * Starts the program.
 */
public class Main {
    /**
     * {@summary starts the program.}
     *
     * @param args default arguments
     */
    public static void main(String[] args) throws Exception {

        Polynomial p1 = new Polynomial(new int[]{4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[]{3, 2, 8});
        System.out.println("Arrr");
        System.out.println(p1);
        System.out.println(p2);

        System.out.println(p1.plus(p2));
        System.out.println(p1.minus(p2));
        System.out.println(p1.plus(p2).minus(p2));

        System.out.println(p1.times(p2));
        System.out.println(p1.times(p2).evaluate(2));

        System.out.println(p1.equals(p1.plus(p2).minus(p2)));

        System.out.println(p1.differentiate(2));

        try {
            System.out.println(p1.differentiate(0));
        } catch (Exception e) {
            System.err.println("Order must be greater then 0!!");
        }


    }
}