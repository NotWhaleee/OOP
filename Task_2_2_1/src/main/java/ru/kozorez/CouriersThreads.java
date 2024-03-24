package ru.kozorez;

import static java.lang.Thread.interrupted;
import static ru.kozorez.Pizzeria.BLUE;
import static ru.kozorez.Pizzeria.RESET;

/**
 * class for operating with threads of couriers.
 */
public class CouriersThreads {

    /**
     * start process of delivering orders.
     *
     * @param pizzeria        pizzeria settings
     * @param couriersThreads threads of couriers
     */
    public void processDelivery(SetUpPizzeria pizzeria, Thread[] couriersThreads) {

        for (int i = 0; i < pizzeria.couriers.length; i++) {
            final int finalI = i;
            couriersThreads[finalI] = new Thread(() -> {
                while (!interrupted()) {
                    pizzeria.couriers[finalI].setCarries(0);
                    pizzeria.couriers[finalI].setBusy(true);
                    System.out.println("Hire courier " + finalI + " "
                            + pizzeria.couriers[finalI]);
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
                    System.out.println("Release courier " + finalI + " "
                            + pizzeria.couriers[finalI]);
                    System.out.println(BLUE);
                    System.out.println(pizzeria.couriers[finalI].getCarries()
                            + "ORDERS DELIVERED");
                    System.out.println(RESET);
                }

            });
            couriersThreads[finalI].start();
        }
    }
}
