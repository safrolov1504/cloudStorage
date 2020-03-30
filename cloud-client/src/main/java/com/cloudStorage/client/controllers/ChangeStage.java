package com.cloudStorage.client.controllers;

import com.cloudStorage.client.Controller;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class ChangeStage {

    private static void setStage(Stage primaryStage, String resources, String nameWindow) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ChangeStage.class.getResource(resources));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle(nameWindow);
        primaryStage.setScene(scene);

        //может здесь возникнуть ошибка
        final Controller primaryController = loader.getController();
        primaryStage.setOnHidden(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                primaryController.shutdown();
            }
        });

        primaryStage.show();
    }

    public static void  changeStageDo(Stage primaryStage,String resources, String nameWindow){
        // do what you have to do
        primaryStage.close();
        Stage stage = new Stage();
        try {
            setStage(stage, resources, nameWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void  changeStageDoWithoutClose(Stage primaryStage,String resources, String nameWindow){
        // do what you have to do
        Stage stage = new Stage();
        try {
            setStage(stage, resources, nameWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
