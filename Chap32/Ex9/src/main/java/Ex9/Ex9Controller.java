package Ex9;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

import java.sql.*;

public class Ex9Controller {

    @FXML private TextField tfUsername;
    @FXML private TextField tfPassword;
    @FXML private TextField tfFullName;
    @FXML private TextArea ta;

    public void initialize(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            ta.setStyle("-fx-font-family: monospace");
        }
        catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    public void onclickSubmit(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaEx", "root", "");

            String[] fullNameArray = tfFullName.getText().split(" ");
            if (fullNameArray.length > 1) {
                PreparedStatement ps = connection.prepareStatement("insert into Student1 (username, password, fullname) values (?, ?, ?)");
                ps.setString(1, tfUsername.getText());
                ps.setString(2, tfPassword.getText());
                ps.setString(3, tfFullName.getText());
                ps.executeUpdate();

//                PreparedStatement ps1 = connection.prepareStatement("select fullname from Student1 where Student1.username = ?");
//                ps1.setString(1, "1");
//                ResultSet rSet = ps1.executeQuery();
//
//                while (rSet.next()) {
//                    System.out.println(rSet.getString(1));
//                }
            }
            else
                ta.appendText("The full name does not contain at least a First Name and a Last Name");

            connection.close();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public void onclickShow(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaEx", "root", "");

            PreparedStatement ps = connection.prepareStatement("select username, password, fullname from Student1");
            ResultSet rSet = ps.executeQuery();

            while (rSet.next()){
                boolean inStudent2 = false;
                String username = rSet.getString(1);
                ps = connection.prepareStatement("select count(*) from Student2 where Student2.username = "+username);
                ResultSet rSet1 = ps.executeQuery();

                while (rSet1.next()){
                    if (rSet1.getInt(1) == 1){
                        inStudent2 = true;
                    }
                }
                if (!inStudent2) {
                    String password = rSet.getString(2);
                    String[] fullName = rSet.getString(3).split(" ");

                    ps = connection.prepareStatement("insert into Student2 (username, password, firstname, lastname) values (?, ?, ?, ?)");
                    ps.setString(1, username);
                    ps.setString(2, password);
                    ps.setString(3, fullName[0]);
                    if (fullName.length == 2) {
                        ps.setString(4, fullName[1]);
                    } else
                        ps.setString(4, fullName[2]);

                    ps.executeUpdate();
                }
            }
            ps = connection.prepareStatement("select username, password, firstname, lastname from Student2");
            rSet = ps.executeQuery();

            ResultSetMetaData rsMetaData = rSet.getMetaData();
            for (int i=1; i<=rsMetaData.getColumnCount(); i++){
                String string = String.format("%-12s\t", rsMetaData.getColumnName(i));
                ta.appendText(string);
            }
            ta.appendText("\n");

            while (rSet.next()){
                for (int i=1; i<=rsMetaData.getColumnCount(); i++){
                    String string = String.format("%-12s\t", rSet.getObject(i));
                    ta.appendText(string);
                }
                ta.appendText("\n");
            }
            ta.appendText("\n");

            connection.close();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
