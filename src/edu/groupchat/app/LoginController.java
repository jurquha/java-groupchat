/*
Developed by Jonah Urquhart
 */

package edu.groupchat.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LoginController {

    @FXML private TextField userNameTextField;
    @FXML private TextField ipAddressTextField;
    @FXML private TextField portNumberTextField;
    @FXML private Button connectButton;

    @FXML
    protected void handleConnectButtonOnAction(ActionEvent event) throws Exception {

        if (userNameTextField.getText().isEmpty() || ipAddressTextField.getText().isEmpty() || portNumberTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No fields can be left empty.", ButtonType.CLOSE);
            alert.showAndWait();
            return;
        }
        try {
            InetAddress group = InetAddress.getByName(ipAddressTextField.getText());
            if (!group.isMulticastAddress()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The multicast address entered is not a valid multicast address.", ButtonType.CLOSE);
                alert.showAndWait();
                return;
            }
        } catch (UnknownHostException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid multicast address.", ButtonType.CLOSE);
            alert.showAndWait();
            return;
        }
        GroupChat.getChatRoomController().setDisplayName(userNameTextField.getText());
        GroupChat.getChatRoomController().setMulticastIP(ipAddressTextField.getText());
        GroupChat.getChatRoomController().setPortNumber(Integer.parseInt(portNumberTextField.getText()));
        GroupChat.getChatRoomController().addToUserList(userNameTextField.getText());
        GroupChat.getPrimaryStage().show();

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

        GroupChat.getChatRoomController().connect();

    }

}
