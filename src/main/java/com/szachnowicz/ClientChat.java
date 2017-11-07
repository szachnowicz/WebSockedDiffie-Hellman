package com.szachnowicz;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientChat extends AbstractChat {
    public static void main(String[] args) {

        final ClientChat serverChat = new ClientChat("Chat");
    }


    public ClientChat(String title) {
        super(title);
        sendingThread = new Thread(this);
        reciveThread = new Thread(this);
        sendingThread.start();
        reciveThread.start();

    }

    @Override
    protected void setSocked() throws IOException {

        socket = new Socket(host.getHostName(), PORT);
    }


    @Override
    public void sendMessage(String message) {
        printWriter.println(message);

    }

    @Override
    public void reciveMessage(String message) {
        if (chatBox != null) {
            chatBox.append(String.format("%" + 10 + "s", " ") + message + "\n");
        }
    }


    @Override
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
}
