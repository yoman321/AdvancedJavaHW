package Ex9Server;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Ex9ServerController {

    @FXML private TextArea taServer;
    @FXML private TextArea taClient;

    private DataInputStream fromClient;
    private DataOutputStream toClient;

    public void initialize(){
        new Thread(() -> {
            try {
                ServerSocket ss = new ServerSocket(8000);

                while (true) {
                    Socket socket = ss.accept();
                    new Thread(new HandleClient(socket)).start();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            finally{
                try {
                    fromClient.close();
                    toClient.close();
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }).start();
    }
    private class HandleClient implements Runnable{

        private Socket socket;

        public HandleClient(Socket socket){
            this.socket = socket;
        }
        @Override
        public void run(){
            try{
                taServer.setOnKeyPressed(e -> {
                    if (e.getCode() == KeyCode.ENTER){
                        try {
                            String[] fullText = taServer.getText().split("\n");
                            toClient = new DataOutputStream(socket.getOutputStream());
                            toClient.writeUTF(fullText[fullText.length-1]);
                            toClient.flush();
                        }
                        catch (Exception ex){
                            ex.printStackTrace();
                            taServer.appendText("Client has disconnected \n");
                        }
                    }
                });

                fromClient = new DataInputStream(socket.getInputStream());
                while (true){
                    taClient.appendText(fromClient.readUTF()+"\n");
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }

        }
    }
}
