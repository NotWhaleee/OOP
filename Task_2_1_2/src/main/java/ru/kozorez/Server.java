package ru.kozorez;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Server {
    private final int port = 12345;
    private final List<ClientInfo> clients = new ArrayList<>();
    private final List<Task> tasks = new ArrayList<>();
    private int taskIdCounter = 0;
    private volatile boolean nonPrimeFound = false;
    private final int taskLength = 100000;
    private static final String POISON_PILL = "POISON_PILL";

    public void start() {
        try {
            Selector selector = Selector.open();
            ServerSocketChannel serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress("localhost", 12345));
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Server started");

/*            // поток для принятия клиентов
            new Thread(() -> {
                while (!nonPrimeFound) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        InetSocketAddress address = new InetSocketAddress(clientSocket.getInetAddress(), clientSocket.getPort());
                        ClientInfo clientInfo = new ClientInfo(*//*address*//*clientSocket);
                        clients.add(clientInfo);
                        System.out.println("Client connected from " + address);
                        new Thread(() -> handleClient(clientInfo)).start();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.exit(1);
                    }
                }
            }).start();*/

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
/*                if (i - 2 == taskLength) {
                    smallerNumbers.add(6);
                }*/
                if ((i + 1) % taskLength == 0 && i != 0) {

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
                for (int i = 0; i < clients.size(); i++) {
                    if (!clients.get(i).isAvailable()) {
                        clientsAreDone = false;
                        break;
                    }
                }
                if (clientsAreDone && tasks.isEmpty()) {
                    System.out.println("Non-prime numbers are not found. Server shutting down.");
                    System.exit(0);
                }
                //Thread.sleep(100); // задержка

                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    if (key.isAcceptable()) {
                        System.out.println("Registered socket");
                        register(selector, serverSocket);
                    }
                    if (key.isReadable()) {
                        System.out.println("Read socket");
                        readSocket(key);
                    }
                    iter.remove();
                }
            }
            System.out.println("Non-prime number found. Server shutting down.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ClientInfo findClientInfo(SocketChannel client) {
        for (ClientInfo clientInfo : clients) {
            if (clientInfo.getSocket().equals(client)) {
                return clientInfo;
            }
        }
        System.out.println("something went wrong :((");
        return null;
    }

    private void readSocket(SelectionKey key)
            throws IOException {

        SocketChannel client = (SocketChannel) key.channel();
        ClientInfo clientInfo = findClientInfo(client);

        ByteBuffer buffer = ByteBuffer.allocate(1); // Allocate a buffer of size 1 byte (since a boolean is 1 byte)
        buffer.clear();
        buffer.flip(); // Flip the buffer before reading

        int bytesRead = client.read(buffer); // Read data into the buffer

        boolean hasNonPrime = buffer.get() != 0; // Read the boolean

        if (hasNonPrime) {
            nonPrimeFound = true;
            System.out.println("Non-prime number found by client " + clientInfo.getId());
            closeSockets();
            System.exit(0);
        } else {
            clientInfo.setAvailable();
        }

        clientInfo.removeTask();
        if (nonPrimeFound) {
            System.out.println("Non-prime number found. Server shutting down.");
            closeSockets();
            System.exit(0);
        }
        if (bytesRead == -1 || new String(buffer.array()).trim().equals(POISON_PILL)) {
            client.close();
            System.out.println("Not accepting client messages anymore");
        }
    }

    private void register(Selector selector, ServerSocketChannel serverSocket)
            throws IOException {

        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
        ClientInfo clientInfo = new ClientInfo(client);
        clients.add(clientInfo);
        System.out.println("Client connected from " + client.getRemoteAddress());

    }

    private void distributeTasks() {
        for (ClientInfo client : clients) {
            if (!tasks.isEmpty() && client.isAvailable()) {
                Task task = tasks.removeFirst();
                System.out.println("Task id: " + task.getId());
                System.out.println("Task size: " + tasks.size());
                System.out.println("sent");
                sendTaskToClient(client, task);
            }
        }
    }

    private void sendTaskToClient(ClientInfo client, Task task) {
        try {
            //System.out.println(task.getArr().size());
            ByteBuffer buffer = ByteBuffer.allocate((taskLength + 2) * 4);
            buffer.clear();

            buffer.putInt(task.getId());
            System.out.println("TaskID: " + task.getId() + " size: " + task.getArr().size());
            buffer.putInt(task.getArr().size());
            for (int number : task.getArr()) {
                buffer.putInt(number);
            }
            buffer.flip();
            client.getSocket().write(buffer);
            //DataOutputStream out = new DataOutputStream(client.getSocket().getOutputStream());
            //out.writeInt(task.getId());
            //System.out.println(task.getArr().size());
            //out.writeInt(task.getArr().size());
/*            for (int number : task.getArr()) {
                out.writeInt(number);
            }*/
            //client.assignTask(task.getId());
            client.assignTask(task);
            client.setUnavailable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeSockets() {
        for (int i = 0; i < clients.size(); i++) {
            try {
                clients.get(i).getSocket().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
