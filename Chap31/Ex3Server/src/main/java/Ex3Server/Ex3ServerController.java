package Ex3Server;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Ex3ServerController {

    @FXML private TextArea ta;
    private int clientNo = 0;

    public void initialize(){
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(8000);
                ta.appendText("MultiThreadServer started at " + new Date() + " \n");

                while (true) {
                    Socket socket = serverSocket.accept();
                    clientNo++;
                    Platform.runLater(() -> ta.appendText("Starting thread for client "+clientNo+" at "+new Date()+" \n"));

                    InetAddress inetAddress = socket.getInetAddress();
                    Platform.runLater(() -> {
                        ta.appendText("Client " + clientNo + "'s host name is " + inetAddress.getHostName() + " \n");
                        ta.appendText("Client " + clientNo + "'s IP address is " + inetAddress.getHostAddress() + " \n");
                    });

                    new Thread(new HandleClient(socket)).start();
                }

            }
            catch(IOException ex){
                ex.printStackTrace();
            }

        }).start();
    }
    private class HandleClient implements Runnable{

        private Socket socket;

        public HandleClient(Socket socket){
            this.socket = socket;
        }
        public void run(){
            try{
                DataInputStream fromClient = new DataInputStream(socket.getInputStream());
                DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());

                while (true){
                    double annualInterestRate = fromClient.readDouble();
                    int numberOfYears = fromClient.readInt();
                    double loanAmount = fromClient.readDouble();

                    double monthlyInterestRate = annualInterestRate / 1200;
                    double monthlyPayment = loanAmount *monthlyInterestRate / (1 - 1 / Math.pow(1 + monthlyInterestRate, numberOfYears * 12));
                    double totalPayment = monthlyPayment * 12 * numberOfYears;

                    toClient.writeDouble(monthlyPayment);
                    toClient.writeDouble(totalPayment);
                    toClient.flush();

                    Platform.runLater(() -> {
                       ta.appendText("Client "+clientNo+"'s \n" +
                               "Annual Interest Rate: "+annualInterestRate+"\n" +
                               "Number of Years: "+numberOfYears+"\n" +
                               "Loan Amount: "+loanAmount+"\n"+
                               "Monthly Payment: "+monthlyPayment+"\n"+
                               "Total Payment: "+totalPayment+"\n"
                        );
                    });
                }
            }
            catch (IOException ex){
                System.err.println(ex);
            }
        }
    }

}
