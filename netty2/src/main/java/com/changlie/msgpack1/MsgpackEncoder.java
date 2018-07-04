package com.changlie.msgpack1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

public class MsgpackEncoder extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object obj, ByteBuf buf) throws Exception {
        MessagePack msgpack = new MessagePack();
        System.out.println("MsgpackEncoder => "+obj);
        // Serialize
        byte[] raw = msgpack.write(obj);
        buf.writeBytes(raw);
    }
}
