package ru.kozorez;

/**
 * class for operating with threads of bakers.
 */
public class BakersThreads extends Thread {
    Pizzeria pizzeria;
    private final Baker baker;

    /**
     * constructor.
     *
     * @param pizzeria pizzeria
     * @param baker baker
     */
    public BakersThreads(Pizzeria pizzeria, Baker baker) {
        this.pizzeria = pizzeria;
        this.baker = baker;
    }

    /**
     * take one order.
     *
     * @param pizzeria pizzeria
     * @return could take an order or not
     */
    private boolean takeOrder(Pizzeria pizzeria) {
        synchronized (pizzeria.pizzeria){
            if (pizzeria.pizzeria.orders > 0) {
                pizzeria.pizzeria.orders--;
                return true;
            }
            return false;
        }

    }

    /**
     * return order if interrupted.
     *
     * @param pizzeria pizzeria settings
     */
    private void returnOrder(Pizzeria pizzeria) {
        synchronized (pizzeria.pizzeria){
            pizzeria.pizzeria.orders++;

        }
    }

    /**
     * start cooking order.
     *
     * @return if baker could take the order or not
     */
    private boolean startCooking() {
        synchronized (pizzeria.pizzeria){
            if (takeOrder(pizzeria)) {
                baker.setIsBusy(true);
                return true;
            }
            return false;
        }
    }

    /**
     * start processing orders.
     */
    @Override
    public void run() {
        while (!interrupted()) {
            if (startCooking()) {
                System.out.println("Hire baker" + baker);
                try {
                    Thread.sleep(baker.getSpeed());
                } catch (InterruptedException e) {
                    baker.setIsBusy(false);
                    returnOrder(pizzeria);
                    return;
                }
                //try to deliver to the storage;
                if (!pizzeria.pizzeria.storage.deliverToTheStorage()) {
                    baker.setIsBusy(false);
                    returnOrder(pizzeria);
                    return;
                }
                baker.setIsBusy(false);
                System.out.println("Release baker " + " " + baker);
            }
        }
    }
}

