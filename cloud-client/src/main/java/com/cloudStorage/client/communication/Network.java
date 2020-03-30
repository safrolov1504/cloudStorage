package com.cloudStorage.client.communication;

import com.cloudStorage.client.Controller;
import com.cloudStorage.common.data.CreatCommand;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Network {
    private final String serverAddress;
    private final int port;
    private final MyClientServer myClientServer;
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Scanner scannerIn;
    private Controller controller;

    public DataOutputStream getOutputStream() {
        return outputStream;
    }
    public DataInputStream getInputStream() {
        return inputStream;
    }

    public Network(String serverAddress, int port, MyClientServer myClientServer) {
        this.serverAddress = serverAddress;
        this.port = port;
        this.myClientServer = myClientServer;

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
                    if(inByte == CreatCommand.getSendListFileFromService() || inByte == CreatCommand.getGetFileOk()
                            || inByte == CreatCommand.getGetFileNOk()){
                        byte command = inByte;
                        if(inByte == CreatCommand.getGetFileNOk()){
                            //error
                        } else {
                            getSthFromService(command);
                        }
                    } else {
                        //System.out.println(inByte);
                        byte finalInByte = inByte;
                        Platform.runLater(() -> myClientServer.processRetrievedMessage(finalInByte));
                    }
                } catch (IOException e){
                    System.exit(0);
                }
            }
        }).start();
    }

    public void getSthFromService(byte command) throws IOException {
        byte inByte;
        System.out.println("Get sth from server");
        ArrayList<Byte> bytes = new ArrayList<>();
        int time=0;

        //waiting for size of string (name or list from service)
        int length = inputStream.readInt();
//        byte [] byteLength = new byte[4];
//        while (time !=4){
//            inByte = inputStream.readByte();
//            byteLength[time] = inByte;
//            time++;
//        }
        time = length;
//        time = ByteBuffer.wrap(byteLength).getInt();
        //bytes.remove(0);
        //System.out.println(Arrays.toString(bytes.toArray())+" "+time);

        while (time > 0){
            inByte = inputStream.readByte();
            bytes.add(inByte);
            //System.out.println("here "+ inByte);
            time--;
        }

        if(command == CreatCommand.getSendListFileFromService()){
            //case when we're waiting for list of Files on the service
            System.out.println("start to get file list from the server");
            myClientServer.getListFile(bytes);
        } else if(command == CreatCommand.getGetFileOk()){
            //case when we're waiting for file from service
            System.out.println("start to get file from the server");
            myClientServer.getFileFromService(bytes,inputStream);
        }
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
