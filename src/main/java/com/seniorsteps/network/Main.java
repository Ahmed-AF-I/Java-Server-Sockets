package com.seniorsteps.network;

import com.seniorsteps.network.handler.SocketHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    static void main() {
        try (ServerSocket serverSocket = new ServerSocket(7777)) {
            System.out.println("Waiting for connection...");
            while (true) {
                System.out.println("Server is running and listening on port 7777...");
                Socket client = serverSocket.accept();
                SocketHandler socketHandler = new SocketHandler(client);
                Thread thread = new Thread(socketHandler);
                thread.start();
                System.out.println(SocketHandler.getCounter() + " Client connected");
            }
        } catch (IOException e) {
            System.err.println("Server Setup Error: " + e.getMessage());
        }
    }
}