package Ex2Server;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Ex2ServerController {

    @FXML TextArea ta;
    public void initialize(){
        new Thread(() -> {
            try {
                Platform.runLater(() -> ta.appendText("Server started at " + new Date()));

                ServerSocket serverSocket = new ServerSocket(8000);
                Socket socket = serverSocket.accept();

                DataInputStream fromClient = new DataInputStream(socket.getInputStream());
                DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());

                while (true){
                    double weight = fromClient.readDouble();
                    double height = fromClient.readDouble();
                    double bmi = (weight * 0.45359237) / Math.pow(height, 2);

                    toClient.writeDouble(bmi);

                    Platform.runLater(() -> {
                        ta.appendText("Client connect to the server at "+new Date()+"\n");
                        ta.appendText("The weight is " + weight + "\n");
                        ta.appendText("The height is " + height + "\n");
                        ta.appendText("The BMI is "+bmi+"\n");
                    });
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }).start();
    }

}
