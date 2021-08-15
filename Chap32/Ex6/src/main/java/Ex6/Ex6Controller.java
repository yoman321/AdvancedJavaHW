package Ex6;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.*;

public class Ex6Controller {

    private Connection connection;
    private Statement s;

    @FXML private ComboBox<String> cbTableName;
    @FXML private TextArea taContents;

    public void initialize(){
        cbTableName.getItems().addAll(FXCollections.observableArrayList("Enrollment", "Student", "Course"));
        taContents.setStyle("-fx-font-family: monospace"); //Allow formatting in TextArea
    }

    public void onclickShowContents(){
        try {
            //Load driver and connect to database
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaEx", "root", "");

            //get values from db and print result
            Statement s = connection.createStatement();
            ResultSet rSet = s.executeQuery("select * from "+cbTableName.getValue());

            ResultSetMetaData rsMetaData = rSet.getMetaData();
            for (int i=1; i<=rsMetaData.getColumnCount(); i++){
                String string = String.format("%-12s\t", rsMetaData.getColumnName(i));
                taContents.appendText(string);
            }
            taContents.appendText("\n");

            while (rSet.next()){
                for (int i=1;  i<=rsMetaData.getColumnCount(); i++){
                    String string = String.format("%-12s\t", rSet.getObject(i));
                    taContents.appendText(string);
                }
                taContents.appendText("\n");
            }
            taContents.appendText("\n");

            connection.close();
        }
        catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
}
