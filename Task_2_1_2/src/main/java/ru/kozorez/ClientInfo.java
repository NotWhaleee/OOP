package ru.kozorez;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * The ClientInfo class represents information about a connected client,
 * including its ID, availability status, and assigned tasks.
 */
class ClientInfo {
    private static int idCounter = 0;
    private final int id;
    private boolean available = true;
    private Task task;
    private long lastTaskTime = 0;
    private DataInputStream in;
    private DataOutputStream out;
    private final SocketChannel socket;

    /**
     * Constructs a ClientInfo object with the specified socket channel.
     *
     * @param socket the socket channel associated with the client
     * @throws IOException if an I/O error occurs
     */
    public ClientInfo(SocketChannel socket) throws IOException {
        this.id = idCounter++;
        this.socket = socket;
    }

    /**
     * Returns the ID of the client.
     *
     * @return the ID of the client
     */
    public int getId() {
        return id;
    }

    /**
     * Checks if the client is available to receive a new task.
     *
     * @return true if the client is available, false otherwise
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Sets the client as unavailable to receive new tasks.
     */
    public void setUnavailable() {
        available = false;
    }

    /**
     * Sets the client as available to receive new tasks.
     */
    public void setAvailable() {
        available = true;
    }

    /**
     * Assigns a task to the client and updates the last task time.
     *
     * @param task the task to be assigned to the client
     */
    public void assignTask(Task task) {
        this.task = task;
        lastTaskTime = System.currentTimeMillis();
    }

    /**
     * Removes the currently assigned task from the client.
     */
    public void removeTask() {
        task = null;
    }

    /**
     * Returns the time the last task was assigned to the client.
     *
     * @return the last task time
     */
    public long getLastTaskTime() {
        return lastTaskTime;
    }

    /**
     * Returns the currently assigned task to the client.
     *
     * @return the currently assigned task
     */
    public Task getTask() {
        return task;
    }

    /**
     * Returns a string representation of the client information.
     *
     * @return a string representation of the client information
     */
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

    /**
     * Returns the socket channel associated with the client.
     *
     * @return the socket channel associated with the client
     */
    public SocketChannel getSocket() {
        return socket;
    }
}
