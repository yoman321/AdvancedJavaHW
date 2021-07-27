package Ex10Client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Ex10ClientController {

    @FXML private TextField tfName;
    @FXML private TextField tfText;
    @FXML private TextArea ta;

    private DataInputStream fromServer;
    private DataOutputStream toServer;

    public void initialize(){
        try{
            Socket socket = new Socket("localhost",8000);
            toServer = new DataOutputStream(socket.getOutputStream());

            tfText.setOnKeyPressed(e -> {
                try {
                    if (e.getCode() == KeyCode.ENTER) {
                        ta.appendText(tfName.getText() + ": " + tfText.getText() + "\n");
                        toServer.writeUTF(tfName.getText() + ": " + tfText.getText() + "\n");
                        toServer.flush();
                    }
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            });
            new Thread(new HandleServer(socket)).start();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private class HandleServer implements Runnable{

        private Socket socket;

        public HandleServer(Socket socket){
            this.socket = socket;
        }
        @Override
        public void run(){
            try{
                fromServer = new DataInputStream(socket.getInputStream());

                while (true){
                    String message = fromServer.readUTF();
                    Platform.runLater(() -> ta.appendText(message));
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
