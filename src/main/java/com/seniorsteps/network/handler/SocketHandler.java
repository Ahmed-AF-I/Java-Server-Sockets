package com.seniorsteps.network.handler;

import com.seniorsteps.network.services.SmartReplyEngin;

import java.io.*;
import java.net.Socket;

public class SocketHandler implements Runnable {
    private final Socket socketFromClient;
    private static int counter = 0;

    public static int getCounter() {
        return counter;
    }


    public SocketHandler(Socket clientSocket) {
        this.socketFromClient = clientSocket;
        counter ++;
    }

    @Override
    public void run() {
        this.handleSocket();
    }

    private void handleSocket(){
        try (
                // 2. استخدام Try-with-Resources للموارد الداخلية للعميل
                InputStream socketFromClientInputStream = socketFromClient.getInputStream();
                OutputStream socketFromClientOutputStream = socketFromClient.getOutputStream();

                Reader r = new InputStreamReader(socketFromClientInputStream);
                BufferedReader fromsocketFromClient = new BufferedReader(r);

                // 3. تفعيل auto-flush بإضافة 'true'
                PrintWriter tosocketFromClient = new PrintWriter(socketFromClientOutputStream, true)) {

            System.out.println("socketFromClient connected: " + socketFromClient.getInetAddress());
            SmartReplyEngin engin = new SmartReplyEngin();

            // دورة القراءة والكتابة
            while (true) {
                // INPUT
                String inputLine = fromsocketFromClient.readLine();

                // تحقق من قيمة readLine. القيمة null تعني أن العميل أغلق الاتصال.
                if (inputLine == null) {
                    System.out.println("socketFromClient disconnected gracefully.");
                    break;
                }

                // PROCESSING
                String serverMessage = engin.reply(inputLine);

                // OUTPUT
                System.out.println("CLIENT: " + inputLine);
                System.out.println("SERVER: " + serverMessage);
                tosocketFromClient.println(serverMessage); // الآن سيتم إرسالها فوراً بسبب auto-flush

                if (serverMessage.trim().equalsIgnoreCase("exit")) {
                    System.out.println("Closing connection by socketFromClient request.");
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("socketFromClient IO Error: " + e.getMessage());
        }finally {
            try {
                socketFromClient.close();
                System.out.println("socketFromClient closed.");
            } catch (IOException e) {
                System.err.println("Socket cannot close: " + e.getMessage());
            }
            counter --;
        }
    }

}
