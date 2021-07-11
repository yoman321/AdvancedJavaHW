package Ex2Server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class Ex2Server extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Ex2Server.fxml"));
        Scene scene = (new Scene(root, 600, 400));
        primaryStage.setTitle("Ex2Server");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
