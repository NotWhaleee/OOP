package ru.kozorez;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private final int port = 12345;
    private final List<ClientInfo> clients = new ArrayList<>();
    private final List<Task> tasks = new ArrayList<>();
    private int taskIdCounter = 0;
    private volatile boolean nonPrimeFound = false;
    private final int taskLength = 1000000;

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started");

            // поток для принятия клиентов
            new Thread(() -> {
                while (!nonPrimeFound) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        InetSocketAddress address = new InetSocketAddress(clientSocket.getInetAddress(), clientSocket.getPort());
                        ClientInfo clientInfo = new ClientInfo(/*address*/clientSocket);
                        clients.add(clientInfo);
                        System.out.println("Client connected from " + address);
                        new Thread(() -> handleClient(clientInfo)).start();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.exit(1);
                    }
                }
            }).start();

            ArrayList<Integer> numbers = new ArrayList<>();
            //numbers.add(8);
            for (int i = 1; i <= 1000000; i++) {
                numbers.add(100000007);
            }
            //numbers.add(8);
            for (int i = 1; i <= 100; i++) {
                numbers.add(7);
            }
            ArrayList<Integer> smallerNumbers = new ArrayList<>();
            for (int i = 0; i < numbers.size(); i++) {
                smallerNumbers.add(numbers.get(i));
                if(i % taskLength == 0 && i != 0){
                    if(tasks.isEmpty()){
                        smallerNumbers.add(6);
                    }
                    Task task = new Task(taskIdCounter++, smallerNumbers);
                    tasks.add(task);
                    System.out.println(task.getArr().size());
                    smallerNumbers = new ArrayList<>();
                }
            }
            Task task = new Task(taskIdCounter++, smallerNumbers);
            tasks.add(task);

            // раздача тасок
            while (!nonPrimeFound) {
                distributeTasks();

                boolean clientsAreDone = true;
                for(int i = 0; i < clients.size(); i++){
                        if(!clients.get(i).isAvailable()){
                            clientsAreDone = false;
                            break;
                        }
                }
                if(clientsAreDone && tasks.isEmpty()){
                    System.out.println("Non-prime numbers are not found. Server shutting down.");
                    System.exit(0);
                }
                Thread.sleep(100); // задержка
            }
            System.out.println("Non-prime number found. Server shutting down.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void distributeTasks() {
        for (ClientInfo client : clients) {
            if (!tasks.isEmpty() && client.isAvailable()) {
                Task task = tasks.removeFirst();
                System.out.println("Task id: " + task.getId());
                System.out.println("Task size: " + tasks.size());
                System.out.println("sent");
/*                System.out.println(client.getAddress().getAddress());
                System.out.println(client.getAddress().getPort());*/
                sendTaskToClient(client, task);
            }
        }
    }

    private void sendTaskToClient(ClientInfo client, Task task) {
        try {
            //System.out.println(task.getArr().size());
            DataOutputStream out = new DataOutputStream(client.getSocket().getOutputStream());
            out.writeInt(task.getId());
            System.out.println(task.getArr().size());
            out.writeInt(task.getArr().size());
            for (int number : task.getArr()) {
                out.writeInt(number);
            }
            //client.assignTask(task.getId());
            client.assignTask(task);
            client.setUnavailable();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handleClient(ClientInfo clientInfo) {
        try {
            DataInputStream in = new DataInputStream(clientInfo.getSocket().getInputStream());

            while (!nonPrimeFound) {

                int taskId = in.readInt();
                boolean hasNonPrime = in.readBoolean();

                if (hasNonPrime) {
                    nonPrimeFound = true;
                    System.out.println("Non-prime number found by client " + clientInfo.getId());
                    System.exit(0);
                }else {
                    clientInfo.setAvailable();
                }

                clientInfo.removeTask();
                if (nonPrimeFound) {
                    System.out.println("Non-prime number found. Server shutting down.");
                    System.exit(0);
                }
            }

        } catch (IOException e) {
            //e.printStackTrace();
            tasks.add(clientInfo.getTask());
        } finally {
            try {
                clientInfo.getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
