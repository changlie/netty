package com.changlie.demo1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class EchoClient {

    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //1创建 Bootstrap
            Bootstrap b = new Bootstrap();
            //2指定 EventLoopGroup 来处理客户端事件。
            // 由于我们使用 NIO 传输，所以用到了 NioEventLoopGroup 的实现
            b.group(group)
                    //3 使用的 channel 类型是一个用于 NIO 传输
                    .channel(NioSocketChannel.class)
                    // 设置服务器的 InetSocketAddress
                    .remoteAddress(new InetSocketAddress(host, port))    //4
//                    .handler(new EchoClientHandler());
                    //当建立一个连接和一个新的通道时，创建添加到 EchoClientHandler 实例 到 channel pipeline
                    .handler(new ChannelInitializer<SocketChannel>() {    //5
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });

            //连接到远程;等待连接完成
            ChannelFuture f = b.connect().sync();        //6
            //阻塞直到 Channel 关闭
            f.channel().closeFuture().sync();            //7
        } finally {
            //调用 shutdownGracefully() 来关闭线程池和释放所有资源
            group.shutdownGracefully().sync();            //8
        }
    }

    public static void main(String[] args) throws Exception {
//        if (args.length != 2) {
//            System.err.println(
//                    "Usage: " + EchoClient.class.getSimpleName() +
//                            " <host> <port>");
//            return;
//        }
//
//        final String host = args[0];
//        final int port = Integer.parseInt(args[1]);
        String host = "127.0.0.1";
        int port = 8989;

        new EchoClient(host, port).start();
    }
}