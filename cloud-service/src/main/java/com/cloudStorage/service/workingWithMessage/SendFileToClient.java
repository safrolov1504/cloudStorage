package com.cloudStorage.service.workingWithMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class SendFileToClient {

    public static byte[] getLengthName(String nameFile){
        int length = nameFile.length();

        byte [] byteLength = intToByteArray(length);
        return byteLength;
    }

    public static byte [] getByteName (String nameFile){
        return nameFile.getBytes();
    }

    public static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }

    private static final byte[] intToByteArray(int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
    }

    public static void sendFile(ChannelHandlerContext ctx, long lengthFile, FileInputStream fileInputStream) {
        //send file to client
        boolean flag=true;
        int i;
        byte [] byteArray = new byte[1024];
        try {
            while (flag){
                if(lengthFile<1024){
                    byteArray = new byte[(int) lengthFile];
                    flag = false;
                }
                    i = fileInputStream.read(byteArray);
                    lengthFile-=i;
                    sendBackBytes(ctx,byteArray);
            }
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendBackBytes(ChannelHandlerContext ctx, byte [] arr){
        ByteBuf buf = ctx.alloc().buffer(arr.length);
        //System.out.println(Arrays.toString(arr));
        buf.writeBytes(arr);
        ctx.writeAndFlush(buf);
    }
}
