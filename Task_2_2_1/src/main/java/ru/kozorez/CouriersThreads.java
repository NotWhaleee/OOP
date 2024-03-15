package ru.kozorez;

import static java.lang.Thread.interrupted;
import static ru.kozorez.Pizzeria.BLUE;
import static ru.kozorez.Pizzeria.RESET;

public class CouriersThreads {
    private synchronized boolean startDelivering(SetUpPizzeria pizzeria, int finalI) {
        if (!pizzeria.couriers[finalI].isBusy() /*pizzeria.storage.getStored() > 0*/) {
            pizzeria.couriers[finalI].setBusy(true);
            return true;
        }
        return false;
    }

    public void processDelivery(SetUpPizzeria pizzeria, Thread[] couriersThreads) {

        for (int i = 0; i < pizzeria.couriers.length; i++) {
            final int finalI = i;
            couriersThreads[finalI] = new Thread(() -> {
                while (!interrupted()) {
                    pizzeria.couriers[finalI].setCarries(0);
                    if (startDelivering(pizzeria, finalI)) {
                        System.out.println("Hire courier " + finalI + " " + pizzeria.couriers[finalI]);
                        try {
                            Thread.sleep(pizzeria.couriers[finalI].getSpeed());
                        } catch (InterruptedException e) {
                            pizzeria.couriers[finalI].setBusy(false);
                            pizzeria.storage.returnToTheStorage(pizzeria.couriers[finalI]);
                            return;
                        }
                        //try to take from the storage;
                        if (!pizzeria.storage.takeFromTheStorage(pizzeria.couriers[finalI])) {
                            pizzeria.couriers[finalI].setBusy(false);
                            pizzeria.storage.returnToTheStorage(pizzeria.couriers[finalI]);
                            return;
                        }
                        pizzeria.couriers[finalI].setBusy(false);
                        System.out.println("Release courier " + finalI + " " + pizzeria.couriers[finalI]);
                        System.out.println(BLUE);
                        System.out.println(pizzeria.couriers[finalI].getCarries() + "ORDERS DELIVERED");
                        System.out.println(RESET);
                    }
                }
            });
            couriersThreads[finalI].start();
        }
    }
}
