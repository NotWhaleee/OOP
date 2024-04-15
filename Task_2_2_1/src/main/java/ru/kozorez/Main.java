package ru.kozorez;

import java.io.IOException;
import ru.kozorez.pizzeria.Pizzeria;

/**
 * Main class.
 */
public class Main {

    /**
     * starts program.
     *
     * @param args doesn't matter
     * @throws IOException parsing json error
     */
    public static void main(String[] args) throws IOException {

        Pizzeria pizzeria = new Pizzeria();
        pizzeria.run();
    }
}