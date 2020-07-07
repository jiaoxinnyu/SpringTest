package com.xinyu.nio;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 自定义一个handle处理器，需要继承netty规定好的某一个 HandlerAdapter
 */
public class NioClientHandle extends ChannelInboundHandlerAdapter {
    /**
     *
     * @param ctx 上下文对象包含一系列信息，通道 管道 地址等等
     * @param msg 客户端消息
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client context:"+ctx);
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("服务端回复消息："+buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务端地址："+ctx.channel().remoteAddress());
    }

    /**
     * 当通道准备好之后
     * @param ctx
     * @throws Exception
     */
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端："+ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello server,",CharsetUtil.UTF_8));
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
