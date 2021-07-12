package Ex2Client;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class Ex2Client extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Ex2Client.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Ex2Client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
