package edu.adhoc.app;

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
            byte[] buffer = new byte[ReadThread.MAX_LEN];
            DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, group, port);
            String message;
            try {
                socket.receive(datagram);
                message = new String(buffer,0,datagram.getLength(),"UTF-8");
                if(!message.startsWith(displayName)){
                    //figure out how to push this message to the vbox
                }
            } catch(IOException e) {
                //fill empty exception
            }
        }
    }
}
