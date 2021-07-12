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

    @FXML private TextArea ta;

    public void initialize(){
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(8000);
                Platform.runLater(() -> ta.appendText("Server started at " + new Date()));

                Socket socket = serverSocket.accept();

                DataInputStream fromClient = new DataInputStream(socket.getInputStream());
                DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());

                while (true){
                    ta.appendText("Client connected at "+new Date()+"\n");
                    double weight = fromClient.readDouble();
                    double height = fromClient.readDouble();
                    double bmi = (weight * 0.45359237) / Math.pow(height * 0.0254, 2);
                    String category = "";

                    if (bmi < 18.5){
                        category = "Underweight";
                    }
                    else if (bmi >= 18.5 && bmi < 25){
                        category = "Normal";
                    }
                    else if (bmi >= 25 && bmi < 30){
                        category = "Overweight";
                    }
                    else{
                        category = "Obese";
                    }

                    toClient.writeDouble(bmi);
                    toClient.writeUTF(category);

                    final String finalCategory = category;
                    Platform.runLater(() -> {
                        ta.appendText("Client connect to the server at "+new Date()+"\n");
                        ta.appendText("The weight is " + weight + "\n");
                        ta.appendText("The height is " + height + "\n");
                        ta.appendText("The BMI is "+bmi+". "+finalCategory+"\n");
                    });
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }).start();
    }

}
