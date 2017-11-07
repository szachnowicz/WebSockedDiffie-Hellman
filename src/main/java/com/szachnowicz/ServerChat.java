package com.szachnowicz;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerChat extends AbstractChat  implements  Runnable{
    private ServerSocket server;

    public static void main(String[] args) {

        final ServerChat serverChat = new ServerChat("server");
    }


    public ServerChat(String title) {
        super(title);


        sendingThread = new Thread(this);
        reciveThread = new Thread(this);
        sendingThread.start();
        reciveThread.start();


    }

    @Override
    protected void setSocked() throws ClassNotFoundException, IOException {

        server = new ServerSocket(PORT);
        System.out.println("Server is waiting. . . . ");
        socket = server.accept();
        System.out.println("Client connected with Ip " + socket.getInetAddress().getHostAddress());
        

    }


    public void run() {

        try {
            if (Thread.currentThread() == sendingThread) {
                do {

                    printWriter = new PrintWriter(socket.getOutputStream(), true);

                } while (true);
            } else {
                do {
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String out = bufferedReader.readLine();
                    reciveMessage(out);

                } while (true);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void sendMessage(String message) {
        printWriter.println(message);

    }

    @Override
    public void reciveMessage(String message) {
        if (chatBox != null) {
            chatBox.append(String.format("%" + 10 + "s", " ") + message);
        }
    }

}
