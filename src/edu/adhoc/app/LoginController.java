package edu.adhoc.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

        if (userNameTextField.getText().isEmpty() || ipAddressTextField.getText().isEmpty() || portNumberTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No fields can be left empty.", ButtonType.CLOSE);
            alert.showAndWait();
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("interface.fxml"));
        Parent root = loader.load();
        ChatRoomController chatRoomController = loader.getController();
        chatRoomController.setDisplayName(userNameTextField.getText());
        chatRoomController.setMulticastIP(ipAddressTextField.getText());
        chatRoomController.setPortNumber(Integer.parseInt(portNumberTextField.getText()));

        Stage secondStage = new Stage();
        secondStage.setTitle("Wireless Ad Hoc Chat Room");
        secondStage.setScene(new Scene(root));
        secondStage.show();

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

        chatRoomController.connect();

    }


}
