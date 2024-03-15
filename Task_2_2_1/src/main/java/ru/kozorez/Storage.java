package ru.kozorez;


import static ru.kozorez.Pizzeria.YELLOW;
import static ru.kozorez.Pizzeria.RESET;


public class Storage {
    private final int capacity;
    private volatile int stored;
    private volatile int delivered = 0;

    public Storage(int capacity) {
        this.capacity = capacity;
    }

    public int getStored() {
        return stored;
    }

    public int getDelivered() {
        return delivered;
    }

    public synchronized void incrementStored() {
        stored++;
    }

    public synchronized void resetDelivered() {
        delivered = 0;
    }

    public synchronized void decreaseStored(int num) {
        if (stored >= num) {
            stored -= num;
        } else {
            System.err.println("Try to take more pizzas from the storage than present! Stored: " + stored + "| Take: " + num);
        }
    }

    public synchronized boolean deliverToTheStorage() {
        while (capacity <= stored) {
            try {
                System.out.println("Baker waitiiing....");
                wait();
            } catch (InterruptedException e) {
                return false;
            }
        }
        incrementStored();
        notifyAll();
        System.out.println(YELLOW);
        System.out.println("ORDER COOKED!");
        System.out.println(RESET);
        return true;
    }

    public synchronized boolean takeFromTheStorage(Courier courier) {
        if (stored == 0) {
            try {
                System.out.println("Courier waitiiing....");
                wait();
            } catch (InterruptedException e) {
                return false;
            }
        }
        int ableToTake = Math.min(courier.getCapacity(), stored);
        decreaseStored(ableToTake);
        courier.setCarries(ableToTake);
        delivered += courier.getCarries();
        notifyAll();
        return true;
    }
}
