package com.test.groupchat;

import com.sun.corba.se.spi.ior.IdentifiableFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author 臧娜
 * @version 1.0
 * @date 2022/10/6 9:26
 */
public class GroupChatServer {
    private ServerSocketChannel listenChannel;
    private Selector selector;
    private static final int PORT =8989;
    //  设置构造器，在创建群聊服务端的时候，就做这件事情
    public GroupChatServer() {
        try {
            listenChannel = ServerSocketChannel.open();
            selector = Selector.open();
            // 绑定端口
            InetSocketAddress address = new InetSocketAddress(PORT);
            listenChannel.socket().bind(address);
            //设置为非阻塞
            listenChannel.configureBlocking(false);
            // listenChannel注册到selector中   同时设置事件为接收事件
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //设置一个方法，进行监听
    public void listen() throws IOException {
        //  循环
        while (true){
            if (selector.select(2000) == 0){
                System.out.println("等待中..................");
            }
            else{
               // 遍历选择键
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()){
                   SelectionKey key = keyIterator.next();
                   //判断是哪个行为
                    if (key.isAcceptable()){
                      SocketChannel socketChannel = listenChannel.accept();
                      socketChannel.configureBlocking(false);
                      socketChannel.register(selector,SelectionKey.OP_READ);
                        System.out.println(socketChannel.getRemoteAddress() + "上线了.........");
                    }
                    if (key.isReadable()){
                    // 获取消息并转发
                        this.acceptMessage(key);
                    }
                    //将当前key删除，防止重复处理
                    keyIterator.remove();
                }
            }
        }
    }
    //获得来自客户端的消息
    private void acceptMessage(SelectionKey key) throws IOException {
        SocketChannel socketChannel =  (SocketChannel) key.channel();
        socketChannel.configureBlocking(false);
        //  读取到来的数据存放的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 读取的字节数
        int count = 0;
        try {
            count = socketChannel.read(buffer);
            if (count > 0){
                //说明读取到了字节
                String msg = new String(buffer.array());
                System.out.println("来自客户端的消息："+msg);
                //之后，对消息进行转发，注意，不要对自己进行转发
                sendMessagesToOtherChannels(msg,socketChannel);
            }
        } catch (IOException e) {
            try {
                System.out.println(socketChannel.getRemoteAddress()+"离线............");
                //  取消注册
                    key.cancel();;
                // 关闭通道
                socketChannel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }
    private void sendMessagesToOtherChannels(String msg,SocketChannel selfChannel) throws IOException {
        System.out.println("服务器转发消息中");
        Set<SelectionKey> keys = selector.keys();
        for (SelectionKey key : keys){
            // 判断是否是 selfChannel
          Channel dest = key.channel();
          if (dest instanceof SocketChannel && dest != selfChannel){
              SocketChannel socketChannel = (SocketChannel)dest;
              ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
              socketChannel.write(buffer);
          }
        }
    }

    public static void main(String[] args) throws IOException {
        GroupChatServer chatServer = new GroupChatServer();
        chatServer.listen();
    }
}
