package com.test.netty.hello;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
/**
 * @author 臧娜
 * @version 1.0
 * @date 2022/10/7 14:03
 */
/**
 * 梳理   ：  EventLoopGroup  是对应Reactor的事件循环组
 *              ServerBootstrap  是配置参数的启动对象
 *              客户端通道  需要使用childHandler设置
 *              设置时，需要创建ChannelInitializer   并且声明其泛型
 *              实现初始化方法时，拿到管道，增加自定义的处理器
 *          异步启动
 */
public class NettyServer {
    public static void main(String[] args)  {
        //  创建Reactor    构建主从Reactor模型  用来管理Channel    都是无限循环的事件组（线程池）
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        //  服务端的引导程序  （启动对象）
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //  设置相关的参数     链式编程的使用方法
        ServerBootstrap bootstrap = serverBootstrap.group(bossGroup, workerGroup)
                //   声明当前使用的通道类型
                //  ServerSocket -->   ServerSocketChannel -->   NioServerSocketChannel
                //    bio                   nio                         netty
                //  底层是通过反射进行调用的
                .channel(NioServerSocketChannel.class)
                //  handler  处理器
                //   设置前面通道的处理器   使用netty的日志打印处理器
                .handler(new LoggingHandler(LogLevel.INFO))
                //   定义【客户端连接处理器】的使用
                //   通道初始化器
                //   抽象类，需要实现其抽象方法
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //  通过channel管道
                        //  通道代表连接的角色
                        //  管道代表处理业务的逻辑管理
                        //      相当于链表，将不同的处理器连接起来。管理的是处理器的顺序
                        //       使用通常使用尾插法    addLast将处理器增加至尾部
                        socketChannel.pipeline().addLast(new NettyServerHandler());
                    }
                });
        System.out.println("服务器初始化完成");

        //  启动并设置端口号   sync() 异步启动    阻塞finally中的代码执行
        ChannelFuture future = null;
        try {
            future = serverBootstrap.bind(8888).sync();
            //  将关闭通道的方式   也设置为异步的
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
