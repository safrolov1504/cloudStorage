package com.cloudStorage.client.workingWithMessage;


import com.cloudStorage.client.communication.Network;
import com.cloudStorage.common.data.CreatCommand;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class SendMessage {
    private Network network;

    public SendMessage(Network network) {
        this.network = network;
    }

    public void sendSighIn(String login, String pass) {
        network.sendByte(CreatCommand.getCommandAuth());

        network.sendInt(login.length());
        network.sendMessage(login.getBytes());
        network.sendInt(pass.length());
        network.sendMessage(pass.getBytes());
    }


    public void sendFileToServer(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file.getPath());
        long lengthFile = file.length();
        byte[] byteArray = new byte[1024];
        int i;

        network.sendByte(CreatCommand.getSendFile());
        network.sendInt(file.getName().length());
        network.sendMessage(file.getName().getBytes());

        network.sendLong(lengthFile);

        //start working with file
        boolean flag=true;

        while (flag){
                if(lengthFile<1024){
                    byteArray = new byte[(int) lengthFile];
                    flag = false;
                }
                i = fileInputStream.read(byteArray);
                lengthFile-=i;
                network.sendMessage(byteArray);
        }

        fileInputStream.close();
    }

    public void sendRequestToGetListFileFromService() {
        System.out.println("Send command to update list "+ CreatCommand.getSendListFileFromService());
        network.sendByte(CreatCommand.getSendListFileFromService());
    }

    public void getFileFromService(String nameFile) {
        network.sendByte(CreatCommand.getGetFile());
        network.sendInt(nameFile.length());
        network.sendMessage(nameFile.getBytes());
    }

    public void sendDelFileFromServer(String nameFile) {
        network.sendByte(CreatCommand.getDelFileFromServer());
        network.sendInt(nameFile.length());
        network.sendMessage(nameFile.getBytes());
    }
}
