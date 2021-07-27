package Ex10Server;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import java.net.ServerSocket;

public class Ex10ServerController {

    @FXML private TextArea ta;

    private DataInputStream fromClient;
    private DataOutputStream toClient;
    private ArrayList<Socket> clients;
    private int clientNo = 0;

    public void initialize(){
        clients = new ArrayList<>();
        new Thread(() -> {
            try {
                ServerSocket ss = new ServerSocket(8000);
                Platform.runLater(() -> ta.appendText("Server for chatting has been made on the "+new Date()+"\n"));

                while (true){
                    Socket socket = ss.accept();
                    clients.add(socket);
                    Platform.runLater(() -> ta.appendText("Connection from socket with address"+socket.getInetAddress().getHostAddress()+" on the "+new Date()+"\n"));
                    new Thread(new HandleClient(socket, clientNo)).start();
                    clientNo++;
                }
            }
            catch (Exception ex){
                ta.appendText(ex.toString()+"\n");
            }
            finally{
                try {
                    fromClient.close();
                    toClient.close();
                }
                catch (IOException ex){
                    ex.printStackTrace();
                }
            }

        }).start();
    }
    private class HandleClient implements Runnable{

        private Socket socket;
        private int clientNo;

        public HandleClient(Socket socket, int clientNo){
            this.socket = socket;
            this.clientNo = clientNo;
        }
        @Override
        public void run(){
            try{
                fromClient = new DataInputStream(socket.getInputStream());

                while (true){
                    String message = fromClient.readUTF();
                    for (int i=0; i<clients.size(); i++){
                        if (i != clientNo) {
                            toClient = new DataOutputStream(clients.get(i).getOutputStream());
                            toClient.writeUTF(message);
                            toClient.close();
                        }
                    }
                }
            }
            catch (IOException ex){
                ta.appendText("Client "+clientNo+" has disconnected\n");
            }
        }
    }
}
