package Ex5Client;

import Loan.LoanClass;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;

public class Ex5ClientController {

    private DataInputStream fromServer;
    private ObjectOutputStream toServer;

    @FXML private TextArea ta;
    @FXML private TextField tfAnnualInterestRate;
    @FXML private TextField tfNumberOfYears;
    @FXML private TextField tfLoanAmount;

    public void initialize(){
        try {
            Socket socket = new Socket("localhost", 8000);
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (IOException ex){
            ta.appendText(ex.toString()+"\n");
        }
    }
    public void onclickSubmit(){
        try{
            toServer.writeObject(new LoanClass(Double.parseDouble(tfAnnualInterestRate.getText().trim()),
                    Integer.parseInt(tfNumberOfYears.getText().trim()), Double.parseDouble(tfLoanAmount.getText().trim())));
            toServer.flush();

            double monthlyPayment = fromServer.readDouble();
            double totalPayment = fromServer.readDouble();
            System.out.println("something");

            ta.appendText("Monthly Payment: "+monthlyPayment+"\n" +
                    "Total Payment: "+totalPayment+"\n");

        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
