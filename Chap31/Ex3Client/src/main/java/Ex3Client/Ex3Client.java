package Ex3Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Ex3Client extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Ex3Client.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Ex3Client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
