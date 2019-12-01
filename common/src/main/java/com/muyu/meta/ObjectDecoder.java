package com.muyu.meta;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author 七小鱼
 */
public class ObjectDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        System.out.println("解码...");
        byte[] values = new byte[msg.readableBytes()];
        msg.readBytes(values);
        Object obj = SerializationUtils.deserialize(values);
        out.add(obj);
    }

}
