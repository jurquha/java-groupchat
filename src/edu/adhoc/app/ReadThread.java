package edu.adhoc.app;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.io.IOException;

public class ReadThread implements Runnable {

    private MulticastSocket socket;
    private InetAddress group;
    private String displayName;
    private int port;
    private boolean exit = false;
    private static final int MAX_LEN = 1000;

    ReadThread(MulticastSocket socket, InetAddress group, int portNumber, String displayName) {
        this.socket = socket;
        this.group = group;
        this.port = portNumber;
        this.displayName = displayName;
    }

    @Override
    public void run() {

        while(!exit) {
            System.out.println("read thread started");
            byte[] buffer = new byte[ReadThread.MAX_LEN];
            DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, group, port);
            try {
                socket.receive(datagram);
                Message message = new Message(buffer, datagram);
                Platform.runLater( () -> {
                    Main.chatRoomController.enterMessage(message);
                    System.out.println("Message type: " + message.getMessageType());
                });
            } catch(IOException e) {
                //TODO fill empty exception
            }
        }
    }
}
