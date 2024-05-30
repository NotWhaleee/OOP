package ru.kozorez;

import java.io.*;
import java.net.*;

public class Server {
    private final int port = 12345;

    public void start() {

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started");

            int number = 1;

            // Подключение и общение с первым клиентом
            try (Socket client1 = serverSocket.accept()) {
                System.out.println("Client 1 connected from port: " + client1.getPort());
                DataInputStream in1 = new DataInputStream(client1.getInputStream());
                DataOutputStream out1 = new DataOutputStream(client1.getOutputStream());

                out1.writeInt(number);
                out1.flush();
                System.out.println("Sent to Client 1: " + number);

                number = in1.readInt();
                System.out.println("Received from Client 1: " + number);
            }

            // Подключение и общение со вторым клиентом
            try (Socket client2 = serverSocket.accept()) {
                System.out.println("Client 2 connected from port: " + client2.getPort());
                DataInputStream in2 = new DataInputStream(client2.getInputStream());
                DataOutputStream out2 = new DataOutputStream(client2.getOutputStream());

                out2.writeInt(number);
                out2.flush();
                System.out.println("Sent to Client 2: " + number);

                number = in2.readInt();
                System.out.println("Received from Client 2: " + number);
            }

            System.out.println("Final number: " + number);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
