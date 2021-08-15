package Ex5;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

import java.sql.*;

public class Ex5Controller {

    @FXML private TextField tfTableName;
    @FXML private TextArea taContents;

    private Statement s;
    private Connection connection;

    public void initialize(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaEx", "root", "");

        }
        catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    public void onclickShowContents(){
        try {
            s = connection.createStatement();
            ResultSet rSet = s.executeQuery("select * from "+tfTableName.getText());
            
            ResultSetMetaData rsMetaData = rSet.getMetaData();
            for (int i=1; i<=rsMetaData.getColumnCount(); i++){
                String string = String.format("string %-12s\t", rsMetaData.getColumnName(i));
                taContents.appendText(string);
            }
            taContents.appendText("\n");

            while (rSet.next()){
                for (int i=1; i<=rsMetaData.getColumnCount(); i++){
                    String string = String.format("string %-12s\t", rSet.getObject(i));
                    taContents.appendText(string);
                }
                taContents.appendText("\n");
            }
            s.close();
        }
        catch (SQLException ex){
            ex.printStackTrace();
            taContents.appendText("The table does not exists in this database");
        }

    }
}
