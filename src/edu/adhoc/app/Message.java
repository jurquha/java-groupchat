package edu.adhoc.app;

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
