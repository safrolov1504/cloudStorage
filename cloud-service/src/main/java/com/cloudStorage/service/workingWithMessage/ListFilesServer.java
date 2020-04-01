package com.cloudStorage.service.workingWithMessage;


import com.cloudStorage.common.data.FileForTable;
import com.cloudStorage.service.connection.handlers.SendBack;
import io.netty.channel.ChannelHandlerContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;

public class ListFilesServer {
    private static int SIZE_CASH = 1024;
    private byte[] cash;
    private byte[] stringByte;
    private int sendSize;
    //private byte[]
    public ListFilesServer() {
        cash = new byte[SIZE_CASH];
    }

    public void creatFileList(ChannelHandlerContext ctx, String userName) {
        //read list of file on server to string
        File folder = new File("cloud-service/global-storage/"+userName);
        File[] arrayFile = folder.listFiles();
        BasicFileAttributes attr;
        StringBuilder sb = new StringBuilder();
        FileForTable fileForTable = new FileForTable();
        try {
            for (File f:arrayFile) {
                attr = Files.readAttributes(f.toPath(), BasicFileAttributes.class);
                fileForTable = new FileForTable(f.getName(),attr.size()+"",attr.creationTime().toString());
                //System.out.println(fileForTable.toString());
                sb.append(fileForTable.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String string = sb.toString();

        //have a string of file's list
        //start to send
        int length = string.length();

        //send length of string
        SendBack.sendBack(ctx,length);
        System.out.println(length);


        if(length<SIZE_CASH) {
            //if string < cash
            SendBack.sendBack(ctx,string.getBytes());
        } else {
            sendSize = 0;
            stringByte = string.getBytes();
            while (sendSize<length){
                if(length - sendSize < SIZE_CASH){
                    cash = new byte[length-sendSize];
                    cash = Arrays.copyOfRange(stringByte,sendSize,stringByte.length);
                    //SendBack.sendBack(ctx,cash,length-sendSize);
                } else {
                    cash = Arrays.copyOfRange(stringByte, sendSize, sendSize + SIZE_CASH);
                }
                SendBack.sendBack(ctx, cash);
                //System.out.println(Arrays.toString(cash));
                sendSize += SIZE_CASH;
            }
        }

//        byte [] byteLength = intToByteArray(length);
//        byte [] byteOut = Arrays.copyOf(byteLength,byteLength.length+string.length());

//        System.arraycopy(string.getBytes(),0,byteOut,byteLength.length,string.getBytes().length);
////        System.out.println(length+" "+string);
//        System.out.println(Arrays.toString(byteOut));
//        //System.out.println(Arrays.toString(byteLength)+" " +ByteBuffer.wrap(byteLength).getInt());
//        return byteOut;
        //return sb.toString().getBytes();
    }

//    public static final byte[] intToByteArray(int value) {
//        return new byte[] {
//                (byte)(value >>> 24),
//                (byte)(value >>> 16),
//                (byte)(value >>> 8),
//                (byte)value};
//    }
}
