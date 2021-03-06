package com.changlie.demo1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }



    public void start() throws Exception {
        //创建 EventLoopGroup
        NioEventLoopGroup group = new NioEventLoopGroup(); //3
        try {
            //4创建 ServerBootstrap
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    //5指定使用 NIO 的传输 Channel
                    .channel(NioServerSocketChannel.class)
                    //6设置 socket 地址使用所选的端口
                    .localAddress(new InetSocketAddress(port))
                    //7 添加 EchoServerHandler 到 Channel 的 ChannelPipeline
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(new EchoServerHandler());
                        }
                    });

            //绑定的服务器;sync 等待服务器关闭
            ChannelFuture f = b.bind().sync();            //8
            System.out.println(EchoServer.class.getName()
                                + " started and listen on "
                                + f.channel().localAddress());
            //9关闭 channel 和 块，直到它被关闭
            f.channel().closeFuture().sync();
        } finally {
            //关机的 EventLoopGroup，释放所有资源。
            group.shutdownGracefully().sync();            //10
        }
    }


    public static void main(String[] args) throws Exception {
//        if (args.length != 1) {
//            System.err.println("Usage: "
//                            + EchoServer.class.getSimpleName()
//                            + " <port>");
//            return;
//        }

        //设置端口值（抛出一个 NumberFormatException 如果该端口参数的格式不正确）
//        int port = Integer.parseInt(args[0]);        //1
        int port = 8989;
        //呼叫服务器的 start() 方法
        new EchoServer(port).start();                //2
    }

}