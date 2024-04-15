package ru.kozorez.pizzeria;

import java.util.Arrays;
import ru.kozorez.staff.bakers.Baker;
import ru.kozorez.staff.couriers.Courier;

/**
 * pizzeria settings.
 */
public class SetUpPizzeria {
    public Baker[] bakers;

    public Courier[] couriers;

    public Storage storage;

    public int openTime;

    public volatile int orders;

    /**
     * pizzeria info.
     *
     * @return string
     */
    @Override
    public String toString() {
        return "SetUpPizzeria{"
                + "bakers=" + Arrays.toString(bakers)
                + ", couriers=" + Arrays.toString(couriers)
                + ", storage=" + storage
                + ", openTime=" + openTime
                + ", orders=" + orders
                + '}';
    }
}
