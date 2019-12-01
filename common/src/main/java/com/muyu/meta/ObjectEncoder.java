package com.muyu.meta;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author 七小鱼
 */
public class ObjectEncoder extends MessageToMessageEncoder<Object> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg,
                          List<Object> out) throws Exception {
        System.out.println("编码..");
        byte[] values = SerializationUtils.serialize(msg);
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeBytes(values);
        out.add(buffer);
    }
}
