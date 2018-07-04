package com.changlie.msgpack1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class EchoServerHandler extends ChannelHandlerAdapter {

    int counter = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
        System.out.println("obj class : "+obj.getClass());
        UserInfo user = (UserInfo) obj;

        System.out.println("server receive: "+user);

        ctx.writeAndFlush(user.toString()+ " <= server to client");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("  server channelActive....");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println(" 出错===========");
        cause.printStackTrace();
        ctx.close();// 发生异常，关闭链路
    }
}
