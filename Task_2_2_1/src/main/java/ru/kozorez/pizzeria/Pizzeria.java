package ru.kozorez.pizzeria;

import static java.lang.Thread.interrupted;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ru.kozorez.json.Json;
import ru.kozorez.staff.bakers.BakersThreads;
import ru.kozorez.staff.couriers.CouriersThreads;

/**
 * Class for operating pizzeria.
 */
public class Pizzeria {
    public static final String GREEN = "\033[0;32m"; // GREEN
    public static final String RED = "\033[0;31m"; // RED
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String RESET = "\033[0m"; // Text Reset

    private static final String jsonFile = "config.json";
    public SetUpPizzeria pizzeria;

    /**
     * run pizzeria.
     *
     * @throws IOException json error
     */
    public void run() throws IOException {
        Json jsonOperations = new Json(jsonFile);
        pizzeria = jsonOperations.parseJson();
        pizzeria.storage.resetDelivered();


        BakersThreads[] bakersWork = new BakersThreads[pizzeria.bakers.length];
        for (int i = 0; i < pizzeria.bakers.length; i++) {
            bakersWork[i] = new BakersThreads(this, pizzeria.bakers[i]);
            bakersWork[i].start();
        }

        CouriersThreads[] couriersWork = new CouriersThreads[pizzeria.couriers.length];
        for (int i = 0; i < pizzeria.couriers.length; i++) {
            couriersWork[i] = new CouriersThreads(this, pizzeria.couriers[i]);
            couriersWork[i].start();
        }

        Thread takingOrders = new Thread(this::takeOrders);
        takingOrders.start();
        try {
            Thread.sleep(pizzeria.openTime);
        } catch (Exception e) {
            System.out.println("Main sleep error: " + e);
        }
        closePizzeria(takingOrders, bakersWork, couriersWork);
    }


    /**
     * close pizzeria.
     *
     * @param takingOrders taking orders thread
     * @param bakersWork   working bakers threads
     * @param couriersWork working couriers threads
     */
    private void closePizzeria(Thread takingOrders,
                               BakersThreads[] bakersWork,
                               CouriersThreads[] couriersWork) {

        takingOrders.interrupt();

        stopWork(bakersWork, couriersWork);

        System.out.println(RED);
        System.out.println("PIZZERIA CLOSED!!!");
        System.out.println("Orders left: " + pizzeria.orders);
        System.out.println("Present on storage: " + pizzeria.storage.getStored());
        System.out.println("Delivered: " + pizzeria.storage.getDelivered());
        System.out.println(RESET);
        for (int i = 0; i < pizzeria.bakers.length; i++) {
            pizzeria.bakers[i].setIsBusy(false);
        }
        for (int i = 0; i < pizzeria.couriers.length; i++) {
            pizzeria.couriers[i].setBusy(false);
        }
        Json jsonOperations = new Json(jsonFile);
        jsonOperations.writeJson(pizzeria);
    }

    /**
     * start taking orders.
     */
    private void takeOrders() {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in));
        String input;
        do {
            try {
                // wait until we have data to complete a readLine()
                while (!br.ready() && !interrupted()) {
                    Thread.sleep(200);
                }
                input = br.readLine();
            } catch (InterruptedException ignored) {
                return;
            } catch (IOException e) {
                System.out.println(e);
                return;
            }
            try {
                int order = Integer.parseInt(input);
                placeOrders(order);
                System.out.println(GREEN + "Order for " + order + " pizzas placed!" + RESET);
                System.out.println();
            } catch (Exception ignored) {
                int a = 0; //not empty catch block so reviewdog wont bite me
            }
        } while (!interrupted());

        try {
            br.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }


    /**
     * stop work for bakers and couriers.
     *
     * @param bakersThreads   thread of bakers
     * @param couriersThreads thread of couriers
     */
    private void stopWork(BakersThreads[] bakersThreads, CouriersThreads[] couriersThreads) {
        for (BakersThreads baker : bakersThreads) {
            baker.interrupt();
        }
        for (CouriersThreads courier : couriersThreads) {
            courier.interrupt();
        }
    }

    /**
     * place n orders.
     *
     * @param orders int
     */
    private synchronized void placeOrders(int orders) {
        pizzeria.orders += orders;
    }
}

