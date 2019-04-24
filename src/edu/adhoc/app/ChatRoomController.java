package edu.adhoc.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChatRoomController {
    @FXML private VBox messageBox;
    @FXML private VBox userListVBox;
    @FXML private TextField enterTextField;

    @FXML
    protected void handleOnActionEnterField(ActionEvent event) {
        messageBox.getChildren().add(new Text(enterTextField.getText()));
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