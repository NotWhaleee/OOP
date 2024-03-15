package ru.kozorez;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.Thread.interrupted;

public class Pizzeria {
    public static final String GREEN = "\033[0;32m"; // GREEN
    public static final String RED = "\033[0;31m"; // RED
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String RESET = "\033[0m"; // Text Reset

    SetUpPizzeria pizzeria;

    public void testRun() throws IOException {
        Json jsonOperations = new Json();
        pizzeria = jsonOperations.parseJson();
        pizzeria.storage.resetDelivered();

        Thread[] bakersThreads = new Thread[pizzeria.bakers.length];
        Thread[] couriersThreads = new Thread[pizzeria.couriers.length];

        Thread bakersWork = new Thread(() -> bakersGoBrrr(bakersThreads));
        bakersWork.start();
        Thread couriersWork = new Thread(() -> couriersGoBrrr(couriersThreads));
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

    private void closePizzeria(Thread takingOrders,
                               Thread bakersWork,
                               Thread couriersWork,
                               Thread[] bakersThreads,
                               Thread[] couriersThreads) {
        Json jsonOperations = new Json();

        takingOrders.interrupt();
        bakersWork.interrupt();
        couriersWork.interrupt();

        joinTreads(bakersThreads);
        joinTreads(couriersThreads);

        System.out.println(RED);
        System.out.println("PIZZERIA CLOSED!!!");
        System.out.println("Orders left: " + pizzeria.orders);
        System.out.println("Present on storage: " + pizzeria.storage.getStored());
        System.out.println("Delivered: " + pizzeria.storage.getDelivered());
        System.out.println(RESET);
        stopWork(bakersThreads, couriersThreads);
        jsonOperations.writeJson(pizzeria);
    }

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

    private synchronized void placeOrders(int orders) {
        pizzeria.orders += orders;
    }

    private synchronized boolean takeOrder() {
        if (pizzeria.orders > 0) {
            pizzeria.orders--;
            return true;
        }
        return false;
    }

    private void bakersGoBrrr(Thread[] couriersThreads) {
        while (!interrupted()) {
            processOrders(couriersThreads);
        }
    }

    private void couriersGoBrrr(Thread[] couriersThreads) {
        while (!interrupted()) {
            processDelivery(couriersThreads);
        }
    }

    private synchronized boolean startCooking(int finalI) {
        if (!pizzeria.bakers[finalI].getIsBusy()) {
            if (takeOrder()) {
                pizzeria.bakers[finalI].setIsBusy(true);
                return true;
            }
        }
        return false;
    }





    private void processOrders(Thread[] bakersThreads) {
        for (int i = 0; i < pizzeria.bakers.length; i++) {
            final int finalI = i;
            if (startCooking(finalI)) {
                bakersThreads[finalI] = new Thread(() -> {
                    System.out.println("Hire baker " + finalI + " " + pizzeria.bakers[finalI]);
                    try {
                        Thread.sleep(pizzeria.bakers[finalI].getSpeed());
                    } catch (InterruptedException e) {
                        pizzeria.bakers[finalI].setIsBusy(false);
                        return;
                    }
                    //try to deliver to the storage;
                    if (!pizzeria.storage.deliverToTheStorage()) {
                        pizzeria.bakers[finalI].setIsBusy(false);
                        return;
                    }
                    pizzeria.bakers[finalI].setIsBusy(false);
                    System.out.println("Release baker " + finalI + " " + pizzeria.bakers[finalI]);
                });
                bakersThreads[i].start();
            }
        }
    }

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

