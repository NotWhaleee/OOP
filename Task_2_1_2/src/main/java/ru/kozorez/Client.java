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

    public void start(){
        try (Socket clientSocket = new Socket(serverIp, serverPort)) {
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

            int number = in.readInt();
            System.out.println("Received from Server: " + number);

            number++;
            out.writeInt(number);
            out.flush();
            System.out.println("Sent to Server: " + number);
            in.close();
            out.close();
            System.out.println(clientSocket.getLocalPort() + " " + clientSocket.getChannel());


            for (int i = 0; i < 10000000; i++){
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 12345);
        client.start();


    }
}
