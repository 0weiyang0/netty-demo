package com.test.socket.nio;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author 臧娜
 * @version 1.0
 * @date 2022/10/4 14:32
 */
public class NioClient {
    public static void main(String[] args) throws Exception{
        //   开启一个客户端
        SocketChannel channel = SocketChannel.open();
        //给他一个连接地址
        SocketAddress address = new InetSocketAddress("127.0.0.1",4321);
        channel.connect(address);
        //  先写后读
        ByteBuffer writeBuffer = ByteBuffer.allocate(128);
        writeBuffer.put("hello server from client".getBytes());
        writeBuffer.flip();
        channel.write(writeBuffer);

        ByteBuffer readBuffer = ByteBuffer.allocate(128);
        channel.read(readBuffer);
        readBuffer.flip();

        StringBuffer sb = new StringBuffer();
        while (readBuffer.hasRemaining()){
            sb.append((char) readBuffer.get());
        }
        System.out.println("server data:" +sb.toString());
    }
}
