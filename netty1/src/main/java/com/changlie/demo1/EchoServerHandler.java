package com.changlie.demo1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

@Sharable //@Sharable 标识这类的实例之间可以在 channel 里面共享
public class EchoServerHandler extends
        ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx,
                            Object msg) {
        String threadName = Thread.currentThread().getName();
        System.out.println("currentThread Name: "+threadName);
        ByteBuf in = (ByteBuf) msg;

        String msg1 = in.toString(CharsetUtil.UTF_8);
        if("testException".equals(msg1)){
            throw new RuntimeException("测试异常情况");
        }
        //日志消息输出到控制台
        System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));        //2
        //.将所接收的消息返回给发送者。注意，这还没有冲刷数据
        ctx.write(in);                            //3
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ByteBuf msg = Unpooled.copiedBuffer("---- finish!", CharsetUtil.UTF_8);
        ctx.write(msg);
        // 冲刷所有待审消息到远程节点。关闭通道后，操作完成
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)//4
                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        ByteBuf msg = Unpooled.copiedBuffer("exception occur!", CharsetUtil.UTF_8);
        ctx.writeAndFlush(msg);
        //打印异常堆栈跟踪
        cause.printStackTrace();                //5
        //关闭通道
        ctx.close();                            //6
    }
}
