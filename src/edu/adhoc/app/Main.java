package edu.adhoc.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.*;

public class Main extends Application {

    static ChatRoomController chatRoomController;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        //Parent room = FXMLLoader.load(getClass().getResource("login.fxml"));

//        Stage loginStage = new Stage();
//        loginStage.setTitle("Connect to Chatroom");
//        loginStage.setScene(new Scene(login));
//        loginStage.show();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("interface.fxml"));
        Parent room = loader.load();
        chatRoomController = (ChatRoomController) loader.getController();

        primaryStage.setTitle("Wireless Ad Hoc Chat Room Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        Stage secondStage = new Stage();
        secondStage.setTitle("Wireless Ad Hoc Chat Room");
        secondStage.setScene(new Scene(room));
        secondStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
