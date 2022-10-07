package com.test.netty.hello;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author 臧娜
 * @version 1.0
 * @date 2022/10/7 15:39
 */
public class NettyClient {
	public static void main(String[] args) {
		// 客户端只需要一个事件循环组
		EventLoopGroup group = new NioEventLoopGroup();
		//  客户端的启动对象
		Bootstrap bootStrap = new Bootstrap();
		bootStrap.group(group)
				.channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new NettyClientHandler());
					}
				});
		System.out.println("客户端初始化完成.....");
		try {
			ChannelFuture future = bootStrap.connect("127.0.0.1",8888).sync();
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			group.shutdownGracefully();
		}
	}

}
