package ru.kozorez;

import ru.kozorez.Pizzeria.Pizzeria;

import java.io.IOException;

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