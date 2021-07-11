package Ex1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Ex1Client extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Ex1Client.fxml"));
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Ex1Client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
