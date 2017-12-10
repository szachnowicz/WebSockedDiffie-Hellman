package com.szachnowicz;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMenager {
  public   static final int PORT = 9876;
    public static void main(String args[]){


        Socket s=null;
        ServerSocket ss2=null;
        System.out.println("Server Listening......");
        try{
            ss2 = new ServerSocket(PORT); // can also use static final PORT_NUM , when defined

        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Server error");

        }

        while(true){
            try{
                s= ss2.accept();
                System.out.println("connection Established");
                ServerChat chat = new ServerChat("server", s);

            }

            catch(Exception e){
                e.printStackTrace();
                System.out.println("Connection Error");

            }
        }

    }
}
