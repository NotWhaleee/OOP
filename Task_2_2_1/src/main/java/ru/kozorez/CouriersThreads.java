package ru.kozorez;

import static ru.kozorez.Pizzeria.BLUE;
import static ru.kozorez.Pizzeria.RESET;

public class CouriersThreads {
    private synchronized boolean startDelivering(Pizzeria p, int finalI) {
        if (!p.pizzeria.couriers[finalI].isBusy() /*pizzeria.storage.getStored() > 0*/) {
            p.pizzeria.couriers[finalI].setBusy(true);
            return true;
        }
        return false;
    }
    private void processDelivery(Pizzeria p, Thread[] couriersThreads) {

        for (int i = 0; i < p.pizzeria.couriers.length; i++) {
            final int finalI = i;

            if (startDelivering(finalI)) {
                couriersThreads[finalI] = new Thread(() -> {
                    p.pizzeria.couriers[finalI].setCarries(0);
                    System.out.println("Hire courier " + finalI + " " + p.pizzeria.couriers[finalI]);
                    try {
                        Thread.sleep(p.pizzeria.couriers[finalI].getSpeed());
                    } catch (InterruptedException e) {
                        p.pizzeria.couriers[finalI].setBusy(false);
                        return;
                    }
                    //try to deliver to the storage;
                    if (!p.pizzeria.storage.takeFromTheStorage(p.pizzeria.couriers[finalI])) {
                        p.pizzeria.couriers[finalI].setBusy(false);
                        return;
                    }
                    p.pizzeria.couriers[finalI].setBusy(false);
                    System.out.println("Release courier " + finalI + " " + p.pizzeria.couriers[finalI]);
                    System.out.println(BLUE);
                    System.out.println(p.pizzeria.couriers[finalI].getCarries() + "ORDERS DELIVERED");
                    System.out.println(RESET);
                });
                couriersThreads[finalI].start();
            }
        }
    }
}
