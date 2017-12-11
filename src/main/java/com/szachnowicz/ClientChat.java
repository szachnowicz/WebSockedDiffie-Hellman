package com.szachnowicz;

import com.szachnowicz.DeffHellman.DeffHell;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.Socket;

import static com.szachnowicz.ServerMenager.PORT;

public class ClientChat extends AbstractChat {
    private boolean keyRequested = false;


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
        try {
            socket = new Socket(host.getHostName(), PORT);
        } catch (ConnectException e
                ) {
            System.out.println("Problem with connection with server");
        }

    }


    public void sendMessage(String message) {
        if (printWriter != null) {
            if (authorised) {
                printWriter.println(deffHell.codeMessage(message));
            }
            else{
                printWriter.println(message);
            }
        }
    }

    @Override
    public void reciveMessage(String message) {


        try {
            JSONObject json = new JSONObject(message);
            System.out.println("JSON REVICED :" +json);
            String request = json.getString("request");

            if (request.equals("publicKey")) {
                deffHell = new DeffHell(json.getInt("p"), json.getInt("g"));
                System.out.println(" key recived form server P : " + json.getInt("p") + " G :" + json.getInt("g"));
                sendMessage(deffHell.getCalculMoudloJson());
            }

            if (request.equals("moduloPublicModulo")) {
                System.out.println("mod recived form Server ");
                BigInteger mod = BigInteger.valueOf(json.getInt("mod"));
                System.out.println(mod + " mod form client ");
                deffHell.setEncryKey(mod);
                System.out.println("key " + deffHell.getEncryKey());
                sendMessage(new JSONObject().put("request", "authOK").toString());

            }


            if (request.equals("authOK")) {
                authorised = true;
                System.out.println("Autorisation Granded");
            }



            if (request.equals("message") && chatBox != null && authorised) {
                System.out.println("Message recived : " +json.getString("mod"));
                chatBox.append("FROM SERVER : " + deffHell.decodeMessage(message) + "\n");
            }


        } catch (JSONException exepction) {
            System.out.println("Problem with JSON " + exepction.toString());


        }

    }


    @Override
    public void run() {
        try {
            if (Thread.currentThread() == sendingThread) {
                do {

                    printWriter = new PrintWriter(socket.getOutputStream(), true);
                    if (!keyRequested) {
                        printWriter.println(new JSONObject().put("request", "publicKey").toString());
                        keyRequested = true;
                    }

                    Thread.sleep(500);

                } while (true);

            } else {
                do {
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String out = bufferedReader.readLine();
                    reciveMessage(out);
                    Thread.sleep(500);

                } while (true);
            }
        } catch (Exception e) {
        }
    }
}
