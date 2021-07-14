package Ex4Server;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

import java.io.IOException;
import java.net.ServerSocket;
import static java.lang.System.out;

public class Ex4ServerController {

    @FXML private TextArea ta;
    private int clientNo = 0;

    public void initialize(){

        new Thread(() -> {
            try {
                RandomAccessFile file = new RandomAccessFile("Ex4Server.dat", "rw");
                ServerSocket serverSocket = new ServerSocket(8000);
                ta.appendText("Connected to multi thread server at "+new Date()+"\n");

                while (true){
                    Socket socket = serverSocket.accept();
                    out.println(file.getFilePointer());//test
                    out.println(file.length());//test;
                    if (file.length() != 0){
                        file.seek(0);
                        clientNo = file.readInt();
                    }
                    clientNo++;
                    file.seek(0);
                    file.writeInt(clientNo);

                    InetAddress inet = socket.getInetAddress();
                    Platform.runLater(() -> {
                       ta.appendText("Starting thread for Client "+clientNo+"'s at "+new Date()+" with these info:\n" +
                               "Host Name: "+inet.getHostName()+"\n" +
                               "Ip Address: "+inet.getHostAddress()+"\n");
                    });
                    new Thread(new HandleClient(socket)).start();
                }
            }
            catch (IOException ex){
                System.err.println(ex);
            }
        }).start();
    }
    public class HandleClient implements Runnable{

        private Socket socket;

        public HandleClient(Socket socket){
            this.socket = socket;
        }
        @Override
        public void run(){
            try{
                    DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());

                    toClient.writeInt(clientNo);
                    toClient.flush();

            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }
}
