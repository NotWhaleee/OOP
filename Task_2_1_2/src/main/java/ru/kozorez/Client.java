package ru.kozorez;

import java.net.*;
import java.io.*;

public class Client {
    private int port;

    public Client(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                DataInputStream in = new DataInputStream(socket.getInputStream());
                int taskId = in.readInt();
                int number = in.readInt();
                boolean isNotPrime = !isPrime(number);
                in.close();

                Socket responseSocket = new Socket("localhost", 5000);
                DataOutputStream out = new DataOutputStream(responseSocket.getOutputStream());
                out.writeInt(taskId);
                out.writeBoolean(isNotPrime);
                out.close();
                responseSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Client client = new Client(5001);
        client.start();
    }
}
