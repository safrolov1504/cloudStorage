package com.cloudStorage.service.connection.handlers;

import com.cloudStorage.common.data.CreatCommand;
import com.cloudStorage.service.workingWithMessage.SendFileToClient;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


// Идет после FirstHandler в конвеере
public class EditFileHandler extends ChannelInboundHandlerAdapter {
    private File file;
    private FileOutputStream fileOutputStream;
    private FileInputStream fileInputStream;

    public enum StateSecond{
        IDLE, SEND_FILE_TO_CLIENT, DEL_FILE_FROM_SERVER,
    }

    private StateSecond stateSecond = StateSecond.IDLE;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String [] aboutFile = (String[]) msg;

        if(aboutFile[0].equals(StateSecond.SEND_FILE_TO_CLIENT.toString())){
            stateSecond = StateSecond.SEND_FILE_TO_CLIENT;
        } else if(aboutFile[0].equals(StateSecond.DEL_FILE_FROM_SERVER.toString())){
            stateSecond = StateSecond.DEL_FILE_FROM_SERVER;
        }


        String nameFile = aboutFile[1];
        String userName = aboutFile[2];

        //open file
        file = new File("cloud-service/global-storage/"+userName+"/"+nameFile);


        //if it needs to send file to client
        if(stateSecond == StateSecond.SEND_FILE_TO_CLIENT) {
            //if file is not exist, then send error command
            if (!file.exists()) {
                SendBack.sendBack(ctx, CreatCommand.getGetFileNOk());
                return;
            }
            fileInputStream = new FileInputStream(file.getPath());
            //int lengthNameFile = nameFile.length();
            long lengthFile = file.length();

            //send command CreatCommand.getGetFile()
            SendBack.sendBack(ctx, CreatCommand.getGetFileOk());
            //send length of name
            SendBack.sendBack(ctx, SendFileToClient.getLengthName(nameFile));
            //send name
            SendBack.sendBack(ctx, SendFileToClient.getByteName(nameFile));
            //send length of file
            SendBack.sendBack(ctx,SendFileToClient.longToBytes(lengthFile));
            //send file
            SendFileToClient.sendFile(ctx, lengthFile, fileInputStream);
            System.out.println("file "+ nameFile + " was send");
        }

        //if it needs to del file from server
        if(stateSecond == StateSecond.DEL_FILE_FROM_SERVER){
            if (!file.exists()) {
                SendBack.sendBack(ctx, CreatCommand.getDelFileFromServerNOk());
                return;
            }
            fileInputStream = new FileInputStream(file.getPath());
            file.delete();
            SendBack.sendBack(ctx, CreatCommand.getDelFileFromServerOk());
            System.out.println("file "+ nameFile + " was send");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
