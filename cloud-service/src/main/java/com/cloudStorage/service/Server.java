package com.cloudStorage.service;


import com.cloudStorage.service.connection.BlockServer;

public class Server {
    public static void main(String[] args) throws Exception {
        new BlockServer().run();
    }
}
