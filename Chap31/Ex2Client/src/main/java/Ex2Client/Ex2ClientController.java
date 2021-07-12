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

    @FXML private TextArea ta;
    @FXML private TextField weight;
    @FXML private TextField height;

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
            toServer.flush();

            double bmi = fromServer.readDouble();
            String category = fromServer.readUTF();

            Platform.runLater(() -> {
                ta.appendText("The weight in pounds is "+weight.getText()+"\n");
                ta.appendText("The height in meters is "+height.getText()+"\n");
                ta.appendText("The BMI is "+bmi+". "+category+"\n");
            });
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }
}
