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
            String message;
            try {
                socket.receive(datagram);
                message = new String(buffer,0,datagram.getLength(),"UTF-8");
                Message messageObj = new Message(buffer, datagram);
                if(!message.startsWith(displayName)){
                    System.out.println(message);
                    Platform.runLater( () -> {
//                        String user = message.substring(0, message.indexOf(':'));
//                        Main.chatRoomController.addToUserList(user);
//                        Main.chatRoomController.enterMessage(message);

                        //Main.chatRoomController.addToUserList(messageObj.getMessageSender());
                        Main.chatRoomController.enterMessage(messageObj);
                        System.out.println("Message type: " + messageObj.getMessageType());
                    });
                    //figure out how to push this message to the vbox
                }
            } catch(IOException e) {
                //fill empty exception
            }
        }
    }
}
