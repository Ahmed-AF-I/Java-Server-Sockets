package com.seniorsteps.network;

import com.seniorsteps.network.handler.SocketHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static void main() {
        try(
            ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
            ServerSocket serverSocket = new ServerSocket(7777)

        ){
            IO.println("Server is running and listening on port 7777...");
            IO.println("Using Java Virtual Threads for high scalability.");

            while(true){
                Socket clientSocket = serverSocket.accept();
                SocketHandler socketHandler = new SocketHandler(clientSocket);
                executor.submit(socketHandler);
                System.out.println("Current active connections: " + SocketHandler.getCounter());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}