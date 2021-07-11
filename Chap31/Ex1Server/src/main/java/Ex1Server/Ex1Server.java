package Ex1Server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Ex1Server extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Ex1Server.fxml"));
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Ex1Server");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
