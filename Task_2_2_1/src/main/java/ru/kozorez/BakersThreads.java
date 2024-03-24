package ru.kozorez;

import static java.lang.Thread.interrupted;

/**
 * class for operating with threads of bakers.
 */
public class BakersThreads {

    /**
     * take one order.
     *
     * @param pizzeria pizzeria settings
     * @return could take an order or not
     */
    private synchronized boolean takeOrder(SetUpPizzeria pizzeria) {
        if (pizzeria.orders > 0) {
            pizzeria.orders--;
            return true;
        }
        return false;
    }

    /**
     * return order if interrupted.
     *
     * @param pizzeria pizzeria settings
     */
    private synchronized void returnOrder(SetUpPizzeria pizzeria) {
        pizzeria.orders++;
    }

    /**
     * start cooking order.
     *
     * @param pizzeria pizzeria settings
     * @param finalI   number of baker
     * @return if baker could take the order or not
     */
    private synchronized boolean startCooking(SetUpPizzeria pizzeria, int finalI) {
        if (takeOrder(pizzeria)) {
            pizzeria.bakers[finalI].setIsBusy(true);
            return true;
        }
        return false;
    }

    /**
     * start processing orders.
     *
     * @param pizzeria      pizzeria settings
     * @param bakersThreads bakers threads
     */
    public void processOrders(SetUpPizzeria pizzeria, Thread[] bakersThreads) {
        for (int i = 0; i < pizzeria.bakers.length; i++) {
            final int finalI = i;
            bakersThreads[finalI] = new Thread(() -> {
                while (!interrupted()) {
                    if (startCooking(pizzeria, finalI)) {
                        System.out.println("Hire baker " + finalI + " "
                                + pizzeria.bakers[finalI]);
                        try {
                            Thread.sleep(pizzeria.bakers[finalI].getSpeed());
                        } catch (InterruptedException e) {
                            pizzeria.bakers[finalI].setIsBusy(false);
                            returnOrder(pizzeria);
                            return;
                        }
                        //try to deliver to the storage;
                        if (!pizzeria.storage.deliverToTheStorage()) {
                            pizzeria.bakers[finalI].setIsBusy(false);
                            returnOrder(pizzeria);
                            return;
                        }
                        pizzeria.bakers[finalI].setIsBusy(false);
                        System.out.println("Release baker " + finalI
                                + " " + pizzeria.bakers[finalI]);
                    }
                }
            });
            bakersThreads[i].start();
        }
    }
}

