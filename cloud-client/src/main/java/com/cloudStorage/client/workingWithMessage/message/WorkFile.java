package com.cloudStorage.client.workingWithMessage.message;


import com.cloudStorage.client.Controller;
import com.cloudStorage.common.data.CreatCommand;
import javafx.scene.control.Alert;


public class WorkFile {
    private Alert alert;
    private Controller controller;

    public WorkFile() {
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void getFileReady(byte innerByte) {
        if(innerByte == CreatCommand.getSendFileOk()){
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("File");
            alert.setContentText("file was send");
            controller.getWorkWithTables().updateTableService();
            alert.showAndWait();
        }   else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("File");
            alert.setContentText("file was not send");
            alert.showAndWait();
        }
    }


    public void delFileReady(byte innerByte) {
        if(innerByte == CreatCommand.getDelFileFromServerOk()){
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("File");
            alert.setContentText("file was deleted");
            controller.getWorkWithTables().updateTableService();
            alert.showAndWait();
        }   else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("File");
            alert.setContentText("file was not deleted");
            alert.showAndWait();
        }
    }
}
