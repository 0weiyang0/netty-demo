package com.test.socket.nio;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author 臧娜
 * @version 1.0
 * @date 2022/10/4 14:18
 */
public class NioServer {
    public static void main(String[] args) throws Exception {
        //创建服务端的通道     通过调用open方法
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        SocketAddress address = new InetSocketAddress("127.0.0.1",4321);
        //绑定ip地址和端口号
        serverSocketChannel.socket().bind(address);
        //接收客户端的连接
        SocketChannel socketChannel = serverSocketChannel.accept();
        //  数据处理都要通过buffer
        ByteBuffer buffer = ByteBuffer.allocate(128);
        buffer.put("hello client".getBytes());
        //  刷新
        buffer.flip();
        socketChannel.write(buffer);
        ByteBuffer readBuffer = ByteBuffer.allocate(128);
        //  把数据读到readBuffer中
        socketChannel.read(readBuffer);
        readBuffer.flip();
        StringBuffer sb = new StringBuffer();
        while (readBuffer.hasRemaining()){
            sb.append((char)readBuffer.get());
        }
        System.out.println("client data" + sb.toString());
        socketChannel.close();
        serverSocketChannel.close();
    }
}
