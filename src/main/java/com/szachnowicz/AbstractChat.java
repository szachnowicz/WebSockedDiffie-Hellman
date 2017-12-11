package com.szachnowicz;

import com.szachnowicz.DeffHellman.DeffHell;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class AbstractChat implements Runnable {

    private JFrame chatFrame;
    private JTextField messageBox;
    private JButton sendMessage;
    protected JTextArea chatBox;


    protected InetAddress host;
    protected Socket socket = null;
    protected Thread sendingThread, reciveThread;
    protected BufferedReader bufferedReader;
    protected PrintWriter printWriter;

    protected DeffHell deffHell;
    protected boolean authorised;

    public AbstractChat(String title) {
        chatFrame = new JFrame(title);
        display();
        try {
            host = InetAddress.getLocalHost();
            setSocked();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public abstract void sendMessage(String message);

    protected abstract void setSocked() throws IOException, ClassNotFoundException;

    public abstract void reciveMessage(String message);




    public void display() {


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.BLUE);
        southPanel.setLayout(new GridBagLayout());

        messageBox = new JTextField(30);
        messageBox.requestFocusInWindow();

        sendMessage = new JButton("Send Message");
        sendMessage.addActionListener(event -> {

            final String messageBoxText = messageBox.getText();
            if (messageBoxText.length() < 1) {
                return;
            }


            if (messageBoxText.equals(".clear")) {
                chatBox.setText("Cleared all messages\n");
                messageBox.setText("");
            } else {
                this.sendMessage(messageBoxText);
                messageBox.setText("");
            }
            messageBox.requestFocusInWindow();

        });

        chatBox = new JTextArea();
        chatBox.setEditable(false);
        chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
        chatBox.setLineWrap(true);

        mainPanel.add(new JScrollPane(chatBox), BorderLayout.CENTER);

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        GridBagConstraints right = new GridBagConstraints();
        right.insets = new Insets(0, 10, 0, 0);
        right.anchor = GridBagConstraints.LINE_END;
        right.fill = GridBagConstraints.NONE;
        right.weightx = 1.0D;
        right.weighty = 1.0D;

        southPanel.add(messageBox, left);
        southPanel.add(sendMessage, right);

        mainPanel.add(BorderLayout.SOUTH, southPanel);

        chatFrame.add(mainPanel);
        chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatFrame.setSize(470, 300);
        chatFrame.setVisible(true);
        chatFrame.setLocationRelativeTo(null);


        chatFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {

                System.out.println("conection closed");

                System.exit(0);

            }
        });
    }


}
