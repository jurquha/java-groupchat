package edu.adhoc.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GroupChat extends Application {

    static ChatRoomController chatRoomController;
    private static Stage primaryStage;

    @Override
    public void start(Stage loginStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        loginStage.setTitle("Wireless Ad Hoc Chat Room Login");
        loginStage.setScene(new Scene(root));
        loginStage.show();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("interface.fxml"));
        Parent room = loader.load();
        chatRoomController = (ChatRoomController) loader.getController();
        primaryStage = new Stage();
        primaryStage.setTitle("Wireless Ad Hoc Chat Room");
        primaryStage.setScene(new Scene(room));
        primaryStage.setOnCloseRequest(event -> chatRoomController.handleOnExit());
    }

    private static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    protected static Stage getPrimaryStage() {
        return primaryStage;
    }

    protected static ChatRoomController getChatRoomController() {
        return chatRoomController;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
