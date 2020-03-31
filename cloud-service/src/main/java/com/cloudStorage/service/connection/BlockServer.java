package com.cloudStorage.service.connection;

import com.cloudStorage.service.connection.handlers.AuthHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class BlockServer implements Runnable {

    @Override
    public void run() {
        // Пул потоков для обработки подключений клиентов
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // Пул потоков для обработки сетевых сообщений
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // Создание настроек сервера
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup) // указание пулов потоков для работы сервера
                    .channel(NioServerSocketChannel.class) // указание канала для подключения новых клиентов
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception { // настройка конвеера для каждого подключившегося клиента
                            ch.pipeline().addLast(
                                    //new StringToByteBufHandler(),
                                    new AuthHandler());
                                    //new CommandHandler(),
                                    //new GatewayHandler(),
                                    //new SingInHandler());
                        }
                    });
            System.out.println("waiting for client");
            ChannelFuture f = b.bind(8189).sync(); // запуск прослушивания порта 8189 для подключения клиентов
            System.out.println("connected with client");
            f.channel().closeFuture().sync(); // ожидание завершения работы сервера
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully(); // закрытие пула
            bossGroup.shutdownGracefully(); // закрытие пула
        }
    }
}
