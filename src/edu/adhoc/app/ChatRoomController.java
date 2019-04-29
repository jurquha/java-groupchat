package edu.adhoc.app;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class ChatRoomController {
    @FXML private VBox messageBox;
    @FXML private VBox userListVBox;
    @FXML private TextField enterTextField;
    @FXML private ScrollPane messageBoxScrollPane;
    @FXML private Button submitMessageButton;
    @FXML private TextArea enterTextArea;
    private String displayName;
    private String multicastIP; //TODO chang this to InetAddress object later, if it seems better
    private int portNumber;
    private MulticastSocket socket;
    private InetAddress group;
    private ArrayList<String> userList = new ArrayList<>();

    @FXML
    protected void handleOnActionEnterField(ActionEvent event) {
        if (enterTextField.getText().isEmpty()){
            return;
        }

        Message message = new Message(displayName, enterTextField.getText());
        Text localMessageText = new Text(message.getMessageSender() + ": " + message.getMessageData());
        messageBox.getChildren().add(localMessageText);
        messageBoxScrollPane.vvalueProperty().bind(messageBox.heightProperty());
        System.out.println(message);

        try {
            DatagramPacket messageObjDatagram = message.getDatagram(group, portNumber);
            socket.send(messageObjDatagram);
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Socket error.", ButtonType.CLOSE);
            alert.showAndWait();
        }
        enterTextField.clear();
    }

    @FXML
    protected void handleSubmitMessageButton(ActionEvent event) {
        messageBox.getChildren().add(new Text(enterTextArea.getText()));
    }

    @FXML
    protected void handleOnExit() {
        sendDisconnectMessage();
        Platform.exit();
        System.exit(0);
    }

    protected void connect() {
        messageBox.getChildren().add(new Text(displayName));
        messageBox.getChildren().add(new Text(multicastIP));
        messageBox.getChildren().add(new Text(Integer.toString(portNumber)));
        System.out.println("This is where I connect");
        joinChat(getDisplayName(), getMulticastIP(), getPortNumber());
    }

    @FXML
    private void joinChat(String displayName, String multicastHost, int portNumber) {

        try {
            group = InetAddress.getByName(multicastHost);
            socket = new MulticastSocket(portNumber);
            socket.setTimeToLive(1);
            socket.joinGroup(group);

            Thread thread = new Thread(new ReadThread(socket, group, portNumber, displayName));
            thread.start();
            sendJoinMessage();

        } catch (SocketException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Socket error when attempting to connect to multicast group.", ButtonType.CLOSE);
            alert.showAndWait();
        } catch (UnknownHostException ex) {
        } catch (IOException ex) {
        }
    }

    protected void addToUserList(String user) {
        if (!userList.contains(user)) {
            userList.add(user);
            userListVBox.getChildren().add(new Text(user));
        }
    }

    protected void removeFromUserList(String userString) {
        for (Node user : userListVBox.getChildren()) {
            Text tempUserText = (Text) user;
            if (tempUserText.getText().equals(userString)) {
                userListVBox.getChildren().remove(user);
                return;
            }
        }
    }

    protected void enterMessage(Message receivedMessage) {
        String displayMessage;
        Text displayMessageText;
        Font font = Font.font("SansSerif", FontWeight.BLACK, FontPosture.ITALIC, 12);
        switch (receivedMessage.getMessageType()) {
            case STANDARD:
                if (!receivedMessage.getMessageSender().equals(displayName)){
                    displayMessage = receivedMessage.getMessageSender() + ": " + receivedMessage.getMessageData();
                    displayMessageText = new Text(displayMessage);
                    messageBox.getChildren().add(displayMessageText);
                }
                break;
            case JOIN:
                if (!receivedMessage.getMessageSender().equals(displayName)) {
                    addToUserList(receivedMessage.getMessageSender());
                    sendJoinAckMessage();
                    displayMessage = receivedMessage.getMessageSender() + " has joined the chat!";
                    displayMessageText = new Text(displayMessage);
                    displayMessageText.setFont(font);
                    messageBox.getChildren().add(displayMessageText);
                }
                else{
                    displayMessage = "You have joined the chat!";
                    displayMessageText = new Text(displayMessage);
                    displayMessageText.setFont(font);
                    messageBox.getChildren().add(displayMessageText);
                }
                break;
            case JOIN_ACK:
                addToUserList(receivedMessage.getMessageSender());
                break;
            case DISCONNECT:
                removeFromUserList(receivedMessage.getMessageSender());
                displayMessage = receivedMessage.getMessageSender() + " " + "has left the chat!";
                displayMessageText = new Text(displayMessage);
                displayMessageText.setFont(font);
                messageBox.getChildren().add(displayMessageText);
                break;
        }
        messageBoxScrollPane.vvalueProperty().bind(messageBox.heightProperty());

    }

    protected void sendJoinMessage(){
        try{
            Message joinMessage = new Message(MessageType.JOIN, displayName, "NONE");
            DatagramPacket datagramPacket = joinMessage.getDatagram(group, portNumber);
            socket.send(datagramPacket);
        } catch (IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Socket error when sending JOIN message.", ButtonType.CLOSE);
            alert.showAndWait();
        }

    }

    protected void sendJoinAckMessage() {
        try{
            Message joinAckMessage = new Message(MessageType.JOIN_ACK, displayName, "NONE");
            DatagramPacket datagramPacket = joinAckMessage.getDatagram(group, portNumber);
            socket.send(datagramPacket);
        } catch (IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Socket error when sending JOIN_ACK message.", ButtonType.CLOSE);
            alert.showAndWait();
        }

    }

    protected void sendDisconnectMessage() {
        try {
            Message disconnectMessage = new Message(MessageType.DISCONNECT, displayName, "NONE");
            DatagramPacket datagramPacket = disconnectMessage.getDatagram(group, portNumber);
            socket.send(datagramPacket);
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Socket error when sending JOIN_ACK message.", ButtonType.CLOSE);
            alert.showAndWait();
        }
    }

    protected String getDisplayName() {
        return displayName;
    }

    protected String getMulticastIP() {
        return multicastIP;
    }

    protected int getPortNumber() {
        return portNumber;
    }

    protected void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    protected void setMulticastIP(String multicastIP) {
        this.multicastIP = multicastIP;
    }

    protected void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }
}
