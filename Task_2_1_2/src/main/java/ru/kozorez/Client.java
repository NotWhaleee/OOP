package ru.kozorez;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    private final String serverIp;
    private final int serverPort;
    private final int taskLength = 100000;


    public Client(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    public void start() {
        IsPrime isPrime = new IsPrime();
        try (SocketChannel clientSocket = SocketChannel.open (new InetSocketAddress(serverIp, serverPort))) {
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
                while (buffer.hasRemaining()){
                    numbers[i] = buffer.getInt();
                }
                System.out.println(i);

                boolean hasNonPrime = false;
                for (int number : numbers) {
                    if (!isPrime.isPrime(number)) {
                        hasNonPrime = true;
                        System.out.println(number);
                        break;
                    }
                }
                buffer.clear();

                //out.writeInt(taskId);
                buffer.putInt(hasNonPrime ? 1 : 0);
                buffer.flip();

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


    public static void main(String[] args) {
        Client client = new Client("localhost", 12345);
        client.start();
    }
}
