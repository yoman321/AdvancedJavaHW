package Ex6Client;

import Address.Address;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.io.*;
import java.net.Socket;

public class Ex6ClientController{

    @FXML TextField tfName;
    @FXML TextField tfStreet;
    @FXML TextField tfCity;
    @FXML TextField tfState;
    @FXML TextField tfZip;

    private ObjectInputStream objectFromServer = null;
    private DataInputStream fromServer;
    private ObjectOutputStream objectToServer = null;
    private DataOutputStream dataToServer = null;
    private Socket socket;

    public void initialize() throws IOException {
        try {
            socket = new Socket("localhost", 8000);
//            fromServer = new DataInputStream(socket.getInputStream());

        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
    public void onclickBtn(ActionEvent e){
        try {
            Button btn = (Button) e.getSource();

            if (btn.getText().equals("Add") || btn.getText().equals("Previous") || btn.getText().equals("Next")) {
                Address address = new Address(tfName.getText(), tfStreet.getText(), tfCity.getText(), tfState.getText(), tfZip.getText());
                System.out.println("something");//test
                System.out.println(btn.getText());
                dataToServer = new DataOutputStream(socket.getOutputStream());
                dataToServer.writeUTF(btn.getText());
                objectToServer = new ObjectOutputStream(socket.getOutputStream());
                objectToServer.writeObject(address);
                dataToServer.flush();
                objectToServer.flush();
            }
            else if (btn.getText().equals("First") || btn.getText().equals("Last")){
                dataToServer.writeUTF(btn.getText());
                dataToServer.flush();
            }

            if (btn.getText().equals("Previous") || btn.getText().equals("Next") || btn.getText().equals("First") || btn.getText().equals("Last")){
                objectFromServer = new ObjectInputStream(socket.getInputStream());
                Address addressFromServer = (Address)objectFromServer.readObject();
                Platform.runLater(() -> {
                    tfName.setText(addressFromServer.getName());
                    tfStreet.setText(addressFromServer.getStreet());
                    tfCity.setText(addressFromServer.getCity());
                    tfState.setText(addressFromServer.getState());
                    tfZip.setText(addressFromServer.getZip());
                });
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally{
            try{
//                objectToServer.close();
//                objectFromServer.close();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

}
