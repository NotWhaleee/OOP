
package ru.kozorez;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

class ClientInfo {
    private static int idCounter = 0;
    private final int id;
    private final InetSocketAddress address;
    private boolean available = true;
    private final Set<Integer> tasks = new HashSet<>();
    private long lastTaskTime;
    private DataInputStream in;
    private DataOutputStream out;

    public ClientInfo(InetSocketAddress address) {
        this.id = idCounter++;
        this.address = address;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public int getId() {
        return id;
    }

    public boolean isAvailable() {
        return available && (System.currentTimeMillis() - lastTaskTime) < 10000; // 10 seconds timeout
    }

    public void setUnavailable() {
        available = false;
    }

    public void assignTask(int number) {
        tasks.add(number);
        lastTaskTime = System.currentTimeMillis();
    }

    public void removeTask(int number) {
        tasks.remove(number);
        if (tasks.isEmpty()) {
            available = true;
        }
    }

    @Override
    public String toString() {
        return "ClientInfo{" +
                "address=" + address +
                '}';
    }
}
