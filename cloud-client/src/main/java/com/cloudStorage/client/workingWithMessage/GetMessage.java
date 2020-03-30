package com.cloudStorage.client.workingWithMessage;

import com.cloudStorage.client.Controller;
import com.cloudStorage.client.FileForTable;
import com.cloudStorage.client.workingWithMessage.message.SingIn;
import com.cloudStorage.client.workingWithMessage.message.WorkFile;
import com.cloudStorage.common.data.CreatCommand;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GetMessage {

    private Controller controller;
    public ObservableList<FileForTable> fileDataService = FXCollections.observableArrayList();
    private WorkFile workFile;


    public GetMessage() {
        this.workFile = new WorkFile();
    }

    public void setController(Controller controller) {
        this.controller = controller;
        workFile.setController(controller);
    }

    public void getListFile(byte[] bytesIn){
        //System.out.println(Arrays.toString(bytesIn));
        String str = new String(bytesIn);
        //System.out.println(str);

        fileDataService.clear();

        String [] subString = str.split("<END>");
        for (String strFile:subString) {
            fileDataService.add(new FileForTable(strFile));
        }
        System.out.println("end to get list file from server");
        controller.getWorkWithTables().addInfoTableService(fileDataService);
    }

    public void workingWithInnerMessage(byte innerByte) {
        if(innerByte == CreatCommand.getCommandAuthNok() || innerByte == CreatCommand.getCommandAuthOk()){
            (new SingIn(controller,innerByte)).checkUser();
        }

        if(innerByte == CreatCommand.getSendFileOk() || innerByte == CreatCommand.getSendFileNOk()){
            workFile.getFileReady(innerByte);
        }

        if(innerByte == CreatCommand.getDelFileFromServerOk() || innerByte == CreatCommand.getDelFileFromServerNOk()){
            workFile.delFileReady(innerByte);
        }
    }

    public void getFileFromService(byte[] nameFileByte, DataInputStream inputStream) throws IOException {
        //get name of file
        String nameFile = new String(nameFileByte);

        //get length of file
        long lengthFile = inputStream.readLong();
        //controller.pb_server.
        System.out.println("Start to get file from service "+ nameFile +" size: "+lengthFile);

        File file = new File("cloud-client/storage/"+nameFile);

        if(file.exists()){

        } else {
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
        }


    }
}
