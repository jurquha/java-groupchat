/*
Developed by Jonah Urquhart
 */

package edu.groupchat.app;

import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class Message {
    private String messageSender;
    private String messageData;
    private MessageType messageType;

    protected Message(MessageType messageType, String messageSender, String messageData) {
        this.messageData = messageData;
        this.messageType = messageType;
        this.messageSender = messageSender;
    }

    protected Message(String messageSender, String messageData) {
        this.messageType = MessageType.STANDARD;
        this.messageData = messageData;
        this.messageSender = messageSender;
    }

    protected Message(byte[] buffer, DatagramPacket datagramPacket) {
        /*
         * Create a Message object from a byte array and datagram, this is used by the ReadThread when messages
         * are received.
         */
        try {
            String rawMessage = new String(buffer, 0, datagramPacket.getLength(), "UTF-8");
            String[] messageArray = rawMessage.split(";");
            this.messageType = MessageType.valueOf(messageArray[0]);
            this.messageSender = messageArray[1];
            this.messageData = messageArray[2];

        } catch (UnsupportedEncodingException ex) {
            System.out.println("Encoding error in receiving and building messasge.");
        }

    }

    public DatagramPacket getDatagram(InetAddress group, int portNumber) {
        String message = getMessageType() + ";" + getMessageSender() + ";" + getMessageData();
        byte[] buffer = message.getBytes();
        DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, group, portNumber);
        return datagram;
    }

    public String getMessageSender() {
        return messageSender;
    }

    public String getMessageData() {
        return messageData;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String toString() {
        String message = messageType.toString() + ';' + messageSender + ';' + messageData;
        return message;
    }

}
