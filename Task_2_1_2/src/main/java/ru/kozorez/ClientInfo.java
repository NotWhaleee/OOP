package ru.kozorez;

import javax.naming.ldap.SortKey;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Set;

class ClientInfo {
    private static int idCounter = 0;
    private final int id;
    //private final SocketAddress address;
    private boolean available = true;
    private Task task;
    private long lastTaskTime = 0;
    private DataInputStream in;
    private DataOutputStream out;
    private final SocketChannel socket;

    public ClientInfo(SocketChannel socket) throws IOException {
        this.id = idCounter++;
        this.socket = socket;
        //address = new SocketAddress(socket.getLocalAddress());
    }

/*    public SocketAddress getAddress() {
        return address;
    }*/

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
        try {
            return "ClientInfo{" +
                    "address=" + socket.getLocalAddress() +
                    '}';
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SocketChannel getSocket() {
        return socket;
    }
}
