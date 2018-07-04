package com.changlie.demo1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

@Sharable                                //1@Sharable标记这个类的实例可以在 channel 里共享
public class EchoClientHandler extends
        SimpleChannelInboundHandler<ByteBuf> {

    //服务器的连接被建立后调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //当被通知该 channel 是活动的时候就发送信息
        ctx.writeAndFlush(Unpooled.copiedBuffer("update user", //2
                CharsetUtil.UTF_8));
    }

    //数据后从服务器接收到调用
    @Override
    public void channelRead0(ChannelHandlerContext ctx,
                             ByteBuf in) {
        //记录接收到的消息
        System.out.println("Client received: " + in.toString(CharsetUtil.UTF_8));    //3
    }

    // 捕获一个异常时调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        //4记录日志错误并关闭 channel
        cause.printStackTrace();
        ctx.close();
    }
}