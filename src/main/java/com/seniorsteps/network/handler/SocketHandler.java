package com.seniorsteps.network.handler;

import com.seniorsteps.network.services.SmartReplyEngin;

import java.io.*;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class SocketHandler implements Runnable {
    private final Socket socketFromClient;
    private static final AtomicInteger counter = new AtomicInteger(0);
    public static volatile boolean hasStarted = false;
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static int getCounter() {
        return counter.get();
    }


    public SocketHandler(Socket clientSocket) {
        this.socketFromClient = clientSocket;
        counter.incrementAndGet();
        hasStarted = true;
    }

    private void log(String message) {
        String time = LocalTime.now().format(timeFormatter);
        String threadInfo = Thread.currentThread().toString();
        System.out.printf("[%s] [%s] %s%n", time, threadInfo, message);
    }

    @Override
    public void run() {
        this.handleSocket();
    }

    private void handleSocket(){
        try (
                InputStream socketFromClientInputStream = socketFromClient.getInputStream();
                OutputStream socketFromClientOutputStream = socketFromClient.getOutputStream();

                BufferedReader fromSocketFromClient = new BufferedReader(new InputStreamReader(socketFromClientInputStream));

                PrintWriter toSocketFromClient = new PrintWriter(socketFromClientOutputStream, true)) {

            log("New connection from: " + socketFromClient.getInetAddress());
            SmartReplyEngin engin = new SmartReplyEngin();

            String inputLine;
            while ((inputLine = fromSocketFromClient.readLine()) != null) { //input
                // INPUT

                // PROCESSING
                String serverMessage = engin.reply(inputLine);

                // OUTPUT
                log("CLIENT sent: " + inputLine);
                log("SERVER replied: " + serverMessage);
                toSocketFromClient.println(serverMessage);

                if (serverMessage.trim().equalsIgnoreCase("exit")) {
                    log("Exit command received.");
                    break;
                }
            }
        } catch (IOException e) {
            log("IO Error: " + e.getMessage());
        }finally {
            try {
                socketFromClient.close();
                log("Socket closed.");
            } catch (IOException e) {
                log("Error closing socket: " + e.getMessage());
            }
            int remaining = counter.decrementAndGet();
            log("Connection finished. Remaining clients: " + remaining);

            if (hasStarted && remaining == 0) {
                log("SHUTTING DOWN SERVER: No more clients.");
                System.exit(0);
            }
        }
    }

}
