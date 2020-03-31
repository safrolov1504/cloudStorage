package com.cloudStorage.client.controllers;


import com.cloudStorage.client.Controller;
import com.cloudStorage.client.workingWithMessage.SendMessage;
import com.cloudStorage.common.data.FileForTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;

public class WorkWithTables {
    private ObservableList<FileForTable> fileDataClient = FXCollections.observableArrayList();
    private ObservableList<FileForTable> fileDataService = FXCollections.observableArrayList();

    private Controller controller;
    private SendMessage sendMessage;

    public WorkWithTables(Controller controller, SendMessage sendMessage) {
        this.controller = controller;
        this.sendMessage = sendMessage;

        controller.table_clientName.setCellValueFactory(new PropertyValueFactory<FileForTable,String>("nameFileTable"));
        controller.table_clientSize.setCellValueFactory(new PropertyValueFactory<FileForTable,String>("sizeFileTable"));
        controller.table_clientDate.setCellValueFactory(new PropertyValueFactory<FileForTable,String>("dateCreatFileTable"));

        controller.table_serverName.setCellValueFactory(new PropertyValueFactory<FileForTable,String>("nameFileTable"));
        controller.table_serverSize.setCellValueFactory(new PropertyValueFactory<FileForTable,String>("sizeFileTable"));
        controller.table_serverDate.setCellValueFactory(new PropertyValueFactory<FileForTable,String>("dateCreatFileTable"));
    }


    public void updateTableClient(){
        //write files from local folder
        File folder = new File("cloud-client/storage");
        File[] arrayFile = folder.listFiles();
        BasicFileAttributes attr;

        fileDataClient.clear();

        FileForTable fileForTable;
        try {
            for (File f:arrayFile) {
                attr = Files.readAttributes(f.toPath(), BasicFileAttributes.class);
                fileForTable = new FileForTable(f.getName(),attr.size()+"",attr.creationTime().toString());
                //System.out.println(f.getName()+" "+attr.size()+" "+attr.creationTime());
                this.fileDataClient.add(fileForTable);
            }

            controller.table_client.setItems(this.fileDataClient);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateTableService(){
        sendMessage.sendRequestToGetListFileFromService();
    }

    public void addInfoTableService(ObservableList<FileForTable> fileDataService){
        this.fileDataService = fileDataService;
        controller.table_service.setItems(this.fileDataService);
    }
}
