package com.cloudStorage.client;

import com.cloudStorage.client.communication.MyClientServer;
import com.cloudStorage.client.controllers.ChangeStage;
import com.cloudStorage.client.workingWithMessage.GetMessage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static MyClientServer messageService;
    private static GetMessage getMessage;
    private static boolean flag;

    public static MyClientServer getMessageService() {
        return messageService;
    }

    public static GetMessage getGetMessage() {
        return getMessage;
    }
    public static void setGetMessage(GetMessage getMessage) {
        App.getMessage = getMessage;
    }

    public static boolean isFlag() {
        return flag;
    }
    public static void setFlag(boolean flag) {
        App.flag = flag;
    }

    @Override
    public void start(Stage stage) throws IOException {
        flag = false;
        messageService = new MyClientServer();
        ChangeStage.changeStageDo(stage, "/com.cloud.client/loginInterface.fxml","Welcome PC");
    }


    public static void main(String[] args) {
        launch();
    }

}