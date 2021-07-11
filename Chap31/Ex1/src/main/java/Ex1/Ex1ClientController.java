package Ex1;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import static java.lang.System.out;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Ex1ClientController {

    //Create fxml variables
    @FXML TextField tfAnnualInterest;
    @FXML TextField tfNumberOfYears;
    @FXML TextField tfLoanAmount;
    @FXML TextArea ta;

    //Create server variables
    DataOutputStream toServer = null;
    DataInputStream fromServer = null;

    public void initialize(){
        //Create server
        try{
            Socket socket = new Socket("localhost", 8000);
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
        }
        catch (Exception ex){
            ta.appendText(ex.toString()+"\n");
        }
    }
    public void onclickSubmit(){
        //Communicate to server
        try{
            //Send values to server
            toServer.writeDouble(Double.valueOf(tfAnnualInterest.getText()));
            toServer.writeDouble(Double.valueOf(tfNumberOfYears.getText()));
            toServer.writeDouble(Double.valueOf(tfLoanAmount.getText()));
            toServer.flush();

            //receive values from server
            double monthlyLoanPayment = fromServer.readDouble();
            double totalPayment = fromServer.readDouble();

            ta.appendText("Annual Interest Rate: "+Double.valueOf(tfAnnualInterest.getText())+"\n");
            ta.appendText("Number of Years: "+Double.valueOf(tfNumberOfYears.getText())+"\n");
            ta.appendText("Loan Amount: "+Double.valueOf(tfLoanAmount.getText())+"\n");
            ta.appendText("Monthly Payment is "+monthlyLoanPayment+"\n");
            ta.appendText("Total Payment is "+totalPayment+"\n");
        }
        catch (Exception ex){
            System.err.println(ex);
        }
    }
}
