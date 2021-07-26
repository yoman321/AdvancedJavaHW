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

    private ObjectInputStream objectFromClient;
    private ObjectOutputStream objectToClient;
    private DataInputStream taskFromClient;
    private ObjectOutputStream toFile;
    private ObjectInputStream fromFile;
    private ArrayList<Address> addresses = new ArrayList<>();

    private int clientNo = 0;
    private Semaphore lock = new Semaphore(2);

    public void initialize(){
        new Thread(() -> {
            try{
                ServerSocket ss = new ServerSocket(8000);
                Platform.runLater(() -> ta.appendText("Server as been created at "+new Date()+"\n"));

                while (true){
                    Socket client = ss.accept();
                    clientNo++;
                    Platform.runLater(() -> {
                        ta.appendText("Client "+clientNo+" has been connected \n");
                        ta.appendText("Client IP Address is "+client.getInetAddress().getHostAddress()+"\n");
                    });
                    System.out.println("socket accept");//test
                    new Thread(new HandleClient(client)).start();
                }

            }
            catch (IOException ex){
                System.err.println(ex.toString());
            }
            finally{
                try {
                    objectFromClient.close();
                    objectToClient.close();
                    toFile.close();
                    fromFile.close();
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }).start();
    }
    private class HandleClient implements Runnable{

        private Socket client;

        public HandleClient (Socket client){
            this.client = client;
        }
        @Override
        public void run() {
            try {
                System.out.println("s");//test
                lock.acquire();
                System.out.println("W");//test

                File file = new File("file.dat");
                FileOutputStream fos = new FileOutputStream(file, true);
                FileInputStream fis = new FileInputStream(file);
                if (file.length() != 0){ //Create object input stream to file if ever the file is not empty
                    fromFile = new ObjectInputStream(fis);
                }
//                fromFile = new ObjectInputStream(fis);
                System.out.println("nothing");//test

                ArrayList<Address> addresses = new ArrayList<>();



                while (true) {
                    if (file.length() != 0){
                        boolean hasRead = true;
                        while (hasRead) {
                            if (fis.available() != 0) {
                                Object address = fromFile.readObject();
                                addresses.add((Address) address);
                            } else {
                                hasRead = false;
                            }
                        }
                    }
//                    object = fromFile.readObject();
//                    Address address1 = (Address)object;
//                    System.out.println(address1.getName());//test
//                    System.out.println(fromFile.available());//test
//                    System.out.println(addresses.size());//test
                    taskFromClient = new DataInputStream(client.getInputStream());
                    String task = taskFromClient.readUTF();
//                    System.out.println(task+" something");//test

                    if (task.equals("Add")){
                        System.out.println("in add");//test
                        objectFromClient = new ObjectInputStream(client.getInputStream());
                        Address address = (Address)objectFromClient.readObject();
                        System.out.println(address.getName());//test
                        if (file.length() < 1){
                            toFile = new ObjectOutputStream(fos);
                            toFile.writeObject(address);
                            fromFile = new ObjectInputStream(fis); //Create object input stream to file after first write to file
                        }
                        else if (file.length() >= 1){
                            NewObjectOutputStream newToFile = new NewObjectOutputStream(fos);
                            newToFile.writeObject(address);
                        }
                        ta.appendText("A new address has been saved from Client"+clientNo+"\n");
                    }
                    if (task.equals("First")){
                        objectToClient = new ObjectOutputStream(client.getOutputStream());
                        objectToClient.writeObject(addresses.get(0));
                        ta.appendText("Retrieved and sent first address to Client "+clientNo+"\n");
                    }
                    if (task.equals("Last")){
                        objectToClient = new ObjectOutputStream(client.getOutputStream());
                        objectToClient.writeObject(addresses.get(addresses.size()-1));
                        ta.appendText("Retrieved and sent last address to Client "+clientNo+"\n");
                    }
                    if (task.equals("Previous")){
                        objectFromClient = new ObjectInputStream(client.getInputStream());

                        Address address = (Address)objectFromClient.readObject();
                        int index = 0;

                        for (int i=0; i<addresses.size(); i++){
                            if (addresses.get(i).getName().equals(address.getName())){
                                index = i;
                            }
                        }
                        System.out.println(address.getName());//test
                        System.out.println("index "+index);//test
                        if (index > 0){
                            objectToClient = new ObjectOutputStream(client.getOutputStream());
                            objectToClient.writeObject(addresses.get(index-1));
                            ta.appendText("Retrieved and sent address at index "+(index-1)+" to Client "+clientNo+"\n");
                        }
                    }
                    if (task.equals("Next")){
                        objectFromClient = new ObjectInputStream(client.getInputStream());

                        Address address = (Address)objectFromClient.readObject();
                        int index = 0;

                        for (int i=0; i<addresses.size(); i++){
                            if (addresses.get(i).getName().equals(address.getName())){
                                index = i;
                            }
                        }
                        if (index < addresses.size()){
                            objectToClient = new ObjectOutputStream(client.getOutputStream());
                            objectToClient.writeObject(addresses.get(index+1));
                            ta.appendText("Retrieved and send address at index "+(index+1)+" to Client "+clientNo+"\n");
                        }
                    }
                }
            }
            catch (EOFException ex){
                ex.printStackTrace();
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
    private static class NewObjectOutputStream extends ObjectOutputStream{
        public NewObjectOutputStream(OutputStream out) throws IOException{
            super(out);
        }
        public NewObjectOutputStream() throws Exception{
            super();
        }
        @Override
        public void writeStreamHeader() throws IOException{
            return;
        }

    }

}
