package edu.adhoc.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChatRoomController {
    @FXML private VBox messageBox;
    @FXML private VBox userListVBox;
    @FXML private TextField enterTextField;
    @FXML private ScrollPane messageBoxScrollPane;
    @FXML private Button submitMessageButton;
    @FXML private TextArea enterTextArea;
    private String displayName;
    private String multicastIP; //TODO chang this to InetAddress object later, this string is purely for testing UI
    private String portNumber;

    @FXML
    protected void handleOnActionEnterField(ActionEvent event) {
        messageBox.getChildren().add(new Text(enterTextField.getText()));
        messageBoxScrollPane.vvalueProperty().bind(messageBox.heightProperty());
    }

    @FXML
    protected void handleSubmitMessageButton(ActionEvent event) {
        messageBox.getChildren().add(new Text(enterTextArea.getText()));
    }

    protected void connect() {
        messageBox.getChildren().add(new Text(displayName));
        messageBox.getChildren().add(new Text(multicastIP));
        messageBox.getChildren().add(new Text(portNumber));
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

    protected String getPortNumber() {
        return portNumber;
    }

    protected void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    protected void setMulticastIP(String multicastIP) {
        this.multicastIP = multicastIP;
    }

    protected void setPortNumber(String portNumber) {
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
