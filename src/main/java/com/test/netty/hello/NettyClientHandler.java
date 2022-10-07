package com.test.netty.hello;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author 臧娜
 * @version 1.0
 * @date 2022/10/7 15:46
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelActive done");
		ctx.writeAndFlush(Unpooled.copiedBuffer("hello,i am client", CharsetUtil.UTF_8));
//		super.channelActive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf	= (ByteBuf)msg;
		System.out.println("server msg :" + buf.toString(CharsetUtil.UTF_8));
		System.out.println("server from :" + ctx.channel().remoteAddress());
//		super.channelRead(ctx, msg);
	}
}
