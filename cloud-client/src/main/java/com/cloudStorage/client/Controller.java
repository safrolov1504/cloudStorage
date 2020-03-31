package com.cloudStorage.client;

import com.cloudStorage.client.communication.MyClientServer;
import com.cloudStorage.client.controllers.CreatAlert;
import com.cloudStorage.client.controllers.WorkWithTables;
import com.cloudStorage.client.workingWithMessage.GetMessage;
import com.cloudStorage.client.workingWithMessage.SendMessage;
import com.cloudStorage.common.data.FileForTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    //Login window
    public TextField textField_login;
    public PasswordField testField_pass;


    //work window
    //client
    public ProgressBar pb_client;
    public TableView<FileForTable> table_client;
    public TableColumn<FileForTable, String> table_clientName;
    public TableColumn<FileForTable,String> table_clientSize;
    public TableColumn<FileForTable,String> table_clientDate;
//    public ObservableList<FileForTable> fileDataClient = FXCollections.observableArrayList();


    //server
    public TableView<FileForTable> table_service;
    public TableColumn<FileForTable,String> table_serverName;
    public TableColumn<FileForTable,String> table_serverSize;
    public TableColumn<FileForTable,String> table_serverDate;
    //public ProgressBar pb_server;
//    public ObservableList<FileForTable> fileDataService = FXCollections.observableArrayList();

    private MyClientServer messageService;
    private SendMessage sendMessage;
    private WorkWithTables workWithTables;
    private GetMessage getMessage;

    public void shutdown() {
        //System.exit(0);
    }

    public WorkWithTables getWorkWithTables() {
        return workWithTables;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            this.messageService = App.getMessageService();
            this.sendMessage = new SendMessage(this.messageService.getNetwork());
            App.getMessageService().getNetwork().getGetMessage().setController(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(App.isFlag()){
            //creatTables();
            workWithTables = new WorkWithTables(this,sendMessage);
            workWithTables.updateTableClient();
            workWithTables.updateTableService();
        }
    }

    //button login
    @FXML
    public void login_buttonSignIn(ActionEvent actionEvent) {
        sendMessage.sendSighIn(textField_login.getText(), testField_pass.getText());
    }

    //button clients
    @FXML
    public void button_sendToService(ActionEvent actionEvent) {
        File file;

        FileForTable selectedFile = table_client.getSelectionModel().getSelectedItem();
        System.out.println(selectedFile);
        if(selectedFile !=null){
            file = new File("cloud-client/storage/"+selectedFile.nameFileTable);
            try {
                sendMessage.sendFileToServer(file);
                //workWithTables.updateTableService();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void button_delete(ActionEvent actionEvent) {
        File file;
        FileForTable selectedFile = table_client.getSelectionModel().getSelectedItem();
        System.out.println(selectedFile);
        if(selectedFile !=null) {
            file = new File("cloud-client/storage/" + selectedFile.nameFileTable);
            file.delete();
        }
        workWithTables.updateTableClient();
        CreatAlert.setAlert(Alert.AlertType.INFORMATION,"File on client", "File was deleted");
    }

    @FXML
    public void button_edit(ActionEvent actionEvent) {
    }

    //button server
    @FXML
    public void button_sendToClient(ActionEvent actionEvent) {
        FileForTable selectedFile = table_service.getSelectionModel().getSelectedItem();
        System.out.println(selectedFile);
        if(selectedFile !=null) {
            sendMessage.getFileFromService(selectedFile.nameFileTable);
        }
    }



    public void button_delService(ActionEvent actionEvent) {
        FileForTable fileForTable = table_service.getSelectionModel().getSelectedItem();
        System.out.println("Server del file "+ fileForTable);
        if(fileForTable != null){
            sendMessage.sendDelFileFromServer(fileForTable.nameFileTable);
        }
    }

    public void button_EditService(ActionEvent actionEvent) {
    }

    @FXML
    public void button_exit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
