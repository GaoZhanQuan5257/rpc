package com.muyu.server;

import com.muyu.domain.HostAndPort;
import com.muyu.meta.*;
import com.muyu.service.Registry;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author 七小鱼
 */
public class ServerProvider {
    //需要暴露bean服务
    private Map<Class, Object> beanFactory;
    //注册服务
    private Registry registry;

    //Netty相关
    private ServerBootstrap sbt;
    private EventLoopGroup boss;
    private EventLoopGroup worker;
    private int port;

    public ServerProvider(int port) {
        this.port = port;
        init();
    }

    public void init() {
        sbt = new ServerBootstrap();
        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();
        sbt.group(boss, worker);
        sbt.channel(NioServerSocketChannel.class);

        //初始化通道 -- 重要
        sbt.childHandler(new ChannelInitializer<SocketChannel>() {

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
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        MethodInvokeMetaWrap mimw = (MethodInvokeMetaWrap) msg;
                        MethodInvokeMeta invokeMeta = mimw.getInvokeMeta();
                        Map<Object, Object> attchments = mimw.getAttchments();
                        //反射调用本地实现类
                        Object targetObject = beanFactory.get(invokeMeta.getTargetClass());
                        Method method = null;
                        try {
                            method = targetObject.getClass().getDeclaredMethod(invokeMeta.getName(), invokeMeta.getParameterTypes());
                        } catch (Exception e1) {
                            method = targetObject.getClass().getSuperclass().getDeclaredMethod(invokeMeta.getName(), invokeMeta.getParameterTypes());
                        }

                        Result result = new Result();
                        try {
                            Object value = method.invoke(targetObject, invokeMeta.getArgs());
                            result.setReturnValue(value);
                        } catch (Exception e) {
                            e.printStackTrace();
                            result.setException(new RuntimeException(e.getCause()));
                        }

                        //封装ResultWrap
                        ResultWrap rw = new ResultWrap(result);
//                        rw.setAttchments(attchments);
                        //写回结果
                        ChannelFuture channelFuture = ctx.writeAndFlush(rw);
                        channelFuture.addListener(ChannelFutureListener.CLOSE);
                        channelFuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
                        channelFuture.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                    }

                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx,
                                                Throwable cause) throws Exception {
                        cause.printStackTrace();
                    }

                });
            }
        });

    }

    public void start() throws UnknownHostException {
        //获取服务端IP
        HostAndPort hap = new HostAndPort(InetAddress.getLocalHost().getHostAddress(), port);
        //注册服务
        for (Class targetInterface : beanFactory.keySet()) {
            registry.registerService(targetInterface, hap);
        }

        new Thread() {
            public void run() {
                try {
                    System.out.println("服务启动@" + port);
                    ChannelFuture channelFuture = sbt.bind(port).sync();
                    channelFuture.channel().closeFuture().sync();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            ;
        }.start();
    }

    public void close() {
        boss.shutdownGracefully();
        worker.shutdownGracefully();
        registry.close();
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

    public void setBeanFactory(Map<Class, Object> beanFactory) {
        this.beanFactory = beanFactory;
    }

}
