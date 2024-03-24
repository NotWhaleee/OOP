package ru.kozorez.Staff.Couriers;

import ru.kozorez.Pizzeria.Pizzeria;

import static ru.kozorez.Pizzeria.Pizzeria.BLUE;
import static ru.kozorez.Pizzeria.Pizzeria.RESET;

/**
 * class for operating with threads of couriers.
 */
public class CouriersThreads extends Thread {
    Pizzeria pizzeria;
    private final Courier courier;

    /**
     * constructor.
     *
     * @param pizzeria pizzeria
     * @param courier  courier
     */
    public CouriersThreads(Pizzeria pizzeria, Courier courier) {
        this.pizzeria = pizzeria;
        this.courier = courier;
    }

    /**
     * start process of delivering orders.
     */
    @Override
    public void run() {

        while (!interrupted()) {
            courier.setCarries(0);
            courier.setBusy(true);
            System.out.println("Hire courier " + courier);
            try {
                Thread.sleep(courier.getSpeed());
            } catch (InterruptedException e) {
                courier.setBusy(false);
                pizzeria.pizzeria.storage.returnToTheStorage(courier);
                return;
            }
            //try taking from the storage;
            if (!pizzeria.pizzeria.storage.takeFromTheStorage(courier)) {
                courier.setBusy(false);
                pizzeria.pizzeria.storage.returnToTheStorage(courier);
                return;
            }
            courier.setBusy(false);
            System.out.println("Release courier " + courier);
            System.out.println(BLUE);
            System.out.println(courier.getCarries() + "ORDERS DELIVERED");
            System.out.println(RESET);
        }
    }
}