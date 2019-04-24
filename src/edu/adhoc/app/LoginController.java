package edu.adhoc.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField userNameTextField;
    @FXML private TextField ipAddressTextField;
    @FXML private TextField portNumberTextField;
    @FXML private Button connectButton;



    @FXML
    protected void handleConnectButtonOnAction(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("interface.fxml"));
        Parent root = loader.load();
        ChatRoomController chatRoomController = loader.getController();
        chatRoomController.setDisplayName(userNameTextField.getText());
        chatRoomController.setMulticastIP(ipAddressTextField.getText());
        chatRoomController.setPortNumber(portNumberTextField.getText());

        Stage secondStage = new Stage();
        secondStage.setTitle("Wireless Ad Hoc Chat Room");
        secondStage.setScene(new Scene(root));
        secondStage.show();

        chatRoomController.connect();

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }


}
