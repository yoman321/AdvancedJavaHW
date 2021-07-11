package Ex1Server;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Ex1ServerController {
    @FXML
    TextArea ta;

    public void initialize(){
        startServer();
    }
    public void startServer(){
        //Create server socket
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(8000);
                ta.appendText("Server started at " + new Date() + "\n");

                Socket socket = serverSocket.accept();
                DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
                DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());

                while (true) {
                    double annualInterest = inputFromClient.readDouble();
                    double numberOfYears = inputFromClient.readDouble();
                    double loanAmount = inputFromClient.readDouble();

                    double monthlyInterestRate = annualInterest / 1200;
                    double monthlyPayment = loanAmount * monthlyInterestRate /
                            (1 - (1 / Math.pow(1 + monthlyInterestRate, numberOfYears * 12)));
                    double totalPayment = monthlyPayment * numberOfYears * 12;

                    outputToClient.writeDouble(monthlyPayment);
                    outputToClient.writeDouble(totalPayment);

                    Platform.runLater(() -> {
                        ta.appendText("Client Connected at "+new Date());
                        ta.appendText("Annual Interest Rate: "+annualInterest+"\n");
                        ta.appendText("Number of Years: "+numberOfYears+"\n");
                        ta.appendText("Loan Amount: "+loanAmount+"\n");
                        ta.appendText("Monthly Payment is "+monthlyPayment+"\n");
                        ta.appendText("Total Payment is "+totalPayment);
                    });

                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }
}
