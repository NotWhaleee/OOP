package org.example;

import static org.example.Heapsort.print_arr;


public class Main {
    public static void main(String[] args) {
        int[] arr = {12, 11, 13, 5, 6, 7};
        Heapsort sort = new Heapsort();
        sort.sort(arr);
        System.out.println();
        print_arr(arr);
    }
}