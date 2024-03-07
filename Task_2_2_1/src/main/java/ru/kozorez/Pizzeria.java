package ru.kozorez;

public class Pizzeria {
    volatile int orders = 5;
    SetUpPizzeria setUpPizzeria = new SetUpPizzeria();

    public void testRun() {
        setUpPizzeria.setUp();
        Thread[] bakersThreads = new Thread[setUpPizzeria.bakers.length];

        //System.out.println("speed = " + setUpPizzeria.bakers[0].getSpeed());
        Thread bakersWork = new Thread(() -> bakersGoBrrr(bakersThreads));
        bakersWork.start();
        placeOrders(2);
        placeOrders(3);
        joinBakersThreads(bakersThreads);
        System.out.println("Left on storage: " + setUpPizzeria.storage.getStored());
        System.out.println("Present on storage: " + setUpPizzeria.storage.getStored());

    }


    private synchronized void placeOrders(int orders) {
        this.orders += orders;
    }

    private synchronized boolean takeOrder() {
        if (orders > 0) {
            orders--;
            return true;
        }
        return false;
    }

    private void bakersGoBrrr(Thread[] bakersThread) {
        while (true) {
            processOrders(bakersThread);
            if (orders < 1) return;
        }
    }

    private synchronized boolean startCooking(int finalI) {
        if (!setUpPizzeria.bakers[finalI].getIsBusy()) {
            if (takeOrder()) {
                setUpPizzeria.bakers[finalI].setIsBusy(true);
                return true;
            }
        }
        return false;
    }

   /* private synchronized boolean startDelivering(int finalI) {
        if (!setUpPizzeria.couriers[finalI].isBusy()) {
            setUpPizzeria.couriers[finalI].setBusy(true);
            return true;
        }
        return false;
    }

    private void processDelivery(Thread[] couriersThreads) {
        for (int i = 0; i < setUpPizzeria.couriers.length; i++) {

            final int finalI = i;

            if (startDelivering(finalI)) {
                bakersThreads[finalI] = new Thread(() -> {
                    System.out.println("Hire baker " + finalI + " " + setUpPizzeria.bakers[finalI]);

                    try {
                        Thread.sleep(setUpPizzeria.bakers[finalI].getSpeed());
                    } catch (InterruptedException e) {
                        System.err.println("Sleep error: " + e);
                        //throw new RuntimeException(e);
                    }

                    setUpPizzeria.storage.deliverToTheStorage(); //try to deliver to the storage;
                    setUpPizzeria.bakers[finalI].setIsBusy(false);
                    System.out.println("Release baker " + finalI + " " + setUpPizzeria.bakers[finalI]);
                });
                bakersThreads[i].start();
            }
        }*/

        private void processOrders (Thread[]bakersThreads){
            for (int i = 0; i < setUpPizzeria.bakers.length; i++) {

                final int finalI = i;

                if (startCooking(finalI)) {
                    bakersThreads[finalI] = new Thread(() -> {
                        System.out.println("Hire baker " + finalI + " " + setUpPizzeria.bakers[finalI]);

                        try {
                            Thread.sleep(setUpPizzeria.bakers[finalI].getSpeed());
                        } catch (InterruptedException e) {
                            System.err.println("Sleep error: " + e);
                            //throw new RuntimeException(e);
                        }

                        setUpPizzeria.storage.deliverToTheStorage(); //try to deliver to the storage;
                        setUpPizzeria.bakers[finalI].setIsBusy(false);
                        System.out.println("Release baker " + finalI + " " + setUpPizzeria.bakers[finalI]);
                    });
                    bakersThreads[i].start();
                }
            }
        }
        private void joinBakersThreads (Thread[]bakersThreads){
            for (Thread baker : bakersThreads) {
                try {
                    if (baker != null) {
                        baker.join();
                    }
                } catch (Exception e) {
                    System.err.println("Error with joining threads: " + e);
                }
            }
        }
    }
