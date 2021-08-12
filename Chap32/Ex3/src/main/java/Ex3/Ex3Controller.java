package Ex3;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Ex3Controller {

    @FXML private ComboBox<String> cbDriver;
    @FXML private ComboBox<String> cbURL;
    @FXML private Label labelMessage;
    @FXML private TextField tfUsername;
    @FXML private PasswordField pfPassword;

    public void initialize(){
        cbDriver.getItems().addAll(FXCollections.observableArrayList("com.mysql.cj.jdbc.Driver"));
        cbURL.getItems().addAll(FXCollections.observableArrayList("jdbc:mysql://localhost:3306/JavaEx"));
    }
    public void onclickConnect(){
        try {
            Class.forName(cbDriver.getValue());
            Connection connection = DriverManager.getConnection(cbURL.getValue(), tfUsername.getText(), pfPassword.getText());
            labelMessage.setText("Connection Successful");
        }
        catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
            labelMessage.setText("No Connection");
        }
    }
}
