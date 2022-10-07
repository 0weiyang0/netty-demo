package com.test.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author 臧娜
 * @version 1.0
 * @date 2022/10/7 13:41
 * ChannelInboundHandlerAdapter    入栈的适配器
 */
//    自定义一个handler，需要继承netty  规定好的某个HandlerAdapter
    //  自定义的一个Handler  ，才能称为  Handler
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

  //  ChannelHandlerContext   上下文对象，  含有管道popeline，通道
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx,msg);
    }


}
