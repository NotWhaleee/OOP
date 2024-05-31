package ru.kozorez;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * The Server class represents a server that distributes tasks to connected clients,
 * processes their results, and shuts down when a non-prime number is found.
 */
public class Server {
    private static final String POISON_PILL = "POISON_PILL";
    private final int port = 12345;
    private final List<ClientInfo> clients = new ArrayList<>();
    private final List<Task> tasks = new ArrayList<>();
    private int taskIdCounter = 0;
    private volatile boolean nonPrimeFound = false;
    private final int taskLength = 100000;

    /**
     * Starts the server to accept client connections, distribute tasks, and process results.
     */
    public void start() {
        try {
            Selector selector = Selector.open();
            ServerSocketChannel serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress("localhost", port));
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Server started");

            ArrayList<Integer> numbers = new ArrayList<>();
            for (int i = 1; i <= 1000000; i++) {
                numbers.add(100000007);
            }
            for (int i = 1; i <= 100; i++) {
                numbers.add(7);
            }
            ArrayList<Integer> smallerNumbers = new ArrayList<>();
            for (int i = 0; i < numbers.size(); i++) {
                smallerNumbers.add(numbers.get(i));
                if ((i + 1) % taskLength == 0 && i != 0) {
                    Task task = new Task(taskIdCounter++, smallerNumbers);
                    tasks.add(task);
                    System.out.println(task.getArr().size());
                    smallerNumbers = new ArrayList<>();
                }
            }
            Task task = new Task(taskIdCounter++, smallerNumbers);
            tasks.add(task);

            while (!nonPrimeFound) {
                distributeTasks();

                boolean clientsAreDone = true;
                for (ClientInfo client : clients) {
                    if (!client.isAvailable()) {
                        clientsAreDone = false;
                        break;
                    }
                }
                if (clientsAreDone && tasks.isEmpty()) {
                    System.out.println("Non-prime numbers are not found. Server shutting down.");
                    System.exit(0);
                }

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

    /**
     * Finds the ClientInfo object for the specified client socket.
     *
     * @param client the client socket channel
     * @return the ClientInfo object for the specified client socket
     */
    private ClientInfo findClientInfo(SocketChannel client) {
        for (ClientInfo clientInfo : clients) {
            if (clientInfo.getSocket().equals(client)) {
                return clientInfo;
            }
        }
        System.out.println("Something went wrong :((");
        return null;
    }

    /**
     * Reads data from the specified client socket.
     *
     * @param key the selection key associated with the client socket
     * @throws IOException if an I/O error occurs
     */
    private void readSocket(SelectionKey key) throws IOException {
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

    /**
     * Registers a new client connection with the selector.
     *
     * @param selector the selector to register with
     * @param serverSocket the server socket channel
     * @throws IOException if an I/O error occurs
     */
    private void register(Selector selector, ServerSocketChannel serverSocket) throws IOException {
        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
        ClientInfo clientInfo = new ClientInfo(client);
        clients.add(clientInfo);
        System.out.println("Client connected from " + client.getRemoteAddress());
    }

    /**
     * Distributes tasks to available clients.
     */
    private void distributeTasks() {
        for (ClientInfo client : clients) {
            if (!tasks.isEmpty() && client.isAvailable()) {
                Task task = tasks.remove(0);
                System.out.println("Task id: " + task.getId());
                System.out.println("Task size: " + tasks.size());
                System.out.println("Sent");
                sendTaskToClient(client, task);
            }
        }
    }

    /**
     * Sends a task to the specified client.
     *
     * @param client the client to send the task to
     * @param task the task to send
     */
    private void sendTaskToClient(ClientInfo client, Task task) {
        try {
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
            client.assignTask(task);
            client.setUnavailable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes all client sockets.
     */
    public void closeSockets() {
        for (ClientInfo client : clients) {
            try {
                client.getSocket().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The main method to run the Server.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
