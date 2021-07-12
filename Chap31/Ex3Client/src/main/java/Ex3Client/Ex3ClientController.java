package Ex3Client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Ex3ClientController {

    @FXML private TextField tfAnnualInterestRate;
    @FXML private TextField tfNumberOfYears;
    @FXML private TextField tfLoanAmount;
    @FXML private TextArea ta;

    private DataInputStream fromServer;
    private DataOutputStream toServer;

    public void initialize() throws Exception{
        try {
            Socket socket = new Socket("localhost", 8000);
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException ex){
            ta.appendText(ex.toString()+"\n");
        }
    }
    public void onclickSubmit(){
        try{
            toServer.writeDouble(Double.valueOf(tfAnnualInterestRate.getText()));
            toServer.writeInt(Integer.valueOf(tfNumberOfYears.getText()));
            toServer.writeDouble(Double.valueOf(tfLoanAmount.getText()));
            toServer.flush();

            double monthlyPayment = fromServer.readDouble();
            double totalPayment = fromServer.readDouble();

            Platform.runLater(() -> {
                ta.appendText("The annual interest rate is "+tfAnnualInterestRate.getText()+"\n");
                ta.appendText("The number of years is "+tfNumberOfYears.getText()+"\n");
                ta.appendText("The loan amount is "+tfLoanAmount.getText()+"\n");
                ta.appendText("The monthly payment is "+monthlyPayment+"\n");
                ta.appendText("The total payment is "+totalPayment+"\n");
            });
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
