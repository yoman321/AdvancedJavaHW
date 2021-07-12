package Ex2Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.application.Platform;

public class Ex2ClientController {

    DataInputStream fromServer;
    DataOutputStream toServer;

    @FXML TextArea ta;
    @FXML TextField weight;
    @FXML TextField height;

    public void initialize(){
        try {
            Socket socket = new Socket("localhost", 8000);
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
        }
        catch(Exception ex){
            ta.appendText(ex.toString());
        }
    }
    public void onclickSubmit(){
        try {
            toServer.writeDouble(Double.valueOf(weight.getText()));
            toServer.writeDouble(Double.valueOf(height.getText()));

            double bmi = fromServer.readDouble();

            Platform.runLater(() -> {
                ta.appendText("The weight in pounds is "+weight+"\n");
                ta.appendText("The height in meters is "+height+"\n");
                ta.appendText("The BMI is "+bmi+"\n");
            });
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }
}
