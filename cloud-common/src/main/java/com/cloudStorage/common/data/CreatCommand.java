package com.cloudStorage.common.data;

public class CreatCommand {
    private static byte commandAuth = -126;
    private static byte commandAuthOk = commandAuth;
    private static byte commandAuthNok = -125;

    private static byte sendListFileFromServer = -124;
    private static byte sendListFileFromServerOk = sendListFileFromServer;



    private static byte sendListFileFromServiceEnd = -123;

    private static byte delFileFromServer = -122;
    private static byte delFileFromServerOk = delFileFromServer;
    private static byte delFileFromServerNOk = -121;

    private static byte getFile = -120;
    private static byte getFileOk = getFile;
    private static byte getFileNOk = -119;

    private static byte sendFile = -118;
    private static byte sendFileOk = sendFile;
    private static byte sendFileNOk = -117;

    private static byte delFile = -116;
    private static byte delFileOk = delFile;
    private static byte delFileNok = -115;

    private static byte editFile = -114;
    private static byte editFileOk = editFile;
    private static byte editFileNOk = -113;

    public static byte getCommandAuth() {
        return commandAuth;
    }

    public static byte getCommandAuthOk() {
        return commandAuthOk;
    }

    public static byte getCommandAuthNok() {
        return commandAuthNok;
    }

    public static byte getGetFile() {
        return getFile;
    }

    public static byte getGetFileOk() {
        return getFileOk;
    }

    public static byte getGetFileNOk() {
        return getFileNOk;
    }

    public static byte getSendFile() {
        return sendFile;
    }

    public static byte getSendFileOk() {
        return sendFileOk;
    }

    public static byte getSendFileNOk() {
        return sendFileNOk;
    }

    public static byte getDelFile() {
        return delFile;
    }

    public static byte getDelFileOk() {
        return delFileOk;
    }

    public static byte getDelFileNok() {
        return delFileNok;
    }

    public static byte getEditFile() {
        return editFile;
    }

    public static byte getEditFileOk() {
        return editFileOk;
    }

    public static byte getEditFileNOk() {
        return editFileNOk;
    }

    public static byte getSendListFileFromService() {
        return sendListFileFromServer;
    }

//    public static byte getSendListFileFromServiceEnd() {
//        return sendListFileFromServiceEnd;
//    }

    public static byte getDelFileFromServer() {
        return delFileFromServer;
    }

    public static byte getDelFileFromServerOk() {
        return delFileFromServerOk;
    }

    public static byte getDelFileFromServerNOk() {
        return delFileFromServerNOk;
    }

    public static byte getSendListFileFromServer() {
        return sendListFileFromServer;
    }

    public static byte getSendListFileFromServerOk() {
        return sendListFileFromServerOk;
    }
}
