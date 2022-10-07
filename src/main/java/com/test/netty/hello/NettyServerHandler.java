package com.test.netty.hello;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author 臧娜
 * @version 1.0
 * @date 2022/10/7 14:46
 *   //   ctrl + o
 */
//   自定义handler的方式之一
    //  继承  ChannelInboundHandlerAdapter
    //   提供了在不同时期会触发的方法
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    // 刚刚建立连接要使用的方法
    //  业务使用中，往往【发送欢迎消息】
    //  ChannelHandlerContext  是通道处理器的上下文
    //   可以整合使用过程中所需的参数   比如通道，管道，写数据
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //  写数据时，可以调用writeAndFlush，直接写入字符串
        //  底层还是  获取通道，创建缓冲区，写入数据，缓冲区写入通道等流程
        System.out.println("channelActive  done ......");
        ctx.writeAndFlush("welcome to NettyServer .....");
//        super.channelActive(ctx);
    }

    //  当客户端发送消息时，   读事件发送的方法
    //   Object msg   发送过来的消息
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //netty中的缓冲区，   ByteBuf  ---》对ByteBuffer的封装
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("client msg :" + buf.toString(CharsetUtil.UTF_8));
        // remoteAddress   可以定位到客户端的远程地址
        System.out.println("client from :"+ ctx.channel().remoteAddress());
//        super.channelRead(ctx, msg);
    }

    //  数据读取完成会调用的方法
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 数据的处理还是使用 ByteBuf， Unpooled是提供 在ByteBuf 和 string 之间方便转换的工具类
        //  Unpooled 常用的方法，copiedBuffer   直接处理String 返回ByteBuf
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello client,how are you?",CharsetUtil.UTF_8));

//        super.channelReadComplete(ctx);
    }
}
