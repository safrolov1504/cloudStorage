package com.cloudStorage.client.controllers;

import javafx.scene.control.Alert;

public class CreatAlert {

    public static void setAlert(Alert.AlertType type, String headText, String contentText){
        Alert alert = new Alert(type);
        alert.setHeaderText(headText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
