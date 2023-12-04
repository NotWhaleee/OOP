package ru.nsu.kozorez;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        String expression = "+ - 20 * 3 4 1"; //ans = 9
        String expression2 = "- * 3 + 3 7 / pow 4 2 2"; // ans = 22
        String expression3 = "sin + - 1 2 1"; // ans = 0
        PrefixCalculations cmonDoMath = new PrefixCalculations();
        System.out.println(cmonDoMath.evaluate(expression));
        System.out.println(cmonDoMath.evaluate(expression2));
        System.out.println(cmonDoMath.evaluate(expression3));
    }
}