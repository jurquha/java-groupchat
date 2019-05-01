/*
Developed by Jonah Urquhart
 */

package edu.groupchat.app;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.io.IOException;

public class ReadThread implements Runnable {

    private MulticastSocket socket;
    private InetAddress group;
    private int port;
    private boolean exit = false;
    private static final int MAX_LEN = 1000;

    ReadThread(MulticastSocket socket, InetAddress group, int portNumber) {
        this.socket = socket;
        this.group = group;
        this.port = portNumber;
    }

    @Override
    public void run() {
        /*
         * Run constantly until exit is set to true (currently exit will never be set to true,
         * but this is left in for future applications). This constantly listens to the specified
         * multicast group and port for new datagrams, and uses those received messages to
         * create a new Message object, which is sent to the ChatRoomController
         */
        while(!exit) {
            byte[] buffer = new byte[ReadThread.MAX_LEN];
            DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, group, port);
            try {
                socket.receive(datagram);
                Message message = new Message(buffer, datagram);
                Platform.runLater( () -> {
                    GroupChat.chatRoomController.enterMessage(message);
                });
            } catch(IOException e) {
                Platform.runLater(() -> {
                    System.out.println("Unexpected IOException in ReadThread");
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Unexpected IOException in ReadThread.", ButtonType.CLOSE);
                    alert.showAndWait();
                });
            }
        }
    }
}
