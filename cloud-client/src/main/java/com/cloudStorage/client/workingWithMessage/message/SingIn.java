package com.cloudStorage.client.workingWithMessage.message;


import com.cloudStorage.client.App;
import com.cloudStorage.client.Controller;
import com.cloudStorage.client.controllers.ChangeStage;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class SingIn {
    private Controller controller;
    private byte arrayByte;
    private Alert alert = new Alert(Alert.AlertType.WARNING);
    private String userName;

    public SingIn(Controller controller, byte innerByte) {
        this.controller = controller;
        this.arrayByte = innerByte;
    }

    public void checkUser() {

        if(arrayByte == -126){
            userName = controller.textField_login.getText();
            App.setFlag(true);
            System.out.println("Checking " + userName + " is ok");
            ChangeStage.changeStageDo((Stage) controller.testField_pass.getScene().getWindow(),
                    "/com.cloud.client/workInterface.fxml","Working window "+ controller.textField_login.getText());
//            controller.setClient(userName);


        } else if(arrayByte == -125){
            System.out.println("Cheking " + userName + " is not ok");
            alert.setHeaderText("Authentication is failed");
            alert.setContentText("Wrong user or password");
            alert.showAndWait();
        }
    }

}
