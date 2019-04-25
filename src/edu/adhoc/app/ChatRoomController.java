package edu.adhoc.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.*;

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
    private InetAddress room;

    @FXML
    protected void handleOnActionEnterField(ActionEvent event) {
        messageBox.getChildren().add(new Text(enterTextField.getText()));
        messageBoxScrollPane.vvalueProperty().bind(messageBox.heightProperty());

        try {
            String message = getDisplayName() + ":" + enterTextField.getText();
            byte[] buffer = message.getBytes();
            DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, room, portNumber);
            System.out.println("sending datagram: " + datagram);
            socket.send(datagram);
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
            room = InetAddress.getByName(multicastHost);
            socket = new MulticastSocket(portNumber);

            socket.setTimeToLive(1);

            socket.joinGroup(room);


            Thread thread = new Thread(new ReadThread(socket, room, portNumber, displayName));
            System.out.println("about to start new thread");
            thread.start();
            System.out.println("thread started");

//            enterTextField.setOnAction((ActionEvent e) -> {
//                try {
//                    String message = getDisplayName() + ":" + enterTextField.getText();
//                    byte[] buffer = message.getBytes();
//                    DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, room, portNumber);
//                    socket.send(datagram);
//                } catch (IOException ex) {
//                    Alert alert = new Alert(Alert.AlertType.ERROR, "Socket error.", ButtonType.CLOSE);
//                    alert.showAndWait();
//                    //ex.printStackTrace();
//                }
//
//            });

        } catch (SocketException ex) {
        } catch (UnknownHostException ex) {
        } catch (IOException ex) {
        }
    }

    protected void enterMessage(String message) {
        messageBox.getChildren().add(new Text(message));
    }

    protected Text createMessage(){
        return new Text("sample return to make intellij happy");
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
