package ru.kozorez;

import static java.lang.Thread.interrupted;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
    SetUpPizzeria pizzeria;

    /**
     * run pizzeria.
     *
     * @throws IOException json error
     */
    public void run() throws IOException {
        Json jsonOperations = new Json(jsonFile);
        pizzeria = jsonOperations.parseJson();
        pizzeria.storage.resetDelivered();

        Thread[] bakersThreads = new Thread[pizzeria.bakers.length];
        BakersThreads bakersGoBrr = new BakersThreads();
        Thread bakersWork = new Thread(() -> bakersGoBrr.processOrders(pizzeria, bakersThreads));
        bakersWork.start();

        Thread[] couriersThreads = new Thread[pizzeria.couriers.length];
        CouriersThreads couriersGoBrr = new CouriersThreads();
        Thread couriersWork = new Thread(() ->
                couriersGoBrr.processDelivery(pizzeria, couriersThreads));
        couriersWork.start();

        Thread takingOrders = new Thread(() -> takeOrders());
        takingOrders.start();
        try {
            Thread.sleep(pizzeria.openTime);
        } catch (Exception e) {
            System.out.println("Main sleep error: " + e);
        }
        closePizzeria(takingOrders, bakersWork, couriersWork, bakersThreads, couriersThreads);
    }

    /**
     * close pizzeria.
     *
     * @param takingOrders    taking orders thread
     * @param bakersWork      start bakers thread
     * @param couriersWork    start couriers thread
     * @param bakersThreads   working bakers thread
     * @param couriersThreads working couriers thread
     */
    private void closePizzeria(Thread takingOrders,
                               Thread bakersWork,
                               Thread couriersWork,
                               Thread[] bakersThreads,
                               Thread[] couriersThreads) {
        Json jsonOperations = new Json(jsonFile);

        takingOrders.interrupt();
        bakersWork.interrupt();
        couriersWork.interrupt();
/*
        joinTreads(bakersThreads);
        joinTreads(couriersThreads);
*/
        System.out.println(RED);
        System.out.println("PIZZERIA CLOSED!!!");
        System.out.println("Orders left: " + pizzeria.orders);
        System.out.println("Present on storage: " + pizzeria.storage.getStored());
        System.out.println("Delivered: " + pizzeria.storage.getDelivered());
        System.out.println(RESET);
        stopWork(bakersThreads, couriersThreads);
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
            }
        } while (true);
    }


    /**
     * stop work for bakers and couriers.
     *
     * @param bakersThreads   thread of bakers
     * @param couriersThreads thread of couriers
     */
    private void stopWork(Thread[] bakersThreads, Thread[] couriersThreads) {
        for (Thread baker : bakersThreads) {
            if (baker != null) {
                baker.interrupt();
            }
        }
        for (Thread courier : couriersThreads) {
            if (courier != null) {
                courier.interrupt();
            }
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

    /**
     * join any threads for 1000ms.
     *
     * @param threads threads
     */
    private void joinTreads(Thread[] threads) {
        int waitTime = 1000;
        for (Thread thread : threads) {
            try {
                if (thread != null) {
                    thread.join(waitTime);
                }
            } catch (Exception e) {
                System.err.println("Error with joining threads: " + e);
            }
        }
    }
}

