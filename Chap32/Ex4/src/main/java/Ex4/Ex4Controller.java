package Ex4;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

import java.sql.*;

public class Ex4Controller {

    @FXML private TextField tfSSN;
    @FXML private TextArea taResult;
    @FXML private Label lbResult;

    private PreparedStatement ps;

    public void initialize(){
        //Connect to database
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaEx", "root", "");

            //Statement for database
            ps = connection.prepareStatement("select firstname, mi, lastName, title, grade from Student, Enrollment, Course " +
                    "where Student.ssn = ? and Enrollment.ssn = Student.ssn and Enrollment.courseId = Course.courseId");
        }
        catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    public void onclickShowGrade(){
        try{
            int count = 0;
            ps.setString(1, tfSSN.getText());
            ResultSet rSet = ps.executeQuery();

            while (rSet.next()){
                String lastName = rSet.getString(1);
                String mi = rSet.getString(2);
                String firstName = rSet.getString(3);
                String title = rSet.getString(4);
                String grade = rSet.getString(5);

                taResult.appendText(lastName+" "+mi+" "+firstName+"'s grade on course "+title+" is "+grade+"\n");
                count++;
            }
            if (count > 0) {
                lbResult.setText(count + " courses found");
            }
            else
                lbResult.setText("No courses found");
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
