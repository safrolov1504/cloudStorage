package com.cloudStorage.client.communication;

import com.cloudStorage.client.Controller;
import com.cloudStorage.client.workingWithMessage.GetMessage;
import com.cloudStorage.common.data.CreatCommand;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import java.util.Scanner;

public class Network {
    private final String serverAddress;
    private final int port;
    private final MyClientServer myClientServer;
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Scanner scannerIn;
    private GetMessage getMessage;

    public GetMessage getGetMessage() {
        return getMessage;
    }

    public Network(String serverAddress, int port, MyClientServer myClientServer) {
        this.serverAddress = serverAddress;
        this.port = port;
        this.myClientServer = myClientServer;
        this.getMessage = new GetMessage();

        try {
            //it's first connection or not
            initNetworkState(serverAddress, port);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Connection is failed");
            alert.setContentText("Нет подключения в серверу");
            alert.showAndWait();
        }
    }

    //creat connection
    private void initNetworkState(String serverAddress, int port) throws IOException {
        this.socket = new Socket(serverAddress,port);
        this.outputStream = new DataOutputStream(socket.getOutputStream());
        this.inputStream = new DataInputStream(socket.getInputStream());
        this.scannerIn = new Scanner(socket.getInputStream());

        //подключили и ждем сообщений
        getMessage();
    }

    public void getMessage(){
        new Thread(() ->{
            while (true){
                //waiting for message
                try {
                    byte inByte = inputStream.readByte();
                    sortInnerMessage(inByte);
                } catch (IOException e){
                    System.exit(0);
                }
            }
        }).start();
    }

    public void sortInnerMessage(byte innerByte) throws IOException {
        if (innerByte == CreatCommand.getCommandAuthOk() || innerByte == CreatCommand.getCommandAuthNok()) {
            //info from server about auth
            System.out.println("Client: Get info from server. Result of authentication");
            Platform.runLater(() -> getMessage.infoAuthIn(innerByte));
        } else if (innerByte == CreatCommand.getSendFileOk() || innerByte == CreatCommand.getSendFileNOk()) {
            //info about sending file to the server
            System.out.println("Client: Get info from server. Result of sending file to the server");
            Platform.runLater(() -> getMessage.infoSendFileToServer(innerByte));
        } else if (innerByte == CreatCommand.getDelFileFromServerOk() || innerByte == CreatCommand.getDelFileFromServerNOk()) {
            //info about deleting file on the server
            System.out.println("Client: get info from server about deleting file on the server");
            Platform.runLater(() -> getMessage.infoDelFileOnServer(innerByte));
        } else if (innerByte == CreatCommand.getSendListFileFromService()) {
            //get list of file from server
            System.out.println("Client: get info from server. List of files on the server is getting");
            byte[] listFile = getStringFromServer();
            getMessage.getListFile(listFile);
        } else if (innerByte == CreatCommand.getGetFileOk() || innerByte == CreatCommand.getGetFileNOk()) {
            //get file from server
            System.out.println("Client: get info from server. File from the server is getting");
            byte[] nameFile = getStringFromServer();
            getMessage.getFileFromService(nameFile, inputStream);
        } else {
            System.out.println("Unexpected value: " + innerByte);
        }
    }

    public byte[] getStringFromServer() throws IOException {
        byte [] byteIn;
        //waiting for size of string (name or list from service)
        int length = inputStream.readInt();
        byteIn = new byte[length];
        inputStream.read(byteIn);
        return byteIn;
    }

    public void sendInt(int intIn) {
        try {
            outputStream.writeInt(intIn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendLong(long longIn){
        try {
            outputStream.writeLong(longIn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendByte(byte byteIn){
        try {
            outputStream.writeByte(byteIn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendMessage(byte[] outByte) {
        try {
            outputStream.write(outByte);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
