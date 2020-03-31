package com.cloudStorage.client.workingWithMessage;

import com.cloudStorage.client.App;
import com.cloudStorage.client.Controller;
import com.cloudStorage.client.controllers.ChangeStage;
import com.cloudStorage.client.controllers.CreatAlert;
import com.cloudStorage.common.data.CreatCommand;
import com.cloudStorage.common.data.FileForTable;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GetMessage {

    private Controller controller;
    public ObservableList<FileForTable> fileDataService = FXCollections.observableArrayList();
    //private WorkFile workFile;
    private Alert alert;

    public GetMessage() {
        //this.workFile = new WorkFile();
    }

    public void setController(Controller controller) {
        this.controller = controller;
        //workFile.setController(controller);
    }

    public void getListFile(byte[] bytesIn){
        String str = new String(bytesIn);

        fileDataService.clear();

        String [] subString = str.split(FileForTable.getEnd());
        for (String strFile:subString) {
            fileDataService.add(new FileForTable(strFile));
        }
        System.out.println("end to get list file from server");
        controller.getWorkWithTables().addInfoTableService(fileDataService);
    }

    //get file from server
    public void getFileFromService(byte[] nameFileByte, DataInputStream inputStream) throws IOException {
        //get name of file
        String nameFile = new String(nameFileByte);

        //get length of file
        long lengthFile = inputStream.readLong();
        //controller.pb_server.
        System.out.println("Start to get file from service "+ nameFile +" size: "+lengthFile);

        File file = new File("cloud-client/storage/"+nameFile);

            //creat file
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte [] cash = new byte[1024];
            int i=0;
            while (lengthFile>0){
                cash[i] = inputStream.readByte();
                i++;
                lengthFile--;
                if(i == cash.length || lengthFile ==0){
                    fileOutputStream.write(cash);
                    //work with tail
                    if(lengthFile<cash.length && lengthFile != 0){
                        cash = new byte[(int)lengthFile];
                    }
                    i = 0;
                }
            }
            System.out.println("End to get file from service "+ nameFile +" size: "+lengthFile);
            controller.getWorkWithTables().updateTableClient();
            Platform.runLater(() ->CreatAlert.setAlert(Alert.AlertType.INFORMATION,
                    "File", "File was got"));
    }

    //get information from server about authentication
    public void infoAuthIn(byte innerByte) {
        String userName = controller.textField_login.getText();
        if(innerByte == CreatCommand.getCommandAuthOk()){
            App.setFlag(true);
            System.out.println("Client: Checking " + userName + " is ok");
            ChangeStage.changeStageDo((Stage) controller.testField_pass.getScene().getWindow(),
                    "/com.cloud.client/workInterface.fxml","Working window "+ controller.textField_login.getText());
        } else if(innerByte == CreatCommand.getCommandAuthNok()){

            System.out.println("Client: Checking " + userName + " is not ok");
            CreatAlert.setAlert(Alert.AlertType.WARNING,"Authentication is failed","Wrong user or password");
        }
    }

    //get information about sending file to the server
    public void infoSendFileToServer(byte innerByte){
        if(innerByte == CreatCommand.getSendFileOk()){
            CreatAlert.setAlert(Alert.AlertType.INFORMATION, "File", "File was send");
            controller.getWorkWithTables().updateTableService();
        }   else {
            CreatAlert.setAlert(Alert.AlertType.WARNING, "File", "File was not send");
        }
    }

    //get information about deleting file from the server
    public void infoDelFileOnServer(byte innerByte){
        //workFile.delFileReady(innerByte);
        if(innerByte == CreatCommand.getDelFileFromServerOk()){
            CreatAlert.setAlert(Alert.AlertType.INFORMATION, "File", "File was deleted");
            controller.getWorkWithTables().updateTableService();
        }   else {
            CreatAlert.setAlert(Alert.AlertType.WARNING, "File", "File was not deleted");
        }
    }


}
