package ru.kozorez;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
    private List<ClientInfo> workers;
    private Map<Integer, Integer> tasks; // taskId -> number
    private Map<Integer, Boolean> results; // taskId -> isNotPrime
    private int taskCounter = 0;

    public Server(List<InetSocketAddress> workerAddresses) {
        workers = new ArrayList<>();
        for (InetSocketAddress address : workerAddresses) {
            workers.add(new ClientInfo(address));
        }
        tasks = new HashMap<>();
        results = new HashMap<>();
    }

    public void distributeTasks(int[] numbers) {
        for (int number : numbers) {
            tasks.put(taskCounter, number);
            assignTask(taskCounter++);
        }
    }

    private void assignTask(int taskId) {
        for (ClientInfo client : workers) {
            if (client.isAvailable()) {
                try {
                    sendTask(client, taskId, tasks.get(taskId));
                    client.assignTask(taskId);
                    return;
                } catch (IOException e) {
                    client.setUnavailable();
                }
            }
        }
    }

    private void sendTask(ClientInfo client, int taskId, int number) throws IOException {
        Socket socket = new Socket(client.getAddress().getHostName(), client.getAddress().getPort());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeInt(taskId);
        out.writeInt(number);
        out.close();
        socket.close();
    }

    public void receiveResults() {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            while (results.size() < tasks.size()) {
                Socket socket = serverSocket.accept();
                DataInputStream in = new DataInputStream(socket.getInputStream());
                int taskId = in.readInt();
                boolean isNotPrime = in.readBoolean();
                results.put(taskId, isNotPrime);
                socket.close();
                workers.forEach(client -> client.removeTask(taskId));
                for (ClientInfo client : workers) {
                    if (client.hasTimedOutTasks()) {
                        assignTask(client.getTimedOutTask());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean hasNonPrime() {
        for (boolean result : results.values()) {
            if (result) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        List<InetSocketAddress> workerAddresses = Arrays.asList(
                new InetSocketAddress("localhost", 5001),
                new InetSocketAddress("localhost", 5002)
        );
        Server server = new Server(workerAddresses);
        int[] numbers = {6, 8, 7, 13, 5, 9, 4};
        server.distributeTasks(numbers);
        server.receiveResults();
        System.out.println(server.hasNonPrime());
    }
}