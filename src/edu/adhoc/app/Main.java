package edu.adhoc.app;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
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
        //Parent login = FXMLLoader.load(getClass().getResource("login.fxml"));

//        Stage loginStage = new Stage();
//        loginStage.setTitle("Connect to Chatroom");
//        loginStage.setScene(new Scene(login));
//        loginStage.show();

        primaryStage.setTitle("Wireless Ad Hoc Chat Room Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
