package Ex5Server;

import Loan.LoanClass;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

import java.net.ServerSocket;

public class Ex5ServerController {

    @FXML private TextArea ta;

    public void initialize(){
        new Thread(() -> {
            try{
                ServerSocket ss = new ServerSocket(8000);
                ta.appendText("Server has been created on "+new Date()+"\n");

                while (true){
                    Socket socket = ss.accept();
                    InetAddress inet = socket.getInetAddress();
                    Platform.runLater(() -> ta.appendText("Started new thread for client at "+new Date()+"\n"));
                    Platform.runLater(() -> ta.appendText(inet.getHostName()+" / "+inet.getHostAddress()+"\n"));
                    new Thread(new HandleClient(socket)).start();
                }
            }
            catch (Exception ex){
                System.err.println(ex);
            }
        }).start();
    }
    private class HandleClient implements Runnable{

        private Socket socket;

        public HandleClient (Socket socket){
            this.socket = socket;
        }
        @Override
        public void run() {
            try {
                System.out.println("nothing");
                ObjectInputStream fromClient = new ObjectInputStream(socket.getInputStream());
                DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());

                while (true) {
                    System.out.println("something");
                    LoanClass loan = (LoanClass)fromClient.readObject();
                    toClient.writeDouble(loan.getMonthlyPayment());
                    toClient.writeDouble(loan.getTotalPayment());
                    toClient.flush();

                    Platform.runLater(() -> {
                        ta.appendText("Monthly Payment: " + loan.getMonthlyPayment() + "\n" +
                                "Total Payment: " + loan.getTotalPayment() + "\n");
                    });
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
