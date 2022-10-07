package com.test.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author 臧娜
 * @version 1.0
 * @date 2022/10/7 13:15
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        //  创建BossGroup和WorkerGroup
        //  bossGroup   只是处理连接请求
        //  真正的与客户端业务处理  交给workerGroup完成
        //  两个都是无限循环
          NioEventLoopGroup bossGroup  =  new NioEventLoopGroup();
          NioEventLoopGroup workerGroup = new NioEventLoopGroup();

          //  创建服务器端的启动对象，配置参数
        ServerBootstrap bootstrap = new ServerBootstrap();
            //  使用链式编程进行参数的设置
        bootstrap.group(bossGroup,workerGroup) //设置两个线程
                 .channel(NioServerSocketChannel.class)// 使用NioSocketChannel  作为服务器的通道实现
                 .option(ChannelOption.SO_BACKLOG,128)  //设置线程队列连接的个数
                 .childOption(ChannelOption.SO_KEEPALIVE,true)//设置保持活动连接状态
                 .childHandler(new ChannelInitializer<SocketChannel>() {
                                //创建一个通道测试对象(匿名对象)
                                //给pipeline  设置处理器
                     @Override
                     protected void initChannel(SocketChannel socketChannel) throws Exception {
                         socketChannel.pipeline().addLast(null);
                     }
                 }) //给我们的workerGroupDE EventGroup 的EventLoop对应的管道设置处理器
        ;
        System.out.println("...服务器  is  ready...");
        //  绑定一个端口并且同步，生成了一个ChannelFuture对象
        ChannelFuture cf  = bootstrap.bind(6668).sync();
        //  对关闭通道进行监听
        cf.channel().closeFuture().sync();
    }


}
