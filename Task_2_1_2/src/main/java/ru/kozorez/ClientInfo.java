package ru.kozorez;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

class ClientInfo {
    private InetSocketAddress address;
    private boolean available = true;
    private Map<Integer, Long> taskStartTimes; // taskId -> startTime

    public ClientInfo(InetSocketAddress address) {
        this.address = address;
        taskStartTimes = new HashMap<>();
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setUnavailable() {
        available = false;
    }

    public void assignTask(int taskId) {
        taskStartTimes.put(taskId, System.currentTimeMillis());
        available = false;
    }

    public void removeTask(int taskId) {
        taskStartTimes.remove(taskId);
        if (taskStartTimes.isEmpty()) {
            available = true;
        }
    }

    public boolean hasTimedOutTasks() {
        long currentTime = System.currentTimeMillis();
        for (Map.Entry<Integer, Long> entry : taskStartTimes.entrySet()) {
            if (currentTime - entry.getValue() > 5000) { // 5 seconds timeout
                return true;
            }
        }
        return false;
    }

    public int getTimedOutTask() {
        long currentTime = System.currentTimeMillis();
        for (Map.Entry<Integer, Long> entry : taskStartTimes.entrySet()) {
            if (currentTime - entry.getValue() > 5000) {
                return entry.getKey();
            }
        }
        return -1;
    }
}