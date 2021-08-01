package Ex1;

import javafx.fxml.FXML;

import java.sql.*;
import java.util.concurrent.Callable;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Ex1Controller {

    private Connection connection;
    private CallableStatement cs;
    private PreparedStatement ps;

    @FXML private Text textMessage;
    @FXML private TextField tfId;
    @FXML private TextField tfLastName;
    @FXML private TextField tfFirstName;
    @FXML private TextField tfMi;
    @FXML private TextField tfAddress;
    @FXML private TextField tfCity;
    @FXML private TextField tfState;
    @FXML private TextField tfTelephone;
    @FXML private TextField tfEmail;

    public void initialize() throws Exception{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loader");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaEx", "root", "!Swagyolo123");
            System.out.println("Database connected");

//            String queryString = "insert into Staff(id, lastName, firstName, mi, address, city, state, telephone, email) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
//            ps = connection.prepareStatement(queryString);
//            ps.setString(1, "1");
//            ps.setString(2, "2");
//            ps.setString(3, "3");
//            ps.setString(4, "4");
//            ps.setString(5, "5");
//            ps.setString(6, "6");
//            ps.setString(7, "7");
//            ps.setString(8, "8");
//            ps.setString(9, "9");
//            ps.executeUpdate();
//
//            String qs2 = "select id, lastName, firstName, mi, address, city, state, telephone, email from Staff where Staff.id = ?";
//            ps2 = connection.prepareStatement(qs2);
//
//            ps2.setString(1, "1");
//            ResultSet rSet = ps2.executeQuery();
//
//            if (rSet.next()){
//                String lastName = rSet.getString(2);
//                String firstName = rSet.getString(3);
//                String email = rSet.getString(9);
//                System.out.println("Last Name: "+lastName+" First Name: "+firstName);
//            }
//            CallableStatement insertCall = connection.prepareCall("{call insertStaff(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
//            insertCall.setString(1, "z");
//            insertCall.setString(2, "b");
//            insertCall.setString(3, "c");
//            insertCall.setString(4, "d");
//            insertCall.setString(5, "e");
//            insertCall.setString(6, "f");
//            insertCall.setString(7, "g");
//            insertCall.setString(8, "h");
//            insertCall.setString(9, "i");
//            insertCall.executeUpdate();

//            String qs2 = "select id, lastName, firstName, mi, address, city, state, telephone, email from Staff where Staff.id = ?";
//            ps2 = connection.prepareStatement(qs2);
//
//            ps2.setString(1, "a");
//            ResultSet rSet = ps2.executeQuery();
//
//            if (rSet.next()){
//                String lastName = rSet.getString(2);
//                String firstName = rSet.getString(3);
//                String email = rSet.getString(9);
//                System.out.println("Last Name: "+lastName+" First Name: "+firstName);
//            }
//            CallableStatement cs = connection.prepareCall("{call staffFound(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
//            cs.setString(1, "b");
//            cs.registerOutParameter(2, Types.VARCHAR);
//            cs.registerOutParameter(3, Types.VARCHAR);
//            cs.registerOutParameter(4, Types.CHAR);
//            cs.registerOutParameter(5, Types.VARCHAR);
//            cs.registerOutParameter(6, Types.VARCHAR);
//            cs.registerOutParameter(7, Types.CHAR);
//            cs.registerOutParameter(8, Types.CHAR);
//            cs.registerOutParameter(9, Types.VARCHAR);
//            cs.execute();

//            if (!cs.getString(2).equals("0")){
//                System.out.println("First Name: "+cs.getString(2)+" Last Name: "+cs.getString(3));
//            }
//            else
//                System.out.println("Non existent in database");
        }
        catch (SQLException ex){
            ex.printStackTrace();

        }
    }
    public void onclickView(){
        try {
            cs = connection.prepareCall("{call staffFound(?, ?, ?, ?, ?, ?, ?, ?, ?)}");

            if (tfId.getText().equals("")) {
                textMessage.setText("ID not given");
            } else {
                cs.setString(1, tfId.getText());
                cs.registerOutParameter(2, Types.VARCHAR);
                cs.registerOutParameter(3, Types.VARCHAR);
                cs.registerOutParameter(4, Types.CHAR);
                cs.registerOutParameter(5, Types.VARCHAR);
                cs.registerOutParameter(6, Types.VARCHAR);
                cs.registerOutParameter(7, Types.CHAR);
                cs.registerOutParameter(8, Types.CHAR);
                cs.registerOutParameter(9, Types.VARCHAR);
                cs.execute();

                if (!cs.getString(2).equals("0")){
                    tfLastName.setText(cs.getString(2));
                    tfFirstName.setText(cs.getString(3));
                    tfMi.setText(cs.getString(4));
                    tfAddress.setText(cs.getString(5));
                    tfCity.setText(cs.getString(6));
                    tfState.setText(cs.getString(7));
                    tfTelephone.setText(cs.getString(8));
                    tfEmail.setText(cs.getString(9));
                }
                else
                    textMessage.setText("Staff with specified ID does not exist");

                cs.close();
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public void onclickInsert(){
        try {
            //Check if id already existent
            ps = connection.prepareStatement("select id, firstName, lastName, mi, address, city, state, telephone, email from Staff where Staff.id = ?");
            ps.setString(1, tfId.getText());
            ResultSet rSet = ps.executeQuery();

            if (rSet.next()) {
                textMessage.setText("ID already exist");
            } else {
                cs = connection.prepareCall("{call insertStaff(?, ?, ?, ?, ?, ?, ?, ?, ?)}");

                if (tfId.getText().equals("")) {
                    textMessage.setText("ID has not been provided");
                } else {
                    cs.setString(1, tfId.getText());
                    cs.setString(2, tfLastName.getText());
                    cs.setString(3, tfFirstName.getText());
                    cs.setString(4, tfMi.getText());
                    cs.setString(5, tfAddress.getText());
                    cs.setString(6, tfCity.getText());
                    cs.setString(7, tfState.getText());
                    cs.setString(8, tfTelephone.getText());
                    cs.setString(9, tfEmail.getText());
                    cs.executeUpdate();
                }
                cs.close();
            }
            ps.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void onclickUpdate(){
        try{
            cs = connection.prepareCall("{? = call updateStaff(?, ?, ?, ?, ?, ?, ?, ?, ?)}");

            if (tfId.getText().equals("")){
                textMessage.setText("ID has not been provided");
            }
            else {
                cs.setString(2, tfId.getText());
                cs.setString(3, tfLastName.getText());
                cs.setString(4, tfFirstName.getText());
                cs.setString(5, tfMi.getText());
                cs.setString(6, tfAddress.getText());
                cs.setString(7, tfCity.getText());
                cs.setString(8, tfState.getText());
                cs.setString(9, tfTelephone.getText());
                cs.setString(10, tfEmail.getText());
                cs.registerOutParameter(1, Types.INTEGER);
                cs.executeUpdate();

                if (cs.getInt(1) == 1){
                    textMessage.setText("Update Successful");
                }
                else
                    textMessage.setText("Update Non-Successful");
            }
            cs.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void onclickClear(){
        try {
            cs = connection.prepareCall("{? = call clearStaff(?)}");
            cs.setString(2, tfId.getText());
            cs.registerOutParameter(1, Types.BOOLEAN);
            cs.executeUpdate();

            if (cs.getBoolean(1)){
                textMessage.setText("Clear Successful");
            }
            else
                textMessage.setText("Clear Non-Successful");

            cs.close();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
