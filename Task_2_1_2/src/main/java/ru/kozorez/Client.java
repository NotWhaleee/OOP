package ru.kozorez;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * The Client class represents a client that connects to a server,
 * receives a task, processes it, and sends back the result.
 */
public class Client {
    private final String serverIp;
    private final int serverPort;
    private final int taskLength = 1000;

    /**
     * Constructs a Client object with the specified server IP address and port number.
     *
     * @param serverIp   the IP address of the server
     * @param serverPort the port number of the server
     */
    public Client(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    /**
     * Starts the client to connect to the server, receive tasks, and process them.
     */
    public void start() {
        IsPrime isPrime = new IsPrime();
        try (SocketChannel clientSocket = SocketChannel.open(new InetSocketAddress(serverIp, serverPort))) {
            ByteBuffer buffer = ByteBuffer.allocate((taskLength + 2) * 4);
            while (true) {
                buffer.clear();

                int bytesRead = clientSocket.read(buffer);
                buffer.flip();
                System.out.println(bytesRead);
                if (bytesRead == -1) {
                    break;
                }
                System.out.print("TaskID: ");

                int taskId = buffer.getInt();
                int arraySize = buffer.getInt();
                System.out.println(taskId);
                System.out.println("Array size: " + arraySize);
                int[] numbers = new int[arraySize];
                int i = 0;
                while (buffer.hasRemaining()) {
                    numbers[i] = buffer.getInt();
                    i++;
                }
                System.out.println(buffer);

                //System.out.println(i);

                boolean hasNonPrime = false;
                for (int number : numbers) {
                    if (!isPrime.isPrime(number)) {
                        hasNonPrime = true;
                        System.out.println(number);
                        break;
                    }
                }

                buffer.clear();
                if (hasNonPrime) {
                    buffer.putInt(1);
                } else {
                    buffer.putInt(0);
                }

                buffer.flip();
                System.out.println(buffer);
                clientSocket.write(buffer);

                if (hasNonPrime) {
                    break;
                }
            }

        } catch (EOFException e) {
            // No more tasks
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * The main method to run the Client.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Client client = new Client("localhost", 12345);
        client.start();
    }
}
