package com.muyu.server;

import com.muyu.domain.HostAndPort;
import com.muyu.meta.MethodInvokeMetaWrap;
import com.muyu.meta.ObjectDecoder;
import com.muyu.meta.ObjectEncoder;
import com.muyu.meta.ResultWrap;
import com.muyu.service.RPCClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.net.InetSocketAddress;

/**
 * @author 七小鱼
 */
public class NettyRPCClient implements RPCClient {
    private Bootstrap bt;
    private EventLoopGroup worker;

    public NettyRPCClient() {
        bt = new Bootstrap();
        worker = new NioEventLoopGroup();
        bt.group(worker);
        bt.channel(NioSocketChannel.class);
    }

    @Override
    public ResultWrap invoke(final MethodInvokeMetaWrap mimw, HostAndPort hap) {
        try {
            bt.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    //数据帧解码器
                    pipeline.addLast(new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
                    //数据帧头编码器
                    pipeline.addLast(new LengthFieldPrepender(2));
                    //解码
                    pipeline.addLast(new ObjectDecoder());
                    //编码
                    pipeline.addLast(new ObjectEncoder());
                    //添加最终处理者
                    pipeline.addLast(new ChannelHandlerAdapter() {

                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            ChannelFuture channelFuture = ctx.writeAndFlush(mimw);
                            channelFuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
                            channelFuture.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                        }

                        @Override
                        public void channelRead(ChannelHandlerContext ctx,
                                                Object msg) throws Exception {
                            ResultWrap rw = (ResultWrap) msg;
                            mimw.setResultWrap(rw);
                        }

                        @Override
                        public void exceptionCaught(ChannelHandlerContext ctx,
                                                    Throwable cause) throws Exception {
                            cause.printStackTrace();
                        }

                    });
                }
            });
            ChannelFuture future = bt.connect(new InetSocketAddress(hap.getHost(), hap.getPort())).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return mimw.getResultWrap();
    }

    public void close() {
        worker.shutdownGracefully();
    }

}
