package Ex4Client;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Ex4ClientController {

    @FXML private Text text;

    private DataInputStream fromServer;

    public void initialize(){
        try{
            Socket socket = new Socket("localhost", 8000);
            fromServer = new DataInputStream(socket.getInputStream());

            text.setText("You are the "+fromServer.readInt()+"'s visitor.");
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
