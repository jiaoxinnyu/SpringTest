package com.xinyu.nio;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup boss = new NioEventLoopGroup(2);
        NioEventLoopGroup worker = new NioEventLoopGroup(5);
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)//使用nioServersocketchannel做为服务端实现
                    .option(ChannelOption.TCP_NODELAY, false)
                    .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列得到链接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持链接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道 通过匿名函数
                        //给pipeling设置处理器 相当于给我们worker工作线程的eventloop对应的管道设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //设置对应的handel 在具体的handel里面我们做数据的读取和业务操作 以及响应客户端操作
                            socketChannel.pipeline().addLast(new NioServerHandle());
                        }
                    });

            System.out.println("服务端 ready ....");
            //绑定一个端口 并且同步 生成了一个channelFuture对象
            ChannelFuture channelFuture = serverBootstrap.bind(6668).sync();
            //对关闭通道进行监听 注意这里不是关闭 是注册一个监听事件
            channelFuture.channel().closeFuture().sync();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }
}
