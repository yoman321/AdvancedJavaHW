package Ex6Server;

import Address.Address;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Semaphore;

import Address.Address;

public class Ex6ServerController {

    @FXML private TextArea ta;

    private int clientCount = 0;
    private Semaphore lock = new Semaphore(2);

    public void initialize(){
        new Thread(() -> {
            try{
                ServerSocket ss = new ServerSocket(8000);
                Platform.runLater(() -> ta.appendText("Server as been created at "+new Date()+"\n"));

                while (true){
                    Socket client = ss.accept();
                    Platform.runLater(() -> {
                        ta.appendText("Client 1 has been connected \n");
                        ta.appendText("Client 1 IP Address is "+client.getInetAddress().getHostAddress()+"\n");
                    });
                    new DataOutputStream(client.getOutputStream()).writeInt(clientCount+1);

                    new Thread(() -> new HandleClient(client, clientCount)).start();
                    clientCount++;
                }

            }
            catch (IOException ex){
                System.err.println(ex.toString());
            }
        }).start();
    }
    private class HandleClient implements Runnable{

        private Socket client;
        private int clientNo;

        public HandleClient (Socket client, int clientNo){
            this.client = client;
            this.clientNo = clientNo;
        }
        @Override
        public void run() {
            try {
                lock.acquire();

                ObjectOutputStream toFile = new ObjectOutputStream(new FileOutputStream("file.dat"));
                ObjectInputStream fromFile = new ObjectInputStream(new FileInputStream("file.dat"));
                ObjectInputStream objectFromClient = new ObjectInputStream(client.getInputStream());
                ObjectOutputStream objectToClient = new ObjectOutputStream(client.getOutputStream());
                DataInputStream taskFromClient = new DataInputStream(client.getInputStream());

                ArrayList<Address> addresses = new ArrayList<>();

                while (true){
                    Object object = null;
                    while ((object = fromFile.readObject()) != null){
                        if (object instanceof Address){
                            addresses.add((Address) object);
                        }
                    }

                    String task = taskFromClient.readUTF();

                    if (task.equals("add")){
                        Object address = objectFromClient.readObject();
                        toFile.writeObject(address);
                        ta.appendText("A new address has been saved\n");
                    }
                    if (task.equals("first")){
                        objectToClient.writeObject(addresses.get(0));
                        ta.appendText("Retrieved and sent first address to Client "+clientNo+"\n");
                    }
                    if (task.equals("last")){
                        objectToClient.writeObject(addresses.get(addresses.size()-1));
                        ta.appendText("Retrieved and sent last address to Client "+clientNo+"\n");
                    }
                    if (task.equals("previous")){
                        Address address = (Address)objectFromClient.readObject();
                        int index = addresses.indexOf(address);
                        if (index != 0){
                            objectToClient.writeObject(addresses.get(index-1));
                            ta.appendText("Retrieved and sent address at index "+(index-1)+" to Client "+clientNo+"\n");
                        }
                    }
                    if (task.equals("next")){
                        Address address = (Address)objectFromClient.readObject();
                        int index = addresses.indexOf(address);
                        if (index != addresses.size()-1){
                            objectToClient.writeObject(addresses.get(index+1));
                            ta.appendText("Retrived and send address at index "+(index+1)+" to Client "+clientNo+"\n");
                        }
                    }
                }
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
            catch (ClassNotFoundException ex){
                ex.printStackTrace();
            }
            catch (InterruptedException ex){
                ex.printStackTrace();
            }
            finally{
                lock.release();
            }
        }
    }
}
