package ru.kozorez;

public class SetUpPizzeria {
    public Baker[] bakers = new Baker[3];
    public Courier[] couriers = new Courier[2];

    public Storage storage = new Storage(4);

    public void setUp() {
        bakers[0] = new Baker(5000);
        bakers[1] = new Baker(2000);
        bakers[2] = new Baker(1000);

        couriers[0] = new Courier(1);
        couriers[1] = new Courier(2);
    }
}
