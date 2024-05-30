package ru.kozorez;

import javax.naming.ldap.SortKey;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

class ClientInfo {
    private static int idCounter = 0;
    private final int id;
    private final InetSocketAddress address;
    private boolean available = true;
    private Task task;
    private long lastTaskTime = 0;
    private DataInputStream in;
    private DataOutputStream out;
    private final Socket socket;

    public ClientInfo(Socket socket) {
        this.id = idCounter++;
        this.socket = socket;
        address = new InetSocketAddress(socket.getInetAddress(), socket.getPort());
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public int getId() {
        return id;
    }

    public boolean isAvailable() {
        return available /*&& (System.currentTimeMillis() - lastTaskTime) < 1000*/; // 10 секунд тайм-аут
    }

    public void setUnavailable() {
        available = false;
    }

    public void setAvailable() {
        available = true;
    }

    public void assignTask(Task task){
        this.task = task;
        lastTaskTime = System.currentTimeMillis();
    }

    public void removeTask(){
        task = null;
    }

    public long getLastTaskTime() {
        return lastTaskTime;
    }

    public Task getTask(){
        return task;
    }

    @Override
    public String toString() {
        return "ClientInfo{" +
                "address=" + address +
                '}';
    }

    public Socket getSocket() {
        return socket;
    }
}
