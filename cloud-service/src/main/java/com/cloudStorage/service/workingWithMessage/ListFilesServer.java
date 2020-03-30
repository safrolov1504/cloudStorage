package com.cloudStorage.service.workingWithMessage;

import com.cloudStorage.service.connection.FileForTable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;

public class ListFilesServer {
    public static byte[] creatFileList(String userName) {
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
        int length = string.length();

        byte [] byteLength = intToByteArray(length);
        byte [] byteOut = Arrays.copyOf(byteLength,byteLength.length+string.length());

        System.arraycopy(string.getBytes(),0,byteOut,byteLength.length,string.getBytes().length);
//        System.out.println(length+" "+string);
//        System.out.println(Arrays.toString(byteOut));
        //System.out.println(Arrays.toString(byteLength)+" " +ByteBuffer.wrap(byteLength).getInt());
        return byteOut;
        //return sb.toString().getBytes();
    }

    public static final byte[] intToByteArray(int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
    }
}
