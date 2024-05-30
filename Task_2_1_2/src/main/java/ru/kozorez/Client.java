package ru.kozorez;

import java.io.*;
import java.net.*;

public class Client {
    private final String serverIp;
    private final int serverPort;

    public Client(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    public void start() {
        IsPrime isPrime = new IsPrime();
        try (Socket clientSocket = new Socket(serverIp, serverPort)) {
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

            while (true) {
                System.out.print("TaskID: ");
                int taskId = in.readInt();
                int arraySize = in.readInt();
                System.out.println(taskId);
                System.out.println("Array size: " + arraySize);
                int[] numbers = new int[arraySize];
                for (int i = 0; i < arraySize; i++) {
                    numbers[i] = in.readInt();
                }

                boolean hasNonPrime = false;
                for (int number : numbers) {
                    if (!isPrime.isPrime(number)) {
                        hasNonPrime = true;
                        System.out.println(number);
                        break;
                    }
                }

                out.writeInt(taskId);
                out.writeBoolean(hasNonPrime);
                out.flush();

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
