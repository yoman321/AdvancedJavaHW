package Ex2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;

public class Ex2 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("Driver loaded");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaEx", "root", "!Swagyolo123");
        System.out.println("Database connected");
//        Parent root = FXMLLoader.load(getClass().getResource("Ex2.fxml"));
//        primaryStage.setTitle("Ex2");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();
    }
}
