package edu.adhoc.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Controller {
    @FXML private VBox messageBox;
    @FXML private VBox userList;
    @FXML private TextField enterTextField;

    @FXML
    protected void handleOnActionEnterField(ActionEvent event) {
        messageBox.getChildren().add(new Text("won't scroll"));
    }
}
