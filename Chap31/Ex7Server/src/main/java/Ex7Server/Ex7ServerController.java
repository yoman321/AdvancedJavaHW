package Ex7Server;

import javafx.application.Platform;
import javafx.fxml.FXML;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javafx.scene.control.TextArea;

public class Ex7ServerController {

    @FXML private TextArea ta;
    private File file;
    private FileInputStream fis;
    private FileOutputStream fos;
    private ObjectInputStream fromClient;
    private ObjectOutputStream toClient;
    private int clientNo;

    public void initialize() {
        new Thread(() -> {
            try {
                ServerSocket ss = new ServerSocket(8000);
                Platform.runLater(() -> ta.appendText("Server has been created on the "+new Date()));

                while (true){
                    Socket socket = ss.accept();
                    clientNo++;
                    Platform.runLater(() -> ta.appendText("Client "+clientNo+"has been connected to the server on the "+new Date()));
                    new Thread(new HandleClient(socket, clientNo)).start();
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
            finally{
                try{
                    fis.close();
                    fos.close();
                    fromClient.close();
                    toClient.close();
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }

        }).start();

    }
    public class HandleClient implements Runnable{

        private Socket socket;
        private int clientNo;
        private int[] array

        public HandleClient(Socket socket, int clientNo){
            this.socket = socket;
            this.clientNo = clientNo;
        }
        @Override
        public void run(){
            try {
                file = new File("primes.dat");
                fis = new FileInputStream(file);
                fos = new FileOutputStream(file, true);

                while (true){
                    if (file.length() != 0){

                    }
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
