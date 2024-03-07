package ru.kozorez;


public class Storage {
    private final int capacity;
    private volatile int stored;

    public Storage(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getStored() {
        return stored;
    }

    public synchronized void incrementStored() {
        stored++;
    }

    public synchronized void decreaseStored(int num) {
        if(stored < num){
            stored -= num;
        }else {
            System.err.println("Try to take more pizzas from the storage than present!");
        }
    }

    public synchronized void deliverToTheStorage() {
        if (capacity <= stored) {
            try {
                System.out.println("Baker waitiiing....");
                wait();
            } catch (Exception e) {
                System.err.println("Wait baker error: " + e);
            }
        }
        incrementStored();
        notifyAll();
    }

/*    public synchronized boolean takeFromTheStorage(Courier courier) {
        if (stored == 0) {
            try {
                System.out.println("Courier waitiiing....");
                wait();
            } catch (Exception e) {
                System.err.println("Wait courier error: " + e);
            }
        }
        int ableToTake = Math.min(courier.getCapacity(), stored);
        stored -= ableToTake;
        courier.setCarries(ableToTake);
        notifyAll();
    }*/
}
