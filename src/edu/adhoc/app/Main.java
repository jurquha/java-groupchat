package edu.adhoc.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    static ChatRoomController chatRoomController;
    public String displayName;
    public String multicastIP;
    public int portNumber;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("interface.fxml"));
        Parent login = FXMLLoader.load(getClass().getResource("login.fxml"));

        Stage loginStage = new Stage();
        loginStage.setTitle("Connect to Chatroom");
        loginStage.setScene(new Scene(login));
        loginStage.show();

        primaryStage.setTitle("Wireless Ad Hoc Chat Room");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        /**
         * New idea for later: initialize and show both windows here, then later close the connection
         * window and pass the info to the other window, instead of opening one then closing it and opening the other
         *
         * Also: have two separate controllers. One chatRoomController shared by all seems like bad practice
         */

    }

    public String getDisplayName() {
        return displayName;
    }

    public String getMulticastIP() {
        return multicastIP;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setMulticastIP(String multicastIP) {
        this.multicastIP = multicastIP;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
