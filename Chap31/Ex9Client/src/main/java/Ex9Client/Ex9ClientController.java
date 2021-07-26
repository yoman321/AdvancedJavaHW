package Ex9Client;

import javafx.fxml.FXML;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;

public class Ex9ClientController {

     @FXML private TextArea taServer;
     @FXML private TextArea taClient;

     private DataInputStream fromServer;
     private DataOutputStream toServer;

     public void initialize(){
        try{
            Socket socket = new Socket("localhost", 8000);
            toServer = new DataOutputStream(socket.getOutputStream());

            taClient.setOnKeyPressed(e -> {
                try {
                    if (e.getCode() == KeyCode.ENTER) {
                        String[] fullText = taClient.getText().split("\n");
                        toServer.writeUTF(fullText[fullText.length-1]);
                        System.out.println(fullText[fullText.length-1]); //test
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
    public class HandleServer implements Runnable{

         private Socket socket;

         public HandleServer(Socket socket){
             this.socket = socket;
         }
         @Override
        public void run(){
             try {
                 fromServer = new DataInputStream(socket.getInputStream());
                 while (true) {
                     String textFromServer = fromServer.readUTF();
                     taServer.appendText(textFromServer + "\n");
                 }
             }
             catch (Exception ex){
                 ex.printStackTrace();
             }
         }
    }
}
