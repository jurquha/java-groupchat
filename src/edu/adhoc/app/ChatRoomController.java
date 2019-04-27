package edu.adhoc.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
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
    private String multicastIP; //TODO chang this to InetAddress object later, this string is purely for testing UI
    private int portNumber;
    private MulticastSocket socket;
    private InetAddress group;
    private ArrayList<String> userList = new ArrayList<>();

    @FXML
    protected void handleOnActionEnterField(ActionEvent event) {
        String localMessage = displayName + ": " + enterTextField.getText();
        Text text = new Text(localMessage);

        messageBox.getChildren().add(new Text(localMessage));
        messageBoxScrollPane.vvalueProperty().bind(messageBox.heightProperty());

        Message messageInstance = new Message(displayName, enterTextField.getText());
        System.out.println(messageInstance);

        try {
            String message = getDisplayName() + ": " + enterTextField.getText();
            byte[] buffer = message.getBytes();
            DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, group, portNumber);
            System.out.println("sending datagram: " + datagram);
            //socket.send(datagram);

            DatagramPacket messageObjDatagram = messageInstance.getDatagram(group, portNumber);
            socket.send(messageObjDatagram);
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Socket error.", ButtonType.CLOSE);
            alert.showAndWait();
            //ex.printStackTrace();
        }
    }

    @FXML
    protected void handleSubmitMessageButton(ActionEvent event) {
        messageBox.getChildren().add(new Text(enterTextArea.getText()));
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
        /**
         * should this method be in here or in main?
         *if it is in here it can easily access UI events
         * if it is in main getting notified on action events could prove to be tricky, but would likely be better
         */

        try {
            System.out.println("trying to connect:");
            group = InetAddress.getByName(multicastHost);
            socket = new MulticastSocket(portNumber);
            socket.setTimeToLive(1);
            socket.joinGroup(group);

            Thread thread = new Thread(new ReadThread(socket, group, portNumber, displayName));
            System.out.println("about to start new thread");
            thread.start();
            System.out.println("thread started");
            sendJoinMessage();

        } catch (SocketException ex) {
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

    protected void enterMessage(Message receivedMessage) {
        switch (receivedMessage.getMessageType()) {
            case STANDARD:
                if (!receivedMessage.getMessageSender().equals(displayName)){
                    String displayMessage = receivedMessage.getMessageSender() + ": " + receivedMessage.getMessageData();
                    messageBox.getChildren().add(new Text(displayMessage));
                }
                break;
            case JOIN:
                addToUserList(receivedMessage.getMessageSender());
                sendJoinAckMessage();
                break;
            case JOIN_ACK:
                addToUserList(receivedMessage.getMessageSender());
                break;
        }
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

//    @FXML
//    protected void handleConnectButtonOnAction(ActionEvent event) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("interface.fxml"));
//        Stage secondStage = new Stage();
//        secondStage.setTitle("Wireless Ad Hoc Chat Room");
//        secondStage.setScene(new Scene(root));
//        secondStage.show();
//
//        Node source = (Node) event.getSource();
//        Stage stage = (Stage) source.getScene().getWindow();
//        stage.close();
//    }
}
