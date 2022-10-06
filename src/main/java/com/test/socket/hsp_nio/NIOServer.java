package com.test.socket.hsp_nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author 臧娜
 * @version 1.0
 * @date 2022/10/5 19:54
 */
public class NIOServer {
    public static void main(String[] args) throws Exception{
        //创建ServerSocketChannel   -->   ServerSocket
        ServerSocketChannel serverSocketChannel= ServerSocketChannel.open();
         //得到一个Selector对象
        Selector selector = Selector.open();
        //绑定端口，在服务器监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
         //把serverSocketChannel 注册到selector
        //关心  这个通道关联的事件为OP_ACCEPT（接收事件）
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //循环等待客户端的连接
        while (true){
            //等待1s,如果没有连接事件发生，返回
            //select方法的作用就是 【监听】
            if (selector.select(1000) ==0 ){
                System.out.println("服务器等待了1s,无连接");
                continue;
            }
            //  如果返回的>0
            //  表示已经获取到关注的事件
            //   selector.selectedKeys()  返回关注事件的集合
            //   通过selectedKeys反向获取通道
            Set<SelectionKey> selectionKeySet = selector.selectedKeys();
            //遍历Set<SelectionKey>
            Iterator<SelectionKey> keyIterator = selectionKeySet.iterator();
            while (keyIterator.hasNext()){
                //  获取到selectionKey
               SelectionKey key =  keyIterator.next();
                //  根据key  对应的通道发生的事件做相应的处理
                if (key.isAcceptable()){
                    //有一个客户端进行了连接
                    //给该客户端生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                  //将socketChannel设置为非阻塞
                    socketChannel.configureBlocking(false);

                    //将socketChannel  注册到selector   关注事件是OP_READ
                    //  同时关联一个Buffer
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if (key.isReadable()){ //发生OP_READ
                    //  通过key  反向获取到对应channel
                    SocketChannel channel = (SocketChannel)key.channel();
                    // 获取到该channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("from 客户端"+new String(buffer.array()));
                }
                // 手动从集合中移除当前的selectionKey,防止重复操作
                keyIterator.remove();
            }
        }
    }
}
