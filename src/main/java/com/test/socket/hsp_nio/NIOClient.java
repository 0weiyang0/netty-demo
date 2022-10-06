package com.test.socket.hsp_nio;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author 臧娜
 * @version 1.0
 * @date 2022/10/5 20:51
 */
public class NIOClient {
    public static void main(String[] args) throws Exception{
        //得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞模式
        socketChannel.configureBlocking(false);

        //提供服务端的ip和端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1",6666);
        //如果没有连接成功
        if (!socketChannel.connect(inetSocketAddress)){
            while(!socketChannel.finishConnect()){
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他工作");
            }
        }
        // .....连接成功，就发送数据
        String str = "hello,尚硅谷";
        ByteBuffer wrap = ByteBuffer.wrap(str.getBytes());
        //发送数据，将buffer写入channel
        socketChannel.write(wrap);
    }
}
