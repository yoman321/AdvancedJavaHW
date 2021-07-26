package Ex9Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Ex9Client extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Ex9Client.fxml"));
        primaryStage.setTitle("Ex9Client");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
