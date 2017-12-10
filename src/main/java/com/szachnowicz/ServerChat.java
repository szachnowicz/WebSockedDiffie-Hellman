package com.szachnowicz;

import com.szachnowicz.DeffHellman.DeffHell;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

import static com.szachnowicz.ServerMenager.PORT;

public class ServerChat extends AbstractChat {
//    private ServerSocket server;
    private boolean keyRequested = false;

    public static void main(String[] args) {
//        Socket s=null;
//        ServerSocket ss2=null;
//
//        try{
//            ss2   = new ServerSocket(PORT); // can also use static final PORT_NUM , when defined
//
//        }
//        catch(IOException e){
//            e.printStackTrace();
//            System.out.println("Server error");
//
//        }
//
//
//
//
//
//
//        final ServerChat serverChat = new ServerChat("server");
//
////

    }


    public ServerChat(String title, Socket socket) {
        super(title);
        this.socket = socket;
        deffHell = new DeffHell();
        sendingThread = new Thread(this);
        reciveThread = new Thread(this);
        sendingThread.start();
        reciveThread.start();


    }

    @Override
    protected void setSocked() throws ClassNotFoundException, IOException {

//        server = new ServerSocket(PORT);
//        System.out.println("Server is waiting. . . . ");
//        socket = server.accept();
//        System.out.println("Client connected with Ip " + socket.getInetAddress().getHostAddress());


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
        if (printWriter != null) {

            if (authorised) {
                printWriter.println(deffHell.codeMessage(message));
            } else {
                printWriter.println(message);
            }

        }
    }

    @Override
    public void reciveMessage(String message) {


        try {
            JSONObject json = new JSONObject(message);
            String request = json.getString("request");

            if (request.equals("message") && chatBox != null && authorised) {
                System.out.println("Message recived : " +json.getString("mod"));
                chatBox.append("FROM CLIENT : " + deffHell.decodeMessage(message) + "\n");
            }


            if (request.equals("publicKey")) {
                sendPublicKeys();
            }

            if (request.equals("moduloPublicModulo")) {
                System.out.println("mod recived form cilent ");
                BigInteger mod = BigInteger.valueOf(json.getInt("mod"));
                deffHell.setEncryKey(mod);
                System.out.println(mod + " mod form server ");
                sendMessage(deffHell.getCalculMoudloJson());
                System.out.println("key " + deffHell.getEncryKey());


            }
            if (request.equals("authOK")) {
                sendMessage(new JSONObject().put("request", "authOK").toString());
                authorised = true;
                System.out.println("Autorisation Granded");

            }


        } catch (JSONException exepction) {
            System.out.println("Problem with JSON " + exepction.toString());


        }
    }

    private void sendPublicKeys() {
        sendMessage(deffHell.getKey());
    }


}
